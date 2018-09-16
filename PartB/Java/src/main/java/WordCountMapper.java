import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 *  Mapper class based on cs5253 WordCount example
 */

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    
    public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		String delimiter = " ";
		StringTokenizer tokens = new StringTokenizer(value.toString(), delimiter);
		
		while(tokens.hasMoreTokens()){
			word.set(tokens.nextToken());
			context.write(word, one);
		}
	}
}
