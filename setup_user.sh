#!/bin/bash

USER_NAME=$1
sudo -u hdfs hadoop fs -mkdir /user/$USER_NAME
sudo -u hdfs hadoop fs -chown -R $USER_NAME:supergroup /user/$USER_NAME