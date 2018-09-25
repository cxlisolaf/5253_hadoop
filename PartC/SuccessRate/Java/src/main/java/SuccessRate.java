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
 *  	hadoop jar ./project1b-0.1.jar SuccessRate clicks buys output
 *
 * 	Example:
 * 		hadoop jar ./project1b-0.1.jar SuccessRate s3://cs5253-project1/PartC_3/input s3://cs5253-project1/PartC_3/output
 */

public class SuccessRate extends Configured {

	public static void main(String[] args) throws Exception{

		if (args.length != 2) {
			System.err.printf("Error: Missing argument(s): clicks buys output\n");
			System.exit(1);;
		}
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "word count");

		job.setJarByClass(SuccessRate.class);

		job.setMapperClass(SuccessRateMapper.class);
		job.setReducerClass(SuccessRateReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileInputFormat.addInputPath(job, new Path(args[1]));

		job.waitForCompletion(true);
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
}
