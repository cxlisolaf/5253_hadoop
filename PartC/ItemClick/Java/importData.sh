#!/bin/bash

DOWNLOAD_FILE=clicks.txt

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

# Copy input into input dir
echo ----------------------------------------------------------
echo Copying input data into input dir...
cp ./${DOWNLOAD_FILE} ./input

echo ----------------------------------------------------------
echo Finished
echo ----------------------------------------------------------
