package zjdf.zhaogongzuo.entity;

/**
 * 查看简历
 * 
 * @author Administrator
 * 
 */
public class Viewed {
	private int company_id;// 企业id
	private String company_name;// 企业名称
	private String viewed_date;// 企业最后查看时间
	private int viewed_times;// 企业查看简历次数

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getViewed_date() {
		return viewed_date;
	}

	public void setViewed_date(String viewed_date) {
		this.viewed_date = viewed_date;
	}

	public int getViewed_times() {
		return viewed_times;
	}

	public void setViewed_times(int viewed_times) {
		this.viewed_times = viewed_times;
	}

}
