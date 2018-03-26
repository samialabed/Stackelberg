#!/bin/bash

rmiregistry &
java -classpath poi-3.7-20101029.jar: -Djava.rmi.server.hostname=127.0.0.1 comp34120.ex2.Main &
java -Djava.rmi.server.hostname=127.0.0.1 SimpleLeader &
