#!/bin/bash

# sample UNIX shell script

INKSCAPE=/Applications/Inkscape.app/Contents/Resources/bin/inkscape


# array of icon sizes

for data in \
   "36 ldpi" \
   "48 mdpi" \
   "72 hdpi" \
   "96 xhdpi" \
   "144 xxhdpi" 
do
    size=`echo $data | awk '{print $1}'`
    screenType=`echo $data | awk '{print $2}'`

    outputDir=drawable-$screenType
    if [ ! -d $outputDir ]
    then
       mkdir $outputDir 
    fi

    $INKSCAPE --export-png=$outputDir/sunny.png --export-width=$size --without-gui sunny.svg
done
