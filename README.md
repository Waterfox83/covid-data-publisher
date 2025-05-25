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

## Secret Management

This project uses secrets for accessing external services and deployment. These secrets should not be hardcoded into the source code. Instead, they should be provided as environment variables at runtime.

### Required Secrets

-   `rapidApiKey`: Your API key for RapidAPI services used by the application. This is required for the application to fetch data from external APIs.
    Example: `export rapidApiKey="YOUR_RAPIDAPI_KEY"`

-   `DOCKER_PASSWORD`: The password for the Docker registry user (`waterfox83` in the current `auto-deploy.sh` script). This is required by the `auto-deploy.sh` script to push Docker images to the registry.
    Example: `export DOCKER_PASSWORD="YOUR_DOCKER_PASSWORD"`

### Local Development

For local development, you can create a file named `application-local.properties` in `src/main/resources/` to override properties defined in `application.properties`. To set the `rapidApiKey` locally, you could add the following line to `application-local.properties`:

```properties
rapidApiKey=YOUR_LOCAL_RAPIDAPI_KEY
```
**Note:** `application-local.properties` is included in `.gitignore` and should not be committed to the repository. Ensure your local API key is kept secure.