package zjdf.zhaogongzuo.entity;
/**
	 * 求职意向基本信息
	 * @author Eilin.Yang  VearyEast 
	 * @since 2014年5月9日
	 * @version v1.0.0
	 * @modify
	 */
	public class ResumeIntentionDesiredJob{
		/**用户id*/
		public String user_id;
		/**工作性质编码*/
		public String work_mode_code;
		/**工作性质值*/
		public String work_mode_value;
		/**当前薪资类型 key*/
		public String current_salary_mode_key;
		/**当前薪资类型 value*/
		public String current_salary_mode_value;
		/**当前薪资货币种类 key*/
		public String current_salary_currency_key;
		/**当前薪资货币种类value*/
		public String current_salary_currency_value;
		/**当前薪资*/
		public String current_salary;
		/**当前薪资是否显示*/
		public String current_salary_is_show="1";
		
		/**期望薪资类型 key*/
		public String desired_salary_mode_key;
		/**期望薪资类型 value*/
		public String desired_salary_mode_value;
		/**期望薪资货币种类 key*/
		public String desired_salary_currency_key;
		/**期望薪资货币种类value*/
		public String desired_salary_currency_value;
		/**期望薪资 key*/
		public String desired_salary_key;
		/**期望薪资 value*/
		public String desired_salary_value;
		/**期望薪资是否显示*/
		public String desired_salary_is_show="1";
		
		/**到岗时间 key*/
		public String arrival_time_key;
		/**到岗时间 value*/
		public String arrival_time_value;
		/**修改时间*/
		public String modify_time;
		/**添加时间 */
		public String add_time;
		
		/**
		 * 序列化数据对象
		 * @return <br/>
		 * "{\"current_salary_mode\" : \""+current_salary_mode_key+"\""
					+ ",\"desired_salary_currency\" : \""+desired_salary_currency_key+"\""
							+ ",\"arrival_time\" : \""+arrival_time_key+"\""
									+ ",\"desired_salary_mode\" : \""+desired_salary_mode_key+"\""
											+ ",\"current_salary\" : \""+current_salary+"\""
													+ ",\"current_salary_is_show\" : "+current_salary_is_show.contains("1")+""
															+ ",\"current_salary_currency\" : \""+current_salary_currency_key+"\""
																	+ ",\"desired_salary\" : \""+desired_salary_key+"\""
																			+ ",\"work_mode\" : \""+work_mode_code+"\""
																					+ ",\"desired_salary_is_show\" : "+desired_salary_is_show.contains("1")+""
																							+ "}";
		 */
		public String toPostString(){
			return "{\"current_salary_mode\" : \""+current_salary_mode_key+"\""
					+ ",\"desired_salary_currency\" : \""+desired_salary_currency_key+"\""
							+ ",\"arrival_time\" : \""+arrival_time_key+"\""
									+ ",\"desired_salary_mode\" : \""+desired_salary_mode_key+"\""
											+ ",\"current_salary\" : \""+current_salary+"\""
													+ ",\"current_salary_is_show\" : "+current_salary_is_show+""
															+ ",\"current_salary_currency\" : \""+current_salary_currency_key+"\""
																	+ ",\"desired_salary\" : \""+desired_salary_key+"\""
																			+ ",\"work_mode\" : \""+work_mode_code+"\""
																					+ ",\"desired_salary_is_show\" : "+desired_salary_is_show+""
																							+ "}";
		}
	}