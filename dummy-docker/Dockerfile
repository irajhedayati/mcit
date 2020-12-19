# An image of Ubuntu with the tools I need to debug my applications on Kubernetes
FROM openjdk:8

RUN apt update && \
    apt install -y vim && \
    apt install -y software-properties-common

# Install confluent packages
RUN wget -qO - http://packages.confluent.io/deb/3.1/archive.key | apt-key add - && \
    add-apt-repository "deb [arch=amd64] http://packages.confluent.io/deb/3.1 stable main" && \
    apt update && apt install -y confluent-platform-oss-2.11

RUN apt install -y jq

# Install Scala and Ammonite-REPL
RUN wgere https://downloads.lightbend.com/scala/2.12.12/scala-2.12.12.deb && \
    dpkg -i scala-2.12.12.deb && \
    sh -c '(echo "#!/usr/bin/env sh" && curl -L https://github.com/lihaoyi/Ammonite/releases/download/2.3.8/2.13-2.3.8) > /usr/local/bin/amm && chmod +x /usr/local/bin/amm'

RUN mkdir /root/bin
ADD register-avro-schema.sh /root/bin/register-avro-schema.sh

RUN apt install -y telnet
