#!/bin/bash
#git ls-files | xargs cat | wc -l

for f in "`git ls-files`"
do 
	value=`cat "$f" | wc -l;`
done