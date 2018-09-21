import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.net.URI;
import java.util.Set;
import org.apache.log4j.Logger;



/*
 *  Mapper class based on cs5253 WordCount example
 */

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private static final Logger LOG = Logger.getLogger(WordCountMapper.class);
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    private String input;

    private Set<String> stopWords = new HashSet<String> ();

    @Override
    protected void setup(Mapper.Context context)
        throws IOException,InterruptedException {

        URI[] stopWordFile = context.getCacheFiles();

        //LOG.info("Added file to the distributed cache: " + stopWordFile);
        BufferedReader br = new BufferedReader(new FileReader(new File(stopWordFile[0].getPath()).getName()));

        String stopWord;

        while ((stopWord = br.readLine()) != null) {
            stopWords.add(stopWord.toLowerCase().replaceAll("[^a-z ]",""));
        }

    }

    public void map(LongWritable key, Text input, Context context)
			throws IOException, InterruptedException {


        String line = input.toString().toLowerCase();
        line = line.replaceAll("[^a-z ]", "");

        String delimiter = " ";
        StringTokenizer tokens = new StringTokenizer(line,delimiter);

        while (tokens.hasMoreTokens()) {

            String wordString = tokens.nextToken();
            if (!stopWords.contains(wordString) && wordString != "") {
                word.set(wordString);
                context.write(word, one);
            }

        }
    }
}
