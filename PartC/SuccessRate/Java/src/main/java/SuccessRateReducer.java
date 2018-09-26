import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.FloatWritable;




public class SuccessRateReducer extends Reducer<Text, BeanSetup, Text, FloatWritable> {

	// Set number of desired results (top n)git
	private static final int NUM_RESULTS = 10;

	// Create a sorted TreeMap at class level to store the results
	private TreeMap<Float, String> resultMap = new TreeMap<>();


	public void reduce(Text key, Iterable<BeanSetup> beans, Context context)
			throws IOException, InterruptedException {

		float sum_click = 0;
		float sum_buy = 0;
		float rate = 0;


		for (BeanSetup bean : beans) {

			sum_buy += bean.getBuy();
			sum_click += bean.getClick();

		}

		if(sum_click != 0) {
			rate = sum_buy / sum_click;
		}

		resultMap.put(rate, key.toString());

		// Keep only top NUM_RESULTS entries
		if (resultMap.size() > NUM_RESULTS) {
			resultMap.descendingMap().remove(resultMap.firstKey());
		}

		//context.write(key, new FloatWritable(rate));
	}

	@Override
	protected void cleanup(Context context)
			throws IOException, InterruptedException {

			// Iterate results in descending order and write to context
			for (Entry<Float, String> entry : resultMap.descendingMap().entrySet()) {
				context.write(new Text(entry.getValue()), new FloatWritable(entry.getKey()));
			}
	}


}



