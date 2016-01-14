package com.paipai.www;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.hadoop.compression.lzo.LzopCodec;
import com.hadoop.mapreduce.LzoTextInputFormat;

public class GdmEccDailyRawPaipaiSimpleTradeJdMr {
	String dealDateStr;

	public static class GdmEccDailyRawPaipaiSimpleTradeJdM extends Mapper<Object, Text, Text, Text> {
		String dealDateStr = "2015-11-30";
		private Configuration	conf;
		@Override
		public void setup(Context context) throws IOException,
				InterruptedException
		{
			conf = context.getConfiguration();
			dealDateStr = conf.get("dealDate");
			
			context.getCounter("USEGROUPMAP", dealDateStr+"_test").increment(1);
		}
		@Override
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			String[] columns = value.toString().split("\t");
			
			if (columns.length < 103) {
				context.getCounter("USEGROUPMAP", "LenErrosInMapper").increment(1);
				//return;
			}
			
			if(null==dealDateStr || "".equals(dealDateStr)) {
				context.getCounter("USEGROUPMAP", "dealDate").increment(1);
				dealDateStr = "2015-11-30";
				//return;
			}
			String[] fleaf_classid_tmp = new String[] { "303914", "303924" };
			List<String> fleaf_classid = Arrays.asList(fleaf_classid_tmp);
			String[] fclassidL1_tmp = new String[] { "3119", "12001", "24590", "200021", "200022", "200023", "200024",
					"200082", "200110", "203981", "239929", "241286", "242370", "28039", "502178", "502184" };
			List<String> fclassidL1 = Arrays.asList(fclassidL1_tmp);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date Fpay_return_time;
			Date dealDate;
			Date Fseller_consignment_time;
			long days = 0;
			long day1 = 0;
			try {
				Fpay_return_time = sdf.parse(columns[32]);
				Fseller_consignment_time = sdf.parse(columns[34]);
				dealDate = sdf.parse(dealDateStr);
				
				day1 =  (dealDate.getTime() - Fseller_consignment_time.getTime())/ (24 * 60 * 60 * 1000);
				days = (dealDate.getTime() - Fpay_return_time.getTime()) / (24 * 60 * 60 * 1000);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				context.getCounter("USEGROUPMAP", "dateConvertError").increment(1);
				e.printStackTrace();
			}

			if ("非测试订单".equals(columns[97]) && "实物".equals(columns[21]) && !fleaf_classid.contains(columns[68])
					&& !fclassidL1.contains(columns[71]) && day1==0 && days < 3
					&& (Long.parseLong(columns[13]) < 1650000660 || Long.parseLong(columns[13]) > 1650000678)) {
				context.getCounter("USEGROUPMAP", "Insert").increment(1);
				context.write(new Text(columns[1]), value);
			}

		}
	}

	public static class GdmEccDailyRawPaipaiSimpleTradeJdR extends Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			for (Text value : values) {
				context.getCounter("USEGROUPREDUCER", "ReduceCount").increment(1);
				context.write(null, value);
			}
		}
	}

	public void run(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		
		//Job job = new Job(conf, "GdmEccDailyRawPaipaiSimpleTradeJdMr");
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		Job job = Job.getInstance(conf, "GdmEccDailyRawPaipaiSimpleTradeJdMr");
		System.out.println("[dealDate]: " + conf.get("dealDate"));
		System.out.println("[iDate]: " + conf.get("iDate"));
		dealDateStr = conf.get("dealDate");
		
		job.setJarByClass(com.paipai.www.GdmEccDailyRawPaipaiSimpleTradeJdMr.class);
		job.setMapperClass(GdmEccDailyRawPaipaiSimpleTradeJdM.class);

		job.setReducerClass(GdmEccDailyRawPaipaiSimpleTradeJdR.class);

		// TODO: specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setInputFormatClass(LzoTextInputFormat.class);

		job.setOutputFormatClass(TextOutputFormat.class);
		TextOutputFormat.setCompressOutput(job, true);
		TextOutputFormat.setOutputCompressorClass(job, LzopCodec.class);
		// TODO: specify input and output DIRECTORIES (not files)
		
		//dealDate="2015-11-30";
		String pathFix = "/user/mart_paipai/gdm.db/";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dealDate = null;
		try {
			dealDate = sdf.parse(dealDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(dealDate);
		
		for(int i = 0; i<=9; i++) {
			cal.add(Calendar.DATE, -i);
			String dateStr = sdf.format(cal.getTime());
			FileInputFormat.addInputPath(job,
					new Path(pathFix + "gdm_ecc_daily_raw_paipai_simple_trade_jd/dt=" + dateStr));
		}

		FileOutputFormat.setOutputPath(job,
				new Path(pathFix + "gdm_ecc_daily_raw_paipai_simple_trade_jd_mr/dt=" + dealDateStr));

		if (!job.waitForCompletion(true))
			return;
	}

	public static void main(String[] args) throws Exception {
		GdmEccDailyRawPaipaiSimpleTradeJdMr gedmr = new GdmEccDailyRawPaipaiSimpleTradeJdMr();
		gedmr.run(args);
	}
}
