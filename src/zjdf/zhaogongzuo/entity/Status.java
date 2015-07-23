package zjdf.zhaogongzuo.entity;

/**
 * 我的简历状态初始化
 * 
 * @author Administrator
 * 
 */
public class Status {
	private Double completion;// 简历完成状态  百分比
	private String avatar;// 图片
	private int job_status;// 求职状态编码
	private int privacy;// 公开状态基本信息
	private String mobile;//
	private String job_status_text;// 求职状态文本
	private String info_text;// 基本信息
	private String intention_text;// 求职意向
	private String education_text;// 教育信息
	private String experience_text;// 工作经验
	private String language_text;// 语言能力
	private String skill_text;// 技能和特长
	private String training_text;// 培训经历
	private String certificate_text;// 证书

	public Double getCompletion() {
		return completion;
	}

	public void setCompletion(Double completion) {
		this.completion = completion;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getJob_status() {
		return job_status;
	}

	public void setJob_status(int job_status) {
		this.job_status = job_status;
	}

	public int getPrivacy() {
		return privacy;
	}

	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getJob_status_text() {
		return job_status_text;
	}

	public void setJob_status_text(String job_status_text) {
		this.job_status_text = job_status_text;
	}

	public String getInfo_text() {
		return info_text;
	}

	public void setInfo_text(String info_text) {
		this.info_text = info_text;
	}

	public String getIntention_text() {
		return intention_text;
	}

	public void setIntention_text(String intention_text) {
		this.intention_text = intention_text;
	}

	public String getEducation_text() {
		return education_text;
	}

	public void setEducation_text(String education_text) {
		this.education_text = education_text;
	}

	public String getExperience_text() {
		return experience_text;
	}

	public void setExperience_text(String experience_text) {
		this.experience_text = experience_text;
	}

	public String getLanguage_text() {
		return language_text;
	}

	public void setLanguage_text(String language_text) {
		this.language_text = language_text;
	}

	public String getSkill_text() {
		return skill_text;
	}

	public void setSkill_text(String skill_text) {
		this.skill_text = skill_text;
	}

	public String getTraining_text() {
		return training_text;
	}

	public void setTraining_text(String training_text) {
		this.training_text = training_text;
	}

	public String getCertificate_text() {
		return certificate_text;
	}

	public void setCertificate_text(String certificate_text) {
		this.certificate_text = certificate_text;
	}

}
