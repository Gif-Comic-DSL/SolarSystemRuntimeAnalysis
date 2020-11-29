#!/bin/sh

PROJECT=$1 # path of the parent directory of the plugin
TARGET=$2 # path to target project
SOURCE=$3 # path to src within target proj
MAIN=$4 # path to main file within source directory


mkdir -p /tmp/solarUI
PLUGINOUT=/tmp/solarUI

javac -cp $PROJECT/target/classes:$TARGET/$SOURCE -Xplugin:MyPlugin -d $PLUGINOUT $TARGET/$SOURCE/$MAIN

#OUT=$PLUGINOUT${MAIN//$SOURCE/}
OUT=${MAIN%.*}

java -Duser.dir=$TARGET -cp $PLUGINOUT $OUT 2> /tmp/solarUI/outJson.txt

egrep "^{" /tmp/solarUI/outJson.txt > /tmp/solarUI/newJson.txt
FILE=/tmp/solarUI/outJson.txt
OUTFILE=/tmp/solarUI/newJson.txt
if grep -iq exception $FILE
then
	a=$(tail -1 $OUTFILE)
	b=${a%:*}
	c=': "exception"},'
	d="${b}${c}"
	echo $d >> $OUTFILE
fi

# trimming last, and add brackets
sed -i '' '$ s/.$//' $OUTFILE
sed -i '' '1 i\
[
' $OUTFILE
echo ']' >> $OUTFILE

mv $OUTFILE $PROJECT/SolarUI/input/project1.txt
live-server $PROJECT/SolarUI
