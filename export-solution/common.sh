#! /bin/bash

TMP=/tmp/algos-export
stat $TMP 1>/dev/null 2>/dev/null
out=$?
if [ $out -ne 0 ]; then
	mkdir $TMP
fi

rm $ZIP_NAME

$JAVA_FILES

cp ../src/main/java/$JAVA_FILE.java $TMP/$JAVA_FILE_.java
sed 's/^.*package.*$//' < $TMP/$JAVA_FILE_.java > $TMP/$JAVA_FILE.java

zip -j $ZIP_NAME $TMP/Percolation.java $TMP/PercolationStats.java