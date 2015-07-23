package zjdf.zhaogongzuo.entity;

/**
 * 简历基本信息
 * 
 * @author Administrator
 * 
 */
public class ResumeInformation {
	private String name;// 姓名
	private int gender;// 性别
	private String gender_text;// 性别文本
	private String birthday;// 出生日期
	private int work_exps;// 工作经验编码
	private String work_exps_text;// 工作经验文本
	private String graduate_date;// 毕业时间
	private int edu;// 学历编码
	private String edu_text;// 学历文本
	private String current_place;// 户籍地编码
	private String current_place_text;// 户籍地
	private String domicile_place;// 现居地编码
	private String domicile_place_text;// 现居地
	private String phone;// 手机号
	private int height;// 身高
	private String email;// 电子邮箱
	private int weight;// 体重
	private String id_type;// 证件类型编码
	private String id_type_text;// 证件类型
	private String id_num;// 证件编码
	private String  job_status;//求职状态

	public String getJob_status() {
		return job_status;
	}

	public void setJob_status(String job_status) {
		this.job_status = job_status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getGender_text() {
		return gender_text;
	}

	public void setGender_text(String gender_text) {
		this.gender_text = gender_text;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getWork_exps() {
		return work_exps;
	}

	public void setWork_exps(int work_exps) {
		this.work_exps = work_exps;
	}

	public String getWork_exps_text() {
		return work_exps_text;
	}

	public void setWork_exps_text(String work_exps_text) {
		this.work_exps_text = work_exps_text;
	}

	public String getGraduate_date() {
		return graduate_date;
	}

	public void setGraduate_date(String graduate_date) {
		this.graduate_date = graduate_date;
	}

	public int getEdu() {
		return edu;
	}

	public void setEdu(int edu) {
		this.edu = edu;
	}

	public String getEdu_text() {
		return edu_text;
	}

	public void setEdu_text(String edu_text) {
		this.edu_text = edu_text;
	}

	public String getCurrent_place() {
		return current_place;
	}

	public void setCurrent_place(String current_place) {
		this.current_place = current_place;
	}

	public String getDomicile_place() {
		return domicile_place;
	}

	public void setDomicile_place(String domicile_place) {
		this.domicile_place = domicile_place;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getId_type() {
		return id_type;
	}

	public void setId_type(String id_type) {
		this.id_type = id_type;
	}

	public String getId_type_text() {
		return id_type_text;
	}

	public void setId_type_text(String id_type_text) {
		this.id_type_text = id_type_text;
	}

	public String getId_num() {
		return id_num;
	}

	public void setId_num(String id_num) {
		this.id_num = id_num;
	}

	public String getCurrent_place_text() {
		return current_place_text;
	}

	public void setCurrent_place_text(String current_place_text) {
		this.current_place_text = current_place_text;
	}

	public String getDomicile_place_text() {
		return domicile_place_text;
	}

	public void setDomicile_place_text(String domicile_place_text) {
		this.domicile_place_text = domicile_place_text;
	}

}
