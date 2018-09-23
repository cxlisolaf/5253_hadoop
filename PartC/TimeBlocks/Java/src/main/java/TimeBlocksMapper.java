import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 *  Mapper class to obtain Item ID from click data
 *
 *	Input data format: Session Id, Timestamp, Item Id, Price, Quantity
 */

public class TimeBlocksMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	public void map(LongWritable key, Text input, Context context) throws IOException, InterruptedException {

		// Get the hour and price from the input string
		String[] fields = input.toString().split(",");
		String hour = fields[1].split("T")[1].split(":")[0];
		int price = Integer.valueOf(fields[3]);

		// Write hour and price to context
		context.write(new Text(hour), new IntWritable(price));
	}
}
