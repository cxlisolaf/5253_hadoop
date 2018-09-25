import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.hdfs.protocol.SnapshotInfo;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

/*
 *  Mapper class to obtain Item ID from click data
 *
 *	Input data format: Session Id, Timestamp, Item Id, Price, Quantity
 */

public class SuccessRateMapper extends Mapper<LongWritable, Text, Text, BeanSetup> {

	BeanSetup bean = new BeanSetup();
	Text item = new Text();


	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		String[] fields = value.toString().split(",");

		FileSplit inputSplit = (FileSplit) context.getInputSplit();

		String filename = inputSplit.getPath().getName();

		String i_id = "";

		if(filename.startsWith("click")){
			i_id = fields[2];
			bean.set(i_id);
			bean.setClick(1);
			bean.setBuy(0);

		}else{
			i_id = fields[2];
			bean.set(i_id);
			bean.setBuy(1);
			bean.setClick(0);
		}

		item.set(i_id);
		context.write(item,bean);




	}
}
