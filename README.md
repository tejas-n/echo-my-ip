echo-my-ip
==========
A small and fast Java/Android utility library to find out the public IP address of the host machine

```
PublicIpAddress publicIpAddress = new PublicIpAddress();

//It's recommended to execute this on background thread(which is mandatory on Android)
String ip = publicIpAddress.myIPv4Address();
```

How does it work?
=================
The library finds the public IP address of the host machine by sending a DNS packet to query "A record" for
"myip.opendns.com" to OpenDNS server(208.67.222). The OpenDNS servers have been programmed to echo back the IP of the
request sender if it requests for the IP of "myip.opendns.com"

Why use this instead of a web service which echoes back the IP?
===============================================================
You can use web services which echo back public IP - for eg, https://postman-echo.com/ip - and it's fine for most of the
cases. Using the library has the following advantages though:

1. It's faster.
2. It has a smaller network footprint.
3. It is more reliable given the fact that DNS servers have a good uptime and also are less likely to go away.

Download
========

```
repositories {
  mavenCentral()
}
dependencies {
  implementation 'com.tejasn:echomyip:0.1.1'
}
```

License
=======

```
Copyright 2021 Tejas Nandanikar

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```