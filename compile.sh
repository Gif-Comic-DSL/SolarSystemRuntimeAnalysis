#!/bin/sh

PROJECT=$1 # path of the parent directory of the plugin
SOURCE=$2 # path to source directory
MAIN=$3 # path from soruce directory to main class


mkdir -p /tmp/solarUI
PLUGINOUT=/tmp/solarUI/

javac -cp $PROJECT/target/classes:$SOURCE -Xplugin:MyPlugin -d $PLUGINOUT $SOURCE/$MAIN

#OUT=$PLUGINOUT${MAIN//$SOURCE/}
OUT=${MAIN%.*}

java -cp $PLUGINOUT $OUT 2> /tmp/solarUI/outJson.txt

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
rm $FILE

live-server --open=$PROJECT/SolarUI



