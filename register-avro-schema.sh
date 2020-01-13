#!/bin/bash

if [[ $# < 3 ]]; then
    echo "Usage: register-avro-schema.sh <schema registry url> <subject> <avsc file path>"
    exit 1
fi
SCHEMA_REGISTRY_URL=$1
SUBJECT=$2
FILE_PATH=$3

echo "Schema registry URL: ${SCHEMA_REGISTRY_URL}"
echo "Subject: ${SUBJECT}"
echo "File path: ${FILE_PATH}"

SCHEMA='{"schema": "'$(cat ${FILE_PATH} | tr '\n' '\r' | sed  's/"/\\"/g' | sed 's/\s//g')'"}'
echo $SCHEMA > ./tmp.json
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json"  --data @./tmp.json ${SCHEMA_REGISTRY_URL}/subjects/${SUBJECT}/versions