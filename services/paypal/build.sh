#!/usr/bin/env bash
mvn clean
mvn package -Dmaven.test.skip=true
docker build -t life14ok/ea-paypal .
docker push life14ok/ea-paypal
