package zjdf.zhaogongzuo.entity;

/**
 *         推送信息保存
 * @author Administrator
 *
 */
public class SetPush {
	
	private  int push_enable;//设置总开关
	private int push_job_enable;//职位订阅
	private int push_msg_enable;//我的消息
	private int push_viewed_enable;//谁看过我
	private int push_meets_enable;//招聘会
	
	
	public int getPush_enable() {
		return push_enable;
	}
	public void setPush_enable(int push_enable) {
		this.push_enable = push_enable;
	}
	public int getPush_job_enable() {
		return push_job_enable;
	}
	public void setPush_job_enable(int push_job_enable) {
		this.push_job_enable = push_job_enable;
	}
	public int getPush_msg_enable() {
		return push_msg_enable;
	}
	public void setPush_msg_enable(int push_msg_enable) {
		this.push_msg_enable = push_msg_enable;
	}
	public int getPush_viewed_enable() {
		return push_viewed_enable;
	}
	public void setPush_viewed_enable(int push_viewed_enable) {
		this.push_viewed_enable = push_viewed_enable;
	}
	public int getPush_meets_enable() {
		return push_meets_enable;
	}
	public void setPush_meets_enable(int push_meets_enable) {
		this.push_meets_enable = push_meets_enable;
	}
	
	

}
