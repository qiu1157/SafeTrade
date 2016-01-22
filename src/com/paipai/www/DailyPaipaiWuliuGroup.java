package com.paipai.www;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.hadoop.compression.lzo.LzopCodec;
import com.hadoop.mapreduce.LzoTextInputFormat;

public class DailyPaipaiWuliuGroup {
	String dealDateStr;

	public static class MyMapper extends Mapper<Object, Text, Text, IntWritable> {
		private Configuration conf;
		private String dealDateStr = "2015-11-30";

		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			conf = context.getConfiguration();
			dealDateStr = conf.get("dealDate");
		}

		@Override
		protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			String[] columns = value.toString().split("\t");
			if (columns[89] != null && !"".equals(columns[89])) {
				//System.out.println(columns[89] + ",0," + columns[2]);
				context.write(new Text(columns[89] + ",0," + columns[2]), new IntWritable(1));
				context.write(new Text(columns[89] + ",1," + columns[14]), new IntWritable(1));
				context.write(new Text(columns[89] + ",2," + columns[11]), new IntWritable(1));
			}
		}

	}

	public static class MyReducer extends Reducer<Text, IntWritable, Text, Text> {
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.setup(context);
		}

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			int count0 = 0;
			int count1 = 0;
			int count2 = 0;
			String[] tag = key.toString().split(",");

				if ("0".equals(tag[1])) {
					count0++;
				}else if ("1".equals(tag[1])) {
					count1++;
				}else if ("2".equals(tag[1])) {
					count2++;
				}

			String tmp = tag[0] + "\t" + count0 + "\t" + count1 + "\t" + count2;
			//context.getCounter("USEGROUPREDUCER", "tmp==" + tmp).increment(1);
			Text t = new Text(tmp);
			context.write(null, t);
		}
	}

	public static class MyPartitioner extends Partitioner<Text, IntWritable> {
		@Override
		public int getPartition(Text key, IntWritable value, int numPartitions) {
			// TODO Auto-generated method stub
			int k = 0;
			String fwuliu_code = key.toString().split(",")[0];
			for (byte b : fwuliu_code.getBytes()) {
				k += b & 0xff;
			}
			System.out.println("k=="+k);
			return k % numPartitions;
		}

	};

/*	public void run(String[] args) throws IOException, ClassNotFoundException, InterruptedException, ParseException {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

	
		if (!job1.waitForCompletion(true))
			return;
	}*/
}
