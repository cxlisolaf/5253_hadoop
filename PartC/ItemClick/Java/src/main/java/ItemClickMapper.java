import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 *  Mapper class to obtain Item ID from click data
 *
 *	Input data format: Session Id, Timestamp, Item Id, Category
 */

public class ItemClickMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	private final static IntWritable one = new IntWritable(1);
	
	// Text object to store item Ids
	private Text itemId = new Text();

	public void map(LongWritable key, Text input, Context context) throws IOException, InterruptedException {

		// Get the itemId from the input string
		String[] fields = input.toString().split(",");
		itemId.set(fields[2]);
		
		// Write itemId to context
		context.write(itemId, one);
	}
}
