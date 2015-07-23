package zjdf.zhaogongzuo.entity;

/**
 * 个人中心状态初始化
 * @author Administrator
 *
 */
public class State {

	private Double resume_complete;// 百分比
	private int resume_status;// 是否公开
	private String avatar;// 图像地址
	private int applied_num;// 申请职位个数
	private int favorited_num;// 收藏职位个数
	private int followed_num;// 关注企业个数
	private int unread_message_num;// 未读消息个数
	private int is_need_fill_micro_resume;
	
	public double getResume_complete() {
		return resume_complete;
	}

	public void setResume_complete(Double resume_complete) {
		this.resume_complete = resume_complete;
	}

	public int getResume_status() {
		return resume_status;
	}

	public void setResume_status(int resume_status) {
		this.resume_status = resume_status;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getApplied_num() {
		return applied_num;
	}

	public void setApplied_num(int applied_num) {
		this.applied_num = applied_num;
	}

	public int getFavorited_num() {
		return favorited_num;
	}

	public void setFavorited_num(int favorited_num) {
		this.favorited_num = favorited_num;
	}

	public int getFollowed_num() {
		return followed_num;
	}

	public void setFollowed_num(int followed_num) {
		this.followed_num = followed_num;
	}

	public int getUnread_message_num() {
		return unread_message_num;
	}

	public void setUnread_message_num(int unread_message_num) {
		this.unread_message_num = unread_message_num;
	}

	/**
	 * @return the is_need_fill_micro_resume
	 */
	public int getIs_need_fill_micro_resume() {
		return is_need_fill_micro_resume;
	}

	/**
	 * @param is_need_fill_micro_resume the is_need_fill_micro_resume to set
	 */
	public void setIs_need_fill_micro_resume(int is_need_fill_micro_resume) {
		this.is_need_fill_micro_resume = is_need_fill_micro_resume;
	}

}
