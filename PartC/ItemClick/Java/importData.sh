#!/bin/bash

DOWNLOAD_FILE=clicks.txt

# Create directories
echo ----------------------------------------------------------
echo Creating input directory...
mkdir input

# Download input file
echo ----------------------------------------------------------
echo Retrieving input file...
wget https://s3-us-west-2.amazonaws.com/cs5253-project1/${INPUT_FILE}

echo ----------------------------------------------------------
echo Finished
echo ----------------------------------------------------------
