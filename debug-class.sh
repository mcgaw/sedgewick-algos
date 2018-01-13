#! /bin/bash
export MAVEN_OPTS="-ea"
mvnDebug exec:java -Dexec.mainClass=$1 -Dexec.args="$@"