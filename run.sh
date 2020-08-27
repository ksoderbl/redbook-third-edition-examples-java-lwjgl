#!/bin/sh

# To run e.g. example 1_2 do ./run.sh 1_2

if [ -n "$1" ]
then
	EXAMPLE="$1"
else
	EXAMPLE=1_1
fi

java -cp .:res:lib/jars/lwjgl.jar:lib/jars/lwjgl_util.jar:lib/jars/slick-util.jar:lib/jars/PNGDecoder.jar:src: -Djava.library.path=lib/natives/  examples/Example_$EXAMPLE
