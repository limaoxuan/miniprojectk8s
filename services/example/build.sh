#!/usr/bin/env bash
mvn clean
mvn package -Dmaven.test.skip=true
docker build -t life14ok/k8s-test .
docker push life14ok/k8s-test
