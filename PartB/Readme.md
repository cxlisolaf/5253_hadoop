Project directories:

PartB/
    Java/
        gutenberg/
            output.tar.gz   - mapreduce output
            run.sh          - run mapreduce job
        holmes/
            importData.sh   - download test data to ./input (if needed)
            output.tar.gz   - mapreduce output
            run.sh          - run mapreduce job
        imdb/
            importData.sh   - download test data to ./input (if needed)
            output.tar.gz   - mapreduce output
            run.sh          - run mapreduce job
        yelp/
            output.tar.gz   - mapreduce output
            run.sh          - run mapreduce job

Notes:
    Gutenberg and Yelp jobs input data from supplied s3 buckets. 
