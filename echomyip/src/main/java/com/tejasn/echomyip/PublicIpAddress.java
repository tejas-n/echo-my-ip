/*
 * Copyright 2021 Tejas Nandanikar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tejasn.echomyip;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Represents the public IP address assigned to the gateway/router.
 */
public class PublicIpAddress {

    private static final String OPENDNS_SERVER_IP = "208.67.222.222";

    private static final byte[] requestPacket = {
            // Transaction ID: 0x0001
            0x00, 0x1,
            // Flags: 0x0000 (Standard query without recursion)
            0x00, 0x00,
            // Questions: 1
            0x00, 0x01,
            // Answers: 0
            0x00, 0x00,
            // Authority Records: 0
            0x00, 0x00,
            // Additional Records: 0
            0x00, 0x00,
            // Query for myip.opendns.com
            0x04, 0x6d, 0x79, 0x69, 0x70, 0x07, 0x6f, 0x70, 0x65, 0x6e, 0x64, 0x6e, 0x73, 0x03, 0x63, 0x6f, 0x6d, 0x00,
            // Type A query
            0x00, 0x01,
            // IN
            0x00, 0x01
    };

    /**
     * Finds the public IP address of the host machine by sending a DNS packet to query "A record" for myip.opendns.com
     * to OpenDNS server
     *
     * @return the public IPv4 address for the host machine or null if it couldn't find the address for unknown reasons
     * @throws IOException An IO error occurred
     */
    public String myIPv4Address() throws IOException {
        byte[] mBuffer = new byte[50];
        DatagramPacket sendPacket =
                new DatagramPacket(requestPacket, requestPacket.length, InetAddress.getByName(OPENDNS_SERVER_IP), 53);
        DatagramPacket packet = new DatagramPacket(mBuffer, mBuffer.length);
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(10000);
            socket.send(sendPacket);
            socket.receive(packet);
            StringBuilder ip4StringBuilder = new StringBuilder();
            for (int i = 46; i < 50; i++) {
                ip4StringBuilder.append(mBuffer[i] & 0xFF);
                if (i != 49) {
                    ip4StringBuilder.append(".");
                }
            }
            String ipv4Address = ip4StringBuilder.toString();
            if (!ipv4Address.equals("0.0.0.0")) {
                return ipv4Address;
            } else {
                return null;
            }
        }
    }
}
