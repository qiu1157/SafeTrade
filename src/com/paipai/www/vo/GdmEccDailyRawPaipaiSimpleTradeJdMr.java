package com.paipai.www.vo;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class GdmEccDailyRawPaipaiSimpleTradeJdMr implements WritableComparable<GdmEccDailyRawPaipaiSimpleTradeJdMr> {
	int fdate_cd;
	Long ftrade_id;
	Long fdeal_id;
	Long fpay_id;
	Long fwuliu_id;
	Long ftrade_refund_id;
	Long fitem_id;
	Long fseller_uin;
	String fseller_name;
	Long fshopid;
	String fshop_name;
	String fshop_classid;
	int fshop_bz;
	Long fbuyer_uin;
	String fbuyer_name;
	String finval_pay_id;
	int fdeal_buy_type_sub;
	String fdeal_buy_type_sub_name;
	String fdeal_state;
	String fdeal_pay_type;
	String fdeal_buy_type;
	String fitem_type;
	String ftrade_state;
	String fdeal_payinfo_type;
	String ftrade_propertymask;
	String ftrade_propertymask2;
	String fpropertymask;
	Long ftrade_propertymask2_id;
	Long forignalpropertymask;
	Long fwho_pay_shippingfee;
	String fdeal_type;
	String fdeal_create_time;
	String fpay_return_time;
	String fmark_nostock_time;
	String fseller_consignment_time;
	String fclose_time;
	String frecvfee_return_time;
	String fdeal_end_time;
	String frefund_req_time;
	String frefund_end_time;
	String fseller_refuse_time;
	String fseller_agree_giveback_time;
	String fbuyer_consignment_time;
	String fwuliu_gen_time;
	String fsend_time;
	String fexpect_arrival_time;
	String frecv_time;
	String fcod_sign_time;
	String fcod_sign_return_time;
	Long fdeal_item_num;
	Long fitem_price;
	Long fdeal_item_score;
	Long fdeal_item_count;
	Long fitem_original_price;
	Long fdiscount_fee;
	Long fdeal_pay_fee_shipping;
	Long fcoupon_fee;
	Long fdeal_pay_fee_ticket;
	Long fitem_adjust_price;
	Long fdeal_pay_fee_commission;
	Long fdeal_voucher_fee;
	Long fitem_fee_total;
	Long fdeal_pay_fee_total;
	Long fdeal_pay_fee;
	Long fitem_shipping_fee;
	String frecv_city_name;
	String frecv_province_name;
	String frecv_country_name;
	Long fleaf_classid;
	Long fclassidl0;
	String fclassnamel0;
	Long fclassidl1;
	String fclassnamel1;
	Long fclassidl2;
	String fclassnamel2;
	Long fclassidl3;
	String fclassnamel3;
	Long fclassidl4;
	String fclassnamel4;
	int fcreate_bz;
	int fpay_bz;
	int fseller_con_bz;
	int fvitual_bz;
	int frefund_bz;
	int fwuliu_rev_bz;
	int fbuyer_rev_bz;
	int fwuliu_company_id;
	int frefund_to_buyer;
	int frefund_to_seller;
	String fwuliu_code;
	String fwuliu_company;
	String fwuliu_type;
	String fitem_name;
	int ftype_bz;
	int fvirtual_bz;
	String fspec_id;
	String finval_create_id;
	String ftest_id;
	String fmalicious_type;
	int fold_bz;
	String app_uuid;
	String dt;

	public GdmEccDailyRawPaipaiSimpleTradeJdMr(String line) {
		set(line);
	}
	
	public void set(String line) {
		String[] columns = line.split("\t");
		
		fdate_cd=Integer.parseInt(columns[0]);
		ftrade_id=Long.parseLong(columns[1]);
		fdeal_id=Long.parseLong(columns[2]);
		fpay_id=Long.parseLong(columns[3]);
		fwuliu_id=Long.parseLong(columns[4]);
		ftrade_refund_id=Long.parseLong(columns[5]);
		fitem_id=Long.parseLong(columns[6]);
		fseller_uin=Long.parseLong(columns[7]);
		fseller_name=columns[8];
		fshopid=Long.parseLong(columns[9]);
		fshop_name=columns[10];
		fshop_classid=columns[11];
		fshop_bz=Integer.parseInt(columns[12]);
		fbuyer_uin=Long.parseLong(columns[13]);
		fbuyer_name=columns[14];
		finval_pay_id=columns[15];
		fdeal_buy_type_sub=Integer.parseInt(columns[16]);
		fdeal_buy_type_sub_name=columns[17];
		fdeal_state=columns[18];
		fdeal_pay_type=columns[19];
		fdeal_buy_type=columns[20];
		fitem_type=columns[21];
		ftrade_state=columns[22];
		fdeal_payinfo_type=columns[23];
		ftrade_propertymask=columns[24];
		ftrade_propertymask2=columns[25];
		fpropertymask=columns[26];
		ftrade_propertymask2_id=Long.parseLong(columns[27]);
		forignalpropertymask=Long.parseLong(columns[28]);
		fwho_pay_shippingfee=Long.parseLong(columns[29]);
		fdeal_type=columns[30];
		fdeal_create_time=columns[31];
		fpay_return_time=columns[32];
		fmark_nostock_time=columns[33];
		fseller_consignment_time=columns[34];
		fclose_time=columns[35];
		frecvfee_return_time=columns[36];
		fdeal_end_time=columns[37];
		frefund_req_time=columns[38];
		frefund_end_time=columns[39];
		fseller_refuse_time=columns[40];
		fseller_agree_giveback_time=columns[41];
		fbuyer_consignment_time=columns[42];
		fwuliu_gen_time=columns[43];
		fsend_time=columns[44];
		fexpect_arrival_time=columns[45];
		frecv_time=columns[46];
		fcod_sign_time=columns[47];
		fcod_sign_return_time=columns[48];
		fdeal_item_num=Long.parseLong(columns[49]);
		fitem_price=Long.parseLong(columns[50]);
		fdeal_item_score=Long.parseLong(columns[51]);
		fdeal_item_count=Long.parseLong(columns[52]);
		fitem_original_price=Long.parseLong(columns[53]);
		fdiscount_fee=Long.parseLong(columns[54]);
		fdeal_pay_fee_shipping=Long.parseLong(columns[55]);
		fcoupon_fee=Long.parseLong(columns[56]);
		fdeal_pay_fee_ticket=Long.parseLong(columns[57]);
		fitem_adjust_price=Long.parseLong(columns[58]);
		fdeal_pay_fee_commission=Long.parseLong(columns[59]);
		fdeal_voucher_fee=Long.parseLong(columns[60]);
		fitem_fee_total=Long.parseLong(columns[61]);
		fdeal_pay_fee_total=Long.parseLong(columns[62]);
		fdeal_pay_fee=Long.parseLong(columns[63]);
		fitem_shipping_fee=Long.parseLong(columns[64]);
		frecv_city_name=columns[65];
		frecv_province_name=columns[66];
		frecv_country_name=columns[67];
		fleaf_classid=Long.parseLong(columns[68]);
		fclassidl0=Long.parseLong(columns[69]);
		fclassnamel0=columns[70];
		fclassidl1=Long.parseLong(columns[71]);
		fclassnamel1=columns[72];
		fclassidl2=Long.parseLong(columns[73]);
		fclassnamel2=columns[74];
		fclassidl3=Long.parseLong(columns[75]);
		fclassnamel3=columns[76];
		fclassidl4=Long.parseLong(columns[77]);
		fclassnamel4=columns[78];
		fcreate_bz=Integer.parseInt(columns[79]);
		fpay_bz=Integer.parseInt(columns[80]);
		fseller_con_bz=Integer.parseInt(columns[81]);
		fvitual_bz=Integer.parseInt(columns[82]);
		frefund_bz=Integer.parseInt(columns[83]);
		fwuliu_rev_bz=Integer.parseInt(columns[84]);
		fbuyer_rev_bz=Integer.parseInt(columns[85]);
		fwuliu_company_id=Integer.parseInt(columns[86]);
		frefund_to_buyer=Integer.parseInt(columns[87]);
		frefund_to_seller=Integer.parseInt(columns[88]);
		fwuliu_code=columns[89];
		fwuliu_company=columns[90];
		fwuliu_type=columns[91];
		fitem_name=columns[92];
		ftype_bz=Integer.parseInt(columns[93]);
		fvirtual_bz=Integer.parseInt(columns[94]);
		fspec_id=columns[95];
		finval_create_id=columns[96];
		ftest_id=columns[97];
		fmalicious_type=columns[98];
		fold_bz=Integer.parseInt(columns[99]);
		app_uuid=columns[100];
		dt=columns[101];
	}
	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(GdmEccDailyRawPaipaiSimpleTradeJdMr o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
