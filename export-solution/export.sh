#! /bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

function exists ()
{
	#echo "Checking existence of $1"
	stat $1 1>/dev/null 2>/dev/null
	out=$?
	if [ $out -ne 0 ]; then
		return 1
	fi
	return 0 
}

ZIP="$1"
TMP="/tmp/algos-export"
if ! exists $TMP;
then
	echo "Creating temp directory..."
	mkdir $TMP
fi

if exists "$TMP/$ZIP";
then
	rm $ZIP
fi

arr=( "$@" )

# For each command line parameter after the first one.3
for (( c=1; c < ${#}; 	c++ ))
do
	path=${arr[$c]}
	file=`echo ${path} | sed -e 's/.*\///'`
	cp "../src/main/java/${path}" "$TMP/${file}_"
	sed 's/^.*package.*$//' < $TMP/${file}_ > $TMP/$file
	zip -j ${TMP}/$ZIP ${TMP}/$file
done

cp ${TMP}/$ZIP .