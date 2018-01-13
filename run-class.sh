#! /bin/bash
export MAVEN_OPTS="-ea"
mvn exec:java -Dexec.mainClass=$1 -Dexec.args="$@"
