#!/bin/bash

DOWNLOAD_FILE=clicks.txt
DOWNLOAD_FILE2=buys.txt
LOCAL_INPUT=./input

# Remove old input data
echo ----------------------------------------------------------
echo Removing old input data
rm -r ${LOCAL_INPUT}

# Create directories
echo ----------------------------------------------------------
echo Creating input directory...
mkdir ${LOCAL_INPUT}

# Download input file
echo ----------------------------------------------------------
echo Retrieving input file...
wget https://s3-us-west-2.amazonaws.com/cs5253-project1/${DOWNLOAD_FILE}

# Move input into input dir
echo ----------------------------------------------------------
echo Moving input data into input dir...
mv ./${DOWNLOAD_FILE} ${LOCAL_INPUT}

# Download input2 file
echo ----------------------------------------------------------
echo Retrieving input file...
wget https://s3-us-west-2.amazonaws.com/cs5253-project1/${DOWNLOAD_FILE2}

# Move input2 into input dir
echo ----------------------------------------------------------
echo Moving input data into input dir...
mv ./${DOWNLOAD_FILE2} ${LOCAL_INPUT}

echo ----------------------------------------------------------
echo Finished
echo ----------------------------------------------------------
