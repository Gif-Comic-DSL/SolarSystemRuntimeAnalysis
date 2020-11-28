#! /bin/bash 
clear
echo "----------------Starting Script----------------"


# 1: path to project2
# path to target proj
# subpath for main class

#

echo "-----------------Running Plugin-----------------"
# replace path to plugin with actual path to plugin 
javac -cp {{$1}/target/classes:{$2}} -Xplugin:MyPlugin -d /tmp/target_modified_out {{$2}/{$3}}

echo "--------Running instrumented target code----------"
java -cp /tmp/target_modified_out $SUBPATH_MAIN 2> /tmp/logs_for_solar.txt
mv /tmp/logs_for_solar.txt {$1}

echo "----------------Launching SolarUI-----------------"
# launch solarui .html

echo
echo "---------Finished Script---------"

chmod +x runSolarUICodeVis.sh
