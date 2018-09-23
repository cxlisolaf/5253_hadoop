#!/bin/bash

INPUT_FILE=buys.txt
JAR_FILE=timeblocks.jar
CLASS=TimeBlocks
LOCAL_INPUT=./input
LOCAL_OUTPUT=./output
HDFS_INPUT=/input
HDFS_OUTPUT=/output
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

# Run Hadoop job
echo ----------------------------------------------------------
echo Running Hadoop job...
hadoop jar ./${JAR_FILE} ${CLASS} ${HDFS_INPUT} ${HDFS_OUTPUT}

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
