#! /bin/bash

declare -A files=(
	["week1"]="weekone/Percolation.java weekone/PercolationStats.java"
	["week2"]="weektwo/Deque.java weektwo/Permutation.java weektwo/RandomizedQueue.java"
	["week3"]="weekthree/Point.java weekthree/BruteCollinearPoints.java weekthree/FastCollinearPoints.java"
	["week4"]="weekfour/Board.java weekfour/Solver.java"
	["week7"]="weekseven/Outcast.java weekseven/SAP.java weekseven/WordNet.java"
	["week8"]="weekeight/SeamCarver.java"
	["week9"]="weeknine/BaseballElimination.java"
	["week10"]="weekten/BoggleSolver.java weekten/AZTrie.java"
	["week11"]="weekeleven/CircularSuffixArray.java weekeleven/MoveToFront.java weekeleven/BurrowsWheeler.java"
)

# Directory the sripts is being run in.
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Checks if a file path exists.
function exists ()
{
	stat $1 1>/dev/null 2>/dev/null
	out=$?
	if [ $out -ne 0 ]; then
		return 1
	fi
	return 0 
}

ZIP="$1"
if exists $ZIP;
then
	echo "Zip file already exists"
	exit 1
fi

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

arr=( ${files["$2"]} )
# For each command line parameter after the first one.
for (( c=0; c < ${#arr[@]};	c++ ))
do
	path=${arr[$c]}
	echo "exporting $path"
	# Strip leading package directory segments from path.
	file=`echo ${path} | sed -e 's/.*\///'`
	cp "../src/main/java/${path}" "$TMP/${file}_"
	# Remove the package statements.
	sed 's/^.*package.*$//' < $TMP/${file}_ > $TMP/$file
	zip -j ${TMP}/$ZIP ${TMP}/$file
done

cp ${TMP}/$ZIP .