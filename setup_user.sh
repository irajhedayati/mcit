#!/bin/bash

GROUP=$1
USER_NAME=$2

sudo -u hdfs hadoop fs -mkdir /user/$GROUP/$USER_NAME
sudo -u hdfs hadoop fs -chown -R $USER_NAME:supergroup /user/$GROUP/$USER_NAME
