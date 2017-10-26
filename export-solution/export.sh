#! /bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

exists ()
{
	stat $1 1>/dev/null 2>/dev/null
	out=$?
	if [ $out -ne 0 ]; then
		return 1
	fi
	return 0 
}

ZIP="$1"
TMP="/tmp/algos-export"

if [ $(exists $TMP) != 1 ];then
	mkdir $TMP
fi

if [ $(exists $ZIP) -ne 1 ]
then
	rm $ZIP
fi

arr=( "$@" )

for (( c=1; c < ${#}; 	c++ ))
do
	path=${arr[$c]}
	file=`echo ${path} | sed -e 's/.*\///'`
	cp "../src/main/java/${path}" "$TMP/${file}_"
	sed 's/^.*package.*$//' < $TMP/${file}_ > $TMP/$file
	zip -j ${TMP}/$1 ${TMP}/$file
done

cp ${TMP}/$ZIP .