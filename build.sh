#!/bin/bash

set -e

mvn -DskipTests clean package

docker build -f src/main/docker/Dockerfile.fast-jar -t  quay.io/rhdevelopers/fruityvice-proxy:jvm .

mvn -DskipTests package -Pnative -Dquarkus.native.container-build=true

docker build -f src/main/docker/Dockerfile.native -t quay.io/rhdevelopers/fruityvice-proxy:native .

docker tag quay.io/rhdevelopers/fruityvice-proxy:native quay.io/rhdevelopers/fruityvice-proxy

docker push quay.io/rhdevelopers/fruityvice-proxy:native
docker push quay.io/rhdevelopers/fruityvice-proxy:jvm
docker push quay.io/rhdevelopers/fruityvice-proxy