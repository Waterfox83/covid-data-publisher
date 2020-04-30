#!/usr/bin/env bash

echo " ++++++++++++ Starting Gradle build  +++++++++++++ "

gradle clean build

echo " ++++++++++++ Building Tar  ++++++++++++ "

gradle jibBuildTar

echo " ++++++++++++ Removing previous docker image  ++++++++++++ "

docker rmi waterfox83/covid-data-source-service:latest

echo " ++++++++++++ Loading new docker image  ++++++++++++ "

docker load --input build/jib-image.tar

echo " ++++++++++++ Logging into ACR  ++++++++++++ "

docker login -u waterfox83 -p Abhishek123*

echo " ++++++++++++ Pushing new docker image to ACR  ++++++++++++ "

docker push waterfox83/covid-data-source-service:latest

echo " ++++++++++++ Deleting old kubectl deployments  ++++++++++++ "

kubectl delete deployment covid-data-source-deployment

echo " ++++++++++++ Creating new kubectl deployments  ++++++++++++ "

kubectl apply -f deploy/data-publisher-httpdeploy.yaml
