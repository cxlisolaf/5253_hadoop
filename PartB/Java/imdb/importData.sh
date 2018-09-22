#!/bin/bash

DOWNLOAD_FILE=quotes.list.gz
INPUT_FILE=quotes.list

# Remove old input data
echo ----------------------------------------------------------
echo Removing old input data
rm -r ./input

# Create directories
echo ----------------------------------------------------------
echo Creating input directory...
mkdir ./input

# Download input file
echo ----------------------------------------------------------
echo Retrieving input file...
wget https://s3-us-west-2.amazonaws.com/cs5253-project1/${DOWNLOAD_FILE}

# Decompress input file
echo ----------------------------------------------------------
echo Decompressing input file...
gunzip ${DOWNLOAD_FILE}
rm ${DOWNLOAD_FILE}

# Copy input into input dir
echo ----------------------------------------------------------
echo Moving input data into input dir...
mv ./${INPUT_FILE} ./input

echo ----------------------------------------------------------
echo Finished
echo ----------------------------------------------------------
