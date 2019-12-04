# An image of Ubuntu with the tools I need to debug my applicaitons on Kubernetes
FROM openjdk:8

RUN apt update && \
    apt install -y vim && \
    apt install -y software-properties-common

# Install confluent packages
RUN wget -qO - http://packages.confluent.io/deb/3.1/archive.key | apt-key add - && \
    add-apt-repository "deb [arch=amd64] http://packages.confluent.io/deb/3.1 stable main" && \
    apt update && apt install -y confluent-platform-oss-2.11