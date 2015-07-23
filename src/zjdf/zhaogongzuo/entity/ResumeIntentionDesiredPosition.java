package zjdf.zhaogongzuo.entity;
/**
	 *意向职位
	 * @author Eilin.Yang  VearyEast 
	 * @since 2014年5月9日
	 * @version v1.0.0
	 * @modify
	 */
	public class ResumeIntentionDesiredPosition{
		/**意向职位id*/
		public String p_id;
		/**用户id*/
		public String user_id;
		/**职位编码*/
		public String position_code;
		/**职位名称*/
		public String position_value;
		/**修改时间*/
		public String modify_time;
		/**添加时间*/
		public String add_time;
		
		/**
		 * 序列化数据对象
		 * @return <br/>
		 * "\""+position_code+"\""
		 */
		public String toPostString(){
			return "\""+position_code+"\"";
		}
	}