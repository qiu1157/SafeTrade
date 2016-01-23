package com.paipai.www;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.paipai.www.DailyPaipaiWuliuGroup.MyMapper;
import com.paipai.www.DailyPaipaiWuliuGroup.MyPartitioner;
import com.paipai.www.DailyPaipaiWuliuGroup.MyReducer;

public class DailyPaipaiWuliuGroup2 {
	String dealDateStr;

	public void run(String[] args)
			throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException, ParseException {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

		dealDateStr = conf.get("dealDate");
		/*
		 * 删除目录
		 */
		FileSystem fs = FileSystem.get(conf);
		Path job1OutPath = new Path("hdfs://master.hadoop:9000/tmp/app_daily_paipai_wuliu_group/out");
		Path job2OutPath = new Path(
				"hdfs://master.hadoop:9000/user/hive/warehouse/app.db/app_daily_paipai_wuliu_group/dt=" + dealDateStr);
		if (fs.exists(job1OutPath)) {
			fs.delete(job1OutPath, true);
		}
		
		if (fs.exists(job2OutPath)) {
			fs.delete(job2OutPath, true);
		}		
		Job job1 = Job.getInstance(conf, "DailyPaipaiWuliuGroup");
		job1.setJarByClass(com.paipai.www.DailyPaipaiWuliuGroup.class);

		job1.setMapperClass(MyMapper.class);
		job1.setPartitionerClass(MyPartitioner.class);
		job1.setReducerClass(MyReducer.class);
		// TODO: specify output types
		job1.setMapOutputKeyClass(Text.class);
		job1.setMapOutputValueClass(IntWritable.class);
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(Text.class);

		// job.setInputFormatClass(LzoTextInputFormat.class);
		// job.setOutputFormatClass(TextOutputFormat.class);
		// TextOutputFormat.setCompressOutput(job, true);
		// TextOutputFormat.setOutputCompressorClass(job, LzopCodec.class);

		// TODO: specify input and output DIRECTORIES (not files)
		String pathFix = "hdfs://master.hadoop:9000/user/hive/warehouse/app.db/";
		FileInputFormat.addInputPath(job1,
				new Path(pathFix + "gdm_ecc_daily_raw_paipai_simple_trade_jd_mr/dt=" + dealDateStr));

		FileOutputFormat.setOutputPath(job1, job1OutPath);

		Job job2 = Job.getInstance(conf, "DailyPaipaiWuliuGroup2");
		job2.setJarByClass(com.paipai.www.DailyPaipaiWuliuGroup2.class);
		job2.setMapperClass(MyD2Mapper.class);
		job2.setReducerClass(MyD2Reducer.class);

		// TODO: specify output types
		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job2, new Path("hdfs://master.hadoop:9000/tmp/app_daily_paipai_wuliu_group/out"));
		FileOutputFormat.setOutputPath(job2, job2OutPath);

		ControlledJob cJob1 = new ControlledJob(job1.getConfiguration());
		ControlledJob cJob2 = new ControlledJob(job2.getConfiguration());

		cJob2.addDependingJob(cJob1);

		JobControl jobControl = new JobControl("RecommendationJob");
		jobControl.addJob(cJob1);
		jobControl.addJob(cJob2);

		cJob1.setJob(job1);
		cJob2.setJob(job2);

		Thread jobControlThread = new Thread(jobControl);
		jobControlThread.start();
		while (!jobControl.allFinished()) {
			Thread.sleep(500);
		}

		jobControl.stop();
	}

	public static void main(String[] args) throws Exception {
		DailyPaipaiWuliuGroup2 dpwg2 = new DailyPaipaiWuliuGroup2();
		dpwg2.run(args);
	}

	public static class MyD2Mapper extends Mapper<Object, Text, Text, Text> {

		@Override
		protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			System.out.println("value==" + value.toString());
			String[] columns = value.toString().split("\t");
			context.write(new Text(columns[0]), new Text(columns[1] + "\t" + columns[2] + "\t" + columns[3]));
		}

	}

	public static class MyD2Reducer extends Reducer<Text, Text, Text, Text> {

		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			int count_Fdeal_id = 0;
			int count_Fbuyer_uin = 0;
			int count_Fseller_uin = 0;
			for (Text value : values) {
				String[] columns = value.toString().split("\t");
				int fdeal_id = Integer.parseInt(columns[0]);
				count_Fdeal_id += fdeal_id;
				int fbuyer_uin = Integer.parseInt(columns[1]);
				count_Fbuyer_uin += fbuyer_uin;
				int fseller_uin = Integer.parseInt(columns[2]);
				count_Fseller_uin += fseller_uin;
			}
			context.write(key, new Text(count_Fdeal_id + "\t" + count_Fbuyer_uin + "\t" + count_Fseller_uin));
		}

	}
}
