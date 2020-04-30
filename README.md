# covid-data-source-service

## Build
 Run `./gradlew clean check`

## Running Locally 
 To launch the application locally with the profile 'no-dependencies', simply run `./gradlew clean bootRun`
 
 Run in remote debugging mode `./gradlew clean bootRun --debug-jvm --stacktrace`

 Currently this service needs a local PostgreSQL installation to run, details of which are given in 
 `application-local.yaml`

 
## Running in docker

 Run `./gradlew jibBuildTar`
 
 Run `docker load --input build/jib-image.tar`
 
 Run `docker run -p 8080:8080 waterfox83/covid-data-source-service`
 

## Deploying to Kubernetes Cluster

For running on AKS, we should create and push the image to ACR. Here we are pushing it to docker hub.

We need to create the image first though, run `./gradlew jibBuildTar` and `docker load --input build/jib-image.tar` 
to create and load the image. 
 
Push it to docker hub: `docker push waterfox83/covid-data-source-service:latest`

Deploy the image on Kubernetes: `kubectl apply -f deploy/data-publisher-httpdeploy.yaml`