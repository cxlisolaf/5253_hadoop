#!/bin/bash

INPUT_FILE=big.txt
JAR_FILE=wordcount.jar

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

# Move stopword file to hdfs
echo ----------------------------------------------------------
echo Copying stopword file to hdfs...
hdfs dfs -copyFromLocal ../stopword.txt /user

# Run Hadoop job
echo ----------------------------------------------------------
echo Running Hadoop job...
hadoop jar ../${JAR_FILE} WordCount /input /output /user/stopword.txt

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
