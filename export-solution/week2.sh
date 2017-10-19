#! /bin/bash

TMP=/tmp/algos-export
stat $TMP 1>/dev/null 2>/dev/null
out=$?
if [ $out -ne 0 ]; then
	mkdir $TMP
fi

rm Queues.zip

cp ../src/main/java/weektwo/Deque.java $TMP/Deque_.java
cp ../src/main/java/weektwo/Permutation.java $TMP/Permutation_.java
cp ../src/main/java/weektwo/RandomizedQueue.java $TMP/RandomizedQueue_.java

sed 's/^.*package.*$//' < $TMP/Deque_.java > $TMP/Deque.java
sed 's/^.*package.*$//' < $TMP/Permutation_.java > $TMP/Permutation.java
sed 's/^.*package.*$//' < $TMP/RandomizedQueue_.java > $TMP/RandomizedQueue.java

zip -j Queues.zip $TMP/Deque.java $TMP/Permutation.java $TMP/RandomizedQueue.java