#!/bin/sh

PLUGIN=$1
SOURCE=$2
MAIN=$3

mkdir -p pluginOut
PLUGINOUT=${SOURCE%/*}/pluginOut

javac -cp $PLUGIN:$SOURCE -Xplugin:MyPlugin -d $PLUGINOUT $MAIN

OUT=$PLUGINOUT${MAIN//$SOURCE/}
OUT=${OUT%.*}

java -cp $OUT 2> outJson.txt
echo $OUT




