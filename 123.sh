#!/bin/bash
mvn compile
mvn package
docker build -t sepa-17-images .
docker tag sepa-17-images:latest 185725700440.dkr.ecr.ap-southeast-2.amazonaws.com/sepa-17-images:latest
docker push 185725700440.dkr.ecr.ap-southeast-2.amazonaws.com/sepa-17-images:latest