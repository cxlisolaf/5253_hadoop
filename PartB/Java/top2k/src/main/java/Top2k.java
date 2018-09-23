import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Top2k extends Configured {

	public static void main(String[] args) throws Exception{

		if (args.length != 2) {
			System.err.printf("Error: Missing argument(s): inputPath outputPath\n");
			System.exit(1);;
		}
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "word count");
		job.setJarByClass(Top2k.class);
		job.setMapperClass(Top2kMapper.class);
		job.setReducerClass(Top2kReducer.class);
		job.setNumReduceTasks(1);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
}
