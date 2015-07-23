package zjdf.zhaogongzuo.entity;
/**意向工作地点
	 * @author Eilin.Yang  VearyEast 
	 * @since 2014年5月9日
	 * @version v1.0.0
	 * @modify
	 */
	public class ResumeIntentionDesiredLocation{
		/**意向地区id*/
		public String l_id;
		/**用户id*/
		public String user_id;
		/**意向地点编码*/
		public String location_code;
		/**意向地点值*/
		public String location_value;
		/**修改时间*/
		public String modify_time;
		/**添加时间*/
		public String add_time;
		
		/**
		 * 序列化数据对象
		 * PersonDesiredLocation = "[\n  \"010000\",\n  \"070100\",\n  \"240200\",\n  \"210100\"\n]";
		 * 
		 * @return <br/>
		 * "\""+location_code+"\""
		 */
		public String toPostString(){
			return "\""+location_code+"\"";
		}
	}