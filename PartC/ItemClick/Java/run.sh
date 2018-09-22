#!/bin/bash

INPUT_FILE=clicks.txt
JAR_FILE=ItemClick.jar

# Remove Existing Output
echo ----------------------------------------------------------
echo Remove existing output files...
hdfs dfs -rm -r /output
rm -r ./output
rm output.tar.gz

# Create directories
echo ----------------------------------------------------------
echo Creating directories...
mkdir output
hdfs dfs -mkdir /input

# Move data file to hdfs
echo ----------------------------------------------------------
echo Copying input file to hdfs...
hdfs dfs -copyFromLocal ./input/${INPUT_FILE} /input

# Run Hadoop job
echo ----------------------------------------------------------
echo Running Hadoop job...
hadoop jar ./${JAR_FILE} ItemClick /input /output

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
