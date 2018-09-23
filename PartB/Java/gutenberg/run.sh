#!/bin/bash

JAR_FILE=wordcount.jar
TOP2K_JAR_FILE=top2k.jar
CLASS=WordCount
LOCAL_INPUT=./input
LOCAL_OUTPUT=./output
HDFS_INPUT=s3://csci5253-gutenberg-dataset/
HDFS_OUTPUT1=/output_tmp
HDFS_OUTPUT2=/output
HDFS_STOPWORD_PATH=/user
GZIP_OUTPUT=output.tar.gz

# Remove Existing Output
echo ----------------------------------------------------------
echo Remove existing output files...
hdfs dfs -rm -r ${HDFS_OUTPUT1}
hdfs dfs -rm -r ${HDFS_OUTPUT2}
rm -r ${LOCAL_OUTPUT}
rm ${GZIP_OUTPUT}

# Create directories
echo ----------------------------------------------------------
echo Creating directories...
mkdir ${LOCAL_OUTPUT}

# Move stopword file to hdfs
echo ----------------------------------------------------------
echo Copying stopword file to hdfs...
hdfs dfs -copyFromLocal ../stopword.txt ${HDFS_STOPWORD_PATH}

# Run WordCount Hadoop job
echo ----------------------------------------------------------
echo Running WordCount Hadoop job...
hadoop jar ../${JAR_FILE} ${CLASS} ${HDFS_INPUT} ${HDFS_OUTPUT1} ${HDFS_STOPWORD_PATH}/stopword.txt

# Run Top2K Hadoop job
echo ----------------------------------------------------------
echo Running Top2K Hadoop job...
hdfs dfs -rm ${HDFS_OUTPUT1}/_S*
hadoop jar ../${TOP2K_JAR_FILE} ${CLASS} ${HDFS_OUTPUT1} ${HDFS_OUTPUT2}

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
hdfs dfs -rm -r ${HDFS_OUTPUT1}
hdfs dfs -rm -r ${HDFS_OUTPUT2}

# Verifying results
echo ----------------------------------------------------------
echo Verifying results...
FILE_COUNT=`ls -1 ${LOCAL_OUTPUT}/p* | wc -l`
LINE_COUNT=`cat ${LOCAL_OUTPUT}/p* | wc -l`
echo Output File Count: ${FILE_COUNT}
echo Output Line Count: ${LINE_COUNT}

echo ----------------------------------------------------------
echo Finished
echo ----------------------------------------------------------
