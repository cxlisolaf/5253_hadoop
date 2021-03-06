import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
 *  Reducer class storing only Top N results
 */

public class Top2kReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	// Set number of desired results (top n)
	private static final int NUM_RESULTS = 2000;

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

		// Keep only top NUM_RESULTS entries
		if (resultMap.size() > NUM_RESULTS) {
			resultMap.descendingMap().remove(resultMap.firstKey());
		}
	}

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		// Iterate results in descending order and write to context
		for (Entry<Integer, String> entry : resultMap.descendingMap().entrySet()) {
			context.write(new Text(entry.getValue()), new IntWritable(entry.getKey()));
		}
	}
}
