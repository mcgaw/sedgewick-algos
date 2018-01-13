#! /bin/bash
export MAVEN_OPTS="-ea"
mvnDebug -Dtest=$1 test