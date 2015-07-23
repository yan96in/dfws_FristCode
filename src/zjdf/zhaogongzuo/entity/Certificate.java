package zjdf.zhaogongzuo.entity;

/**
 * 证书
 * 
 * @author Administrator
 * 
 */
public class Certificate {

	private String id;// 证书ID
	private String user_id;// 用户ID
	private String obtained_year;// 获得证书年份
	private String obtained_month;// 证书时间
	private String certificate_cn;// 证书名字
	private String certificate_en;// 证书
	private String file_id;// 文件ID
	private String detail_cn;//证书描述
	private String detail_en;
	private String modify_time;// 时间
	private String add_time;// 时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getObtained_year() {
		return obtained_year;
	}

	public void setObtained_year(String obtained_year) {
		this.obtained_year = obtained_year;
	}

	public String getObtained_month() {
		return obtained_month;
	}

	public void setObtained_month(String obtained_month) {
		this.obtained_month = obtained_month;
	}

	public String getCertificate_cn() {
		return certificate_cn;
	}

	public void setCertificate_cn(String certificate_cn) {
		this.certificate_cn = certificate_cn;
	}

	public String getCertificate_en() {
		return certificate_en;
	}

	public void setCertificate_en(String certificate_en) {
		this.certificate_en = certificate_en;
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
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
