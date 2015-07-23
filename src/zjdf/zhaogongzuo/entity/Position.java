/**
 * Copyright © 2014年3月25日 FindJob www.veryeast.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package zjdf.zhaogongzuo.entity;

import java.io.Serializable;

/**
 * <h2>职位对象</h2>
 * 
 * <pre> </pre>
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年3月25日
 * @version
 * @modify
 * 
 */
public class Position implements Serializable {

	/** 字段 */
	private static final long serialVersionUID = -7367418206080736038L;

	/** 职位id string类型 */
	private String idStr;
	/** 职位名称 */
	private String name;
	/** 更新时间 */
	private String updateTime;
	/** 是否急聘,是否热门 */
	private int is_urgent;
	/** 招聘人数 */
	private String recruitingNumbers;
	/** 工作地点 */
	private String address;
	/** 招聘条件 */
	private String condition;
	/** 薪资待遇 */
	private String salary;
	/** 是否提供食宿 */
	private String foodStatus;
	/** 职位描述 */
	private String description;
	/** 联系人 */
	private String contacts;
	/** 联系电话 */
	private String telephone;
	/** 手机 */
	private String phone;
	/** 邮箱 */
	private String email;
	/** 公司地址 */
	private String companyaddress;
	/** 所属企业id */
	private String companyId;
	/** 所属企业名称 */
	private String companyName;
	/** 公司 */
	private Company company;
	/** 公司网站 */
	private String website;
	/** 经度 */
	private String longitude;
	/** 纬度 */
	private String latitude;
	/** 是否已经收藏 */
	private int is_favorited;
	/** 是否已经申请*/
	private int is_applied;
	private String favorite_date;// 收藏时间
	private int is_stop;// 是否已停止招聘
	private Boolean is_stops;// 是否已停止招聘
	private String apply_time;// 时间

	public Boolean getIs_stops() {
		return is_stops;
	}

	public void setIs_stops(Boolean is_stops) {
		this.is_stops = is_stops;
	}


	public String getApply_time() {
		return apply_time;
	}

	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}

	public int getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(int is_stop) {
		this.is_stop = is_stop;
	}

	public String getFavorite_date() {
		return favorite_date;
	}

	public void setFavorite_date(String favorite_date) {
		this.favorite_date = favorite_date;
	}

	/**
	 * @param
	 */
	public Position() {

	}

	/**
	 * 职位id
	 * 
	 * @return the idStr
	 */
	public String getIdStr() {
		return idStr;
	}

	/**
	 * 职位id
	 * 
	 * @param idStr
	 *            the idStr to set
	 */
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	/**
	 * 职位名称
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 职位名称
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 更新时间
	 * 
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * 更新时间
	 * 
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 是否急聘。1:急聘，0:不是急聘.,是否热门
	 * 
	 * @return the is_urgent
	 */
	public int getIs_urgent() {
		return is_urgent;
	}

	/**
	 * 是否急聘。1:急聘，0:不是急聘.,是否热门
	 * 
	 * @param is_urgent
	 *            the is_urgent to set
	 */
	public void setIs_urgent(int is_urgent) {
		this.is_urgent = is_urgent;
	}

	/**
	 * 招聘人数
	 * 
	 * @return the recruitingNumbers
	 */
	public String getRecruitingNumbers() {
		return recruitingNumbers;
	}

	/**
	 * 招聘人数
	 * 
	 * @param recruitingNumbers
	 *            the recruitingNumbers to set
	 */
	public void setRecruitingNumbers(String recruitingNumbers) {
		this.recruitingNumbers = recruitingNumbers;
	}

	/**
	 * 工作地点
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 工作地点
	 * 
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 招聘条件
	 * 
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * 招聘条件
	 * 
	 * @param condition
	 *            the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * 薪资要求
	 * 
	 * @return the salary
	 */
	public String getSalary() {
		return salary;
	}

	/**
	 * 薪资要求
	 * 
	 * @param salary
	 *            the salary to set
	 */
	public void setSalary(String salary) {
		this.salary = salary;
	}

	/**
	 * 是否提供食宿
	 * 
	 * @return the foodStatus
	 */
	public String getFoodStatus() {
		return foodStatus;
	}

	/**
	 * 是否提供食宿
	 * 
	 * @param foodStatus
	 *            the foodStatus to set
	 */
	public void setFoodStatus(String foodStatus) {
		this.foodStatus = foodStatus;
	}

	/**
	 * 职位描述
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 职位描述
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 联系人
	 * 
	 * @return the contacts
	 */
	public String getContacts() {
		return contacts;
	}

	/**
	 * 联系人
	 * 
	 * @param contacts
	 *            the contacts to set
	 */
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	/**
	 * 联系电话
	 * 
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * 联系电话
	 * 
	 * @param telephone
	 *            the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 手机
	 * 
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 手机
	 * 
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 电子邮件
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 电子邮件
	 * 
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 公司地址
	 * 
	 * @return the companyaddress
	 */
	public String getCompanyaddress() {
		return companyaddress;
	}

	/**
	 * 公司地址
	 * 
	 * @param companyaddress
	 *            the companyaddress to set
	 */
	public void setCompanyaddress(String companyaddress) {
		this.companyaddress = companyaddress;
	}

	/**
	 * 公司id
	 * 
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * 公司id
	 * 
	 * @param companyId
	 *            the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * 公司名称
	 * 
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * 公司名称
	 * 
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 公司
	 * 
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * 公司
	 * 
	 * @param company
	 *            the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * 公司网站
	 * 
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * 公司网站
	 * 
	 * @param website
	 *            the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * 经度
	 * 
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * 经度
	 * 
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * 纬度
	 * 
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * 纬度
	 * 
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * 是否已经收藏。1:收藏，0:没有收藏
	 * 
	 * @return the is_favorited
	 */
	public int getIs_favorited() {
		return is_favorited;
	}

	/**
	 * 是否已经收藏。1:收藏，0:没有收藏
	 * 
	 * @param is_favorited
	 *            the is_favorited to set
	 */
	public void setIs_favorited(int is_favorited) {
		this.is_favorited = is_favorited;
	}

	/**
	 * @return 是否已经申请。1:申请，0:没有申请
	 */
	public int getIs_applied() {
		return is_applied;
	}

	/**
	 * @param 是否已经申请。1:申请，0:没有申请
	 */
	public void setIs_applied(int is_applied) {
		this.is_applied = is_applied;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Position [idStr=" + idStr + ", name=" + name + ", updateTime="
				+ updateTime + ", is_urgent=" + is_urgent
				+ ", recruitingNumbers=" + recruitingNumbers + ", address="
				+ address + ", condition=" + condition + ", salary=" + salary
				+ ", foodStatus=" + foodStatus + ", description=" + description
				+ ", contacts=" + contacts + ", telephone=" + telephone
				+ ", phone=" + phone + ", email=" + email + ", companyaddress="
				+ companyaddress + ", companyId=" + companyId
				+ ", companyName=" + companyName + ", company=" + company
				+ ", website=" + website + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", is_favorited=" + is_favorited
				+ ", is_applied=" + is_applied + ", favorite_date="
				+ favorite_date + ", is_stop=" + is_stop + ", is_stops="
				+ is_stops + ", apply_time=" + apply_time + "]";
	}

	
}
