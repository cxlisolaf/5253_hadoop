import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
 *  Reducer class sorting results in descending order
 */

public class TimeBlocksReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	// Objects to store output data
	private Text hour = new Text();
	private IntWritable price = new IntWritable();

	// Create a sorted TreeMap at class level to store the results
	private TreeMap<Integer, String> resultMap = new TreeMap<Integer, String>();

	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {

		int sum = 0;
		for (IntWritable value : values) {
			sum += value.get();
		}

		// Store result of this reducer in map
		resultMap.put(sum, key.toString());
	}

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		// Iterate results in descending order and write to context
		for (Entry<Integer, String> entry : resultMap.descendingMap().entrySet()) {
			hour.set(entry.getValue());
			price.set(entry.getKey());
			context.write(hour, price);
		}
	}
}
