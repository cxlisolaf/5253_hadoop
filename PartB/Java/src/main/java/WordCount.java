import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
 *  WordCount class based on cs5253 WordCount example
 *
 *  Usage:
 *  	hadoop jar ./project1b-0.1.jar WordCount inputPath outputPath stopwordfilepath
 *
 * 	Example:
 * 		hadoop jar ./project1b-0.1.jar WordCount s3://cs5253-project1/PartB/input s3://cs5253-project1/PartB/output xxx/stopword.txt
 */

public class WordCount extends Configured {

	public static void main(String[] args) throws Exception{

		if (args.length != 3) {
			System.err.printf("Error: Missing argument(s): inputPath outputPath\n");
			System.exit(1);;
		}

		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "word count");
		job.setJarByClass(WordCount.class);
		job.setMapperClass(WordCountMapper.class);
		job.setReducerClass(WordCountReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.addCacheFile(new Path(args[2]).toUri());

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
