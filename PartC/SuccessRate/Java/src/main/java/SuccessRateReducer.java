import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.FloatWritable;


/*
 *  Reducer class sorting results in descending order
 */

public class SuccessRateReducer extends Reducer<Text, Text, Text, Text> {

	// Set number of desired results (top n)git
	private static final int NUM_RESULTS = 10;

	// Create a sorted TreeMap at class level to store the results
	private TreeMap<Float, String> resultMap = new TreeMap<>();


	public void reduce(Text key, Iterable<BeanSetup> beans, Context context)
			throws IOException, InterruptedException {

		float sum_click = 0;
		float sum_buy = 0;

		for (BeanSetup bean : beans) {

			sum_buy += bean.getBuy();
			sum_click += bean.getClick();

		}

		resultMap.put(sum_buy / sum_click, key.toString());

		// Keep only top NUM_RESULTS entries
		if (resultMap.size() > NUM_RESULTS) {
			resultMap.descendingMap().remove(resultMap.firstKey());
		}
	}

	protected void cleanup(Context context) throws IOException, InterruptedException {
		// Iterate results in descending order and write to context
		for (Entry<Float, String> entry : resultMap.descendingMap().entrySet()) {
			context.write(new Text(entry.getValue()), new Text(entry.getKey().toString()));
		}
	}


}



