#!/bin/sh

FILE=$1

> newJson.txt

egrep "^{" $FILE > newJson.txt

if grep -iq exception $FILE
then
	a=$(tail -1 newJson.txt)
	b=${a%:*}
	c=': "exception"},'
	d="${b}${c}"
	echo $d >> newJson.txt
fi 

# trimming last, and add brackets
sed -i '' '$ s/.$//' newJson.txt
sed -i '' '1 i\
[
' newJson.txt
echo ']' >> newJson.txt


