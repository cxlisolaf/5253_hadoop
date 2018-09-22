import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
 *  Reducer class based on cs5253 WordCount example
 */

public class ItemClickReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	private static final int NUM_RESULTS = 10;
	private Map<String, Integer> resultMap = new HashMap<String, Integer>();

	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {

		int sum = 0;
		for (IntWritable value : values) {
			sum += value.get();
		}
		resultMap.put(key.toString(), sum);
	}

	/*
	 * Sorting output in "Cleanup" based on example:
	 * 	http://andreaiacono.blogspot.com/2014/03/mapreduce-for-top-n-items.html and
	 */
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {

		List<Entry<String, Integer>> sortedResults = resultMap.entrySet().stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).collect(Collectors.toList());

		int counter = 0;
		for (Entry<String, Integer> entry : sortedResults) {
			context.write(new Text(entry.getKey()), new IntWritable(entry.getValue()));
			if (counter++ > NUM_RESULTS)
				break;
		}
	}
}
