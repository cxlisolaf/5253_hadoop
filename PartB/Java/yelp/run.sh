#!/bin/bash

DOWNLOAD_FILE=yelp_academic_dataset_review.txt.zip
INPUT_FILE=yelp_academic_dataset_review.txt

# Remove Existing Output
echo ----------------------------------------------------------
echo Remove existing output files...
hdfs dfs -rm -r /output
rm -r ./output
rm output.tar.gz

# Create directories
echo ----------------------------------------------------------
echo Creating directories...
mkdir input
mkdir output
hdfs dfs -mkdir /input

# Download input file
echo ----------------------------------------------------------
echo Retrieving input file...
wget https://s3-us-west-2.amazonaws.com/cs5253-project1/${INPUT_FILE}

# Decompress input file
echo ----------------------------------------------------------
echo Decompressing input file...
unzip -j ${DOWNLOAD_FILE}
rm ${DOWNLOAD_FILE}

# Move data file to hdfs
echo ----------------------------------------------------------
echo Moving input file to hdfs...
hdfs dfs -moveFromLocal ./${INPUT_FILE} /input

# Run Hadoop job
echo ----------------------------------------------------------
echo Running Hadoop job...
hadoop jar ../project1b-0.1.jar WordCount /input /output

# Move output data to local file system
echo ----------------------------------------------------------
echo Copying output files...
hdfs dfs -copyToLocal /output/* ./output

# Create compressed output file
echo ----------------------------------------------------------
echo Compressing output files...
cd output && tar -zcvf ../output.tar.gz part-* && cd -

# Cleanup hdfs files
echo ----------------------------------------------------------
echo Cleaning up hdfs...
hdfs dfs -rm -r /input
hdfs dfs -rm -r /output

echo ----------------------------------------------------------
echo Finished
echo ----------------------------------------------------------
