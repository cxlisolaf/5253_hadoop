#!/bin/bash

INPUT_FILE1=clicks.txt
INPUT_FILE2=buys.txt
JAR_FILE=SuccessRate.jar
CLASS=SuccessRate
LOCAL_INPUT=./input
LOCAL_OUTPUT=./output.txt
HDFS_INPUT=/input
HDFS_OUTPUT=/output

# Remove Existing Output
echo ----------------------------------------------------------
echo Remove existing output files...
hadoop fs -rm -r ${HDFS_OUTPUT}
rm ${LOCAL_OUTPUT}

# Create directories
echo ----------------------------------------------------------
echo Creating directories...
hadoop fs -mkdir ${HDFS_INPUT}

# Move data file to hdfs
echo ----------------------------------------------------------
echo Copying input file to hdfs...
hadoop fs -copyFromLocal ${LOCAL_INPUT}/${INPUT_FILE1} ${HDFS_INPUT}
hadoop fs -copyFromLocal ${LOCAL_INPUT}/${INPUT_FILE2} ${HDFS_INPUT}

# Run Hadoop job
echo ----------------------------------------------------------
echo Running Hadoop job...
hadoop jar ./${JAR_FILE} ${CLASS} ${HDFS_INPUT} ${HDFS_OUTPUT}

# Move output data to local file system
echo ----------------------------------------------------------
echo Copying output files...
hadoop fs -copyToLocal ${HDFS_OUTPUT}/p* ${LOCAL_OUTPUT}

# Cleanup hdfs files
echo ----------------------------------------------------------
echo Cleaning up hdfs...
hadoop fs -rm -r ${HDFS_INPUT}
hadoop fs -rm -r ${HDFS_OUTPUT}

# Verifying results
echo ----------------------------------------------------------
echo Verifying results...
FILE_COUNT=`ls -1 ${LOCAL_OUTPUT} | wc -l`
LINE_COUNT=`cat ${LOCAL_OUTPUT} | wc -l`
echo Output File Count: ${FILE_COUNT}
echo Output Line Count: ${LINE_COUNT}

echo ----------------------------------------------------------
echo Finished
echo ----------------------------------------------------------
