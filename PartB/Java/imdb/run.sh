#!/bin/bash

INPUT_FILE=quotes.list
JAR_FILE=wordcount.jar
CLASS=WordCount
LOCAL_INPUT=./input
LOCAL_OUTPUT=./output
HDFS_INPUT=/input
HDFS_OUTPUT=/output
HDFS_STOPWORD_PATH=/user
GZIP_OUTPUT=output.tar.gz

# Remove Existing Output
echo ----------------------------------------------------------
echo Remove existing output files...
hdfs dfs -rm -r ${HDFS_OUTPUT}
rm -r ${LOCAL_OUTPUT}
rm ${GZIP_OUTPUT}

# Create directories
echo ----------------------------------------------------------
echo Creating directories...
mkdir ${LOCAL_OUTPUT}
hdfs dfs -mkdir ${HDFS_INPUT}

# Move data file to hdfs
echo ----------------------------------------------------------
echo Copying input file to hdfs...
hdfs dfs -copyFromLocal ${LOCAL_INPUT}/${INPUT_FILE} ${HDFS_INPUT}

# Move stopword file to hdfs
echo ----------------------------------------------------------
echo Copying stopword file to hdfs...
hdfs dfs -copyFromLocal ../stopword.txt ${HDFS_STOPWORD_PATH}

# Run Hadoop job
echo ----------------------------------------------------------
echo Running Hadoop job...
hadoop jar ../${JAR_FILE} ${CLASS} ${HDFS_INPUT} ${HDFS_OUTPUT} ${HDFS_STOPWORD_PATH}/stopword.txt

# Move output data to local file system
echo ----------------------------------------------------------
echo Copying output files...
hdfs dfs -copyToLocal ${HDFS_OUTPUT}/* ${LOCAL_OUTPUT}

# Create compressed output file
echo ----------------------------------------------------------
echo Compressing output files...
cd ${LOCAL_OUTPUT} && tar -zcvf ../${GZIP_OUTPUT} part-* && cd -

# Cleanup hdfs files
echo ----------------------------------------------------------
echo Cleaning up hdfs...
hdfs dfs -rm -r ${HDFS_INPUT}
hdfs dfs -rm -r ${HDFS_OUTPUT}

echo ----------------------------------------------------------
echo Finished
echo ----------------------------------------------------------
