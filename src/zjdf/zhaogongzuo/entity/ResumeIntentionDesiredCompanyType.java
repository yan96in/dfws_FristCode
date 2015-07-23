package zjdf.zhaogongzuo.entity;
/**
	 * 意向企业类型相关
	 * @author Eilin.Yang  VearyEast
	 * @since 2014年5月9日
	 * @version v1.0.0
	 * @modify
	 */
	public class ResumeIntentionDesiredCompanyType{
		
		/**意向企业相关id*/
		public String c_id;
		/**用户id*/
		public String user_id;
		/**所属行业编码*/
		public String industry_code;
		/**所属行业值*/
		public String industry_value;
		/**所属行业的 上一级行业编码*/
		public String parent_industry_code;
		/**所属行业的 上一级行业值*/
		public String parent_industry_value;
		/**星级编码*/
		public String star_code;
		/**星级值*/
		public String star_value;
		/**修改时间*/
		public String modify_time;
		/**添加时间*/
		public String add_time;
		
		/**
		 * 序列化数据对象
		 * @return <br/>
		 * "{\"industry\":\""+parent_industry_code+"\",\"company_type\" : \""+industry_code+"\",\"star\" : \""+star_code+"\"}"
		 */
		public String toPostString(){
			return "{\"industry\":\""+parent_industry_code+"\",\"company_type\" : \""+industry_code+"\",\"star\" : \""+star_code+"\"}";
		}
		
	}