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
 * <h2>企业实体</h2>
 * 
 * <pre> </pre>
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年3月25日
 * @version
 * @modify
 * 
 */
public class Company implements Serializable {

	/** 字段 */
	private static final long serialVersionUID = 1710474609447729337L;
	/** id string */
	private String id;
	/** 公司或企业名称 */
	private String name;
	/** 所属行业 */
	private String trade;
	/** 星级，酒店行业专用字段 */
	private String starLevel;
	/** 企业，公司性质 */
	private String property;
	/** 企业规模 */
	private String scale;
	/** 雇主指数 */
	private String grade;
	/** 企业介绍 */
	private String introduce;
	/** 联系人 */
	private String contacts;
	/** 联系电话 */
	private String telephone;
	/** 手机 */
	private String phone;
	/** 邮箱 */
	private String email;
	/** 地址 */
	private String address;
	/** 招聘的岗位数 */
	private int recruitNum;
	private String followed_date;// 关注时间
	/**经度*/
	private String longtitude;
	/**纬度*/
	private String latitude;
	/**网站*/
	private String website;
	/**是否已经关注*/
	private int isFollowed;

	public String getFollowed_date() {
		return followed_date;
	}

	public void setFollowed_date(String followed_date) {
		this.followed_date = followed_date;
	}

	/**
	 * @param
	 */
	public Company() {

	}

	/**企业id
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**企业id
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**企业名称
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**企业名称
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**所属行业
	 * @return the trade
	 */
	public String getTrade() {
		return trade;
	}

	/**所属行业
	 * @param trade
	 *            the trade to set
	 */
	public void setTrade(String trade) {
		this.trade = trade;
	}

	/**星级
	 * @return the starLevel
	 */
	public String getStarLevel() {
		return starLevel;
	}

	/**星级
	 * @param starLevel
	 *            the starLevel to set
	 */
	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}

	/**企业规模
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**企业规模
	 * @param property
	 *            the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**企业规模
	 * @return the scale
	 */
	public String getScale() {
		return scale;
	}

	/**企业规模
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(String scale) {
		this.scale = scale;
	}

	/**雇主指数
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}

	/**雇主指数
	 * @param grade
	 *            the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**企业简介
	 * @return the introduce
	 */
	public String getIntroduce() {
		return introduce;
	}

	/**企业简介
	 * @param introduce
	 *            the introduce to set
	 */
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	/**联系人
	 * @return the contacts
	 */
	public String getContacts() {
		return contacts;
	}

	/**联系人
	 * @param contacts
	 *            the contacts to set
	 */
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	/**联系电话
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**联系电话
	 * @param telephone
	 *            the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**手机
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**手机
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**邮箱
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**邮箱
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**公司地址
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**公司地址
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**招聘人数
	 * @return the recruitNum
	 */
	public int getRecruitNum() {
		return recruitNum;
	}

	/**招聘人数
	 * @param recruitNum
	 *            the recruitNum to set
	 */
	public void setRecruitNum(int recruitNum) {
		this.recruitNum = recruitNum;
	}

	/**经度
	 * @return the longtitude
	 */
	public String getLongtitude() {
		return longtitude;
	}

	/**经度
	 * @param longtitude the longtitude to set
	 */
	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	/***纬度
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**纬度
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**网站
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**网站
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return 是否已经关注。1：关注，0：未关注
	 */
	public int getIsFollowed() {
		return isFollowed;
	}

	/**
	 * @param 是否已经关注。1：关注，0：未关注
	 */
	public void setIsFollowed(int isFollowed) {
		this.isFollowed = isFollowed;
	}
	
}
