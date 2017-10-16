#! /bin/bash

TMP=/tmp/algos-export
stat $TMP 1>/dev/null 2>/dev/null
out=$?
if [ $out -ne 0 ]; then
	mkdir $TMP
fi

rm Percolation.zip

cp ../src/main/java/weekone/Percolation.java $TMP/Percolation_.java
cp ../src/main/java/weekone/PercolationStats.java $TMP/PercolationStats_.java

sed 's/^.*package.*$//' < $TMP/Percolation_.java > $TMP/Percolation.java
sed 's/^.*package.*$//' < $TMP/PercolationStats_.java > $TMP/PercolationStats.java

zip -j Percolation.zip $TMP/Percolation.java $TMP/PercolationStats.java