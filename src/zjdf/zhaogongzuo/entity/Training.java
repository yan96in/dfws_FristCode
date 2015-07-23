package zjdf.zhaogongzuo.entity;

public class Training {
	private String id;// 培训经历 ID
	private int user_id;// 用户ID
	private String institutions_cn;// 培训名字
	private String institutions_en;// 机构
	private String begin_year;// 开始年份
	private String begin_month;// 开始月份
	private String end_year;// 结束年
	private String end_month;// 结束月份
	private String course_cn;// 课程名字
	private String course_en;// 课程类型
	private String certificates_cn;// 证书
	private String certificates_en;// 不知道
	private String detail_cn;// 描述
	private String detail_en;// 不知道
	private String location;// 培训位置编码
	private String location_txt;//培训位置
	private String modify_time;// 修改时间
	private String add_time;// 添加时间
	

	public String getLocation_txt() {
		return location_txt;
	}

	public void setLocation_txt(String location_txt) {
		this.location_txt = location_txt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getInstitutions_cn() {
		return institutions_cn;
	}

	public void setInstitutions_cn(String institutions_cn) {
		this.institutions_cn = institutions_cn;
	}

	public String getInstitutions_en() {
		return institutions_en;
	}

	public void setInstitutions_en(String institutions_en) {
		this.institutions_en = institutions_en;
	}

	public String getBegin_year() {
		return begin_year;
	}

	public void setBegin_year(String begin_year) {
		this.begin_year = begin_year;
	}

	public String getBegin_month() {
		return begin_month;
	}

	public void setBegin_month(String begin_month) {
		this.begin_month = begin_month;
	}

	public String getEnd_year() {
		return end_year;
	}

	public void setEnd_year(String end_year) {
		this.end_year = end_year;
	}

	public String getEnd_month() {
		return end_month;
	}

	public void setEnd_month(String end_month) {
		this.end_month = end_month;
	}

	public String getCourse_cn() {
		return course_cn;
	}

	public void setCourse_cn(String course_cn) {
		this.course_cn = course_cn;
	}

	public String getCourse_en() {
		return course_en;
	}

	public void setCourse_en(String course_en) {
		this.course_en = course_en;
	}

	public String getCertificates_cn() {
		return certificates_cn;
	}

	public void setCertificates_cn(String certificates_cn) {
		this.certificates_cn = certificates_cn;
	}

	public String getCertificates_en() {
		return certificates_en;
	}

	public void setCertificates_en(String certificates_en) {
		this.certificates_en = certificates_en;
	}

	public String getDetail_cn() {
		return detail_cn;
	}

	public void setDetail_cn(String detail_cn) {
		this.detail_cn = detail_cn;
	}

	public String getDetail_en() {
		return detail_en;
	}

	public void setDetail_en(String detail_en) {
		this.detail_en = detail_en;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getModify_time() {
		return modify_time;
	}

	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

}
