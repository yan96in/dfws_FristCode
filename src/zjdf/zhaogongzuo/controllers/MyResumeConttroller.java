/**
 * Copyright © 2014年4月1日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.entity.Certificate;
import zjdf.zhaogongzuo.entity.Company;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.entity.JobFair;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.entity.ResumeLanguage;
import zjdf.zhaogongzuo.entity.Skills;
import zjdf.zhaogongzuo.entity.Training;
import zjdf.zhaogongzuo.net.HttpTools;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;

/**
 * <h2>我的简历控制器</h2>
 * 
 * <pre> </pre>
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年4月1日
 * @version
 * @modify
 * 
 */
public class MyResumeConttroller extends BaseConttroller {

	public MyResumeConttroller(Context context) {
		super(context);
	}

	// 设置 微简历
	public EorrerBean setResume(String true_name_cn, String gender,
			String mobile, String birthday, String person_desired_location,
			String person_desired_position, String height, String degree,
			String desired_salary) {
		EorrerBean state = null;
		int genders;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		if (gender.contains("男")) {
			genders = 1;
		} else {
			genders = 2;
		}
		String request = NET_HOST + "resume/set_micro_resume";
		nv.add(new BasicNameValuePair("true_name_cn", true_name_cn));
		nv.add(new BasicNameValuePair("gender", genders + ""));
		nv.add(new BasicNameValuePair("mobile", mobile));
		nv.add(new BasicNameValuePair("birthday", birthday));
		nv.add(new BasicNameValuePair("person_desired_location",
				person_desired_location));
		nv.add(new BasicNameValuePair("person_desired_position",
				person_desired_position));
		nv.add(new BasicNameValuePair("height", height));
		nv.add(new BasicNameValuePair("degree", degree));
		nv.add(new BasicNameValuePair("desired_salary", desired_salary));
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (jObject.has("status")) {
						state = new EorrerBean();
						state.status = jObject.getInt("status");
						if (jObject.has("errCode")) {
							state.errCode = jObject.getInt("errCode");
						}
						if (jObject.has("errMsg")) {
							state.errMsg = jObject.getString("errMsg");
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return state;
			}
		}
		return state;
	}

	/**
	 * 获取招聘会列表
	 * 
	 * @param
	 */
	public List<JobFair> getJobFair() {
		JobFair jobFair;
		ArrayList<JobFair> lisFairs = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "job/meets";
		String strResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(strResult);
				if (jsonObject.has("data")) {
					JSONObject Object = jsonObject.getJSONObject("data");
					if (Object.has("list")) {
						JSONArray array = Object.getJSONArray("list");
						int n = array.length();
						lisFairs = new ArrayList<JobFair>(n);
						for (int i = 0; i < n; i++) {
							JSONObject data = array.getJSONObject(i);
							jobFair = new JobFair();
							jobFair.setId(data.getInt("meet_id"));
							jobFair.setDatetime(data.getString("date")
									.substring(0, 10));
							jobFair.setAddress(data.getString("area"));
							jobFair.setImageUrl(data.getString("image_url"));
							jobFair.setName(data.getString("meet_name"));
							lisFairs.add(jobFair);
						}
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}
		return lisFairs;
	}

	/**
	 * 招聘会列表详情
	 * 
	 * @param area
	 *            招聘会ID
	 * @return
	 */
	public JobFair getJobmeet_detail(int meet_id) {
		JobFair jobFair = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "job/meet_detail";
		nv.add(new BasicNameValuePair("meet_id", meet_id + ""));
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(sreResult);
				JSONObject Object = jsonObject.getJSONObject("data");
				if (!StringUtils.isEmpty(Object)) {
					jobFair = new JobFair();
					jobFair.setId(Object.getInt("meet_id"));
					jobFair.setName(Object.getString("title"));
					jobFair.setBegin_time(Object.getString("begin_time"));
					jobFair.setEnd_time(Object.getString("end_time"));
					jobFair.setAddress(Object.getString("address"));
					jobFair.setLatitude(Object.getString("latitude"));
					jobFair.setLongitude(Object.getString("longitude"));
					if (Object.has("joined_companies")) {
						JSONArray array = Object
								.getJSONArray("joined_companies");
						int n = array.length();
						List<Company> list = new ArrayList<Company>(n);
						for (int i = 0; i < n; i++) {
							Company company = new Company();
							JSONObject data = array.getJSONObject(i);
							company.setId(data.getString("UserId"));
							company.setName(data.getString("cname"));
							list.add(company);
						}
						jobFair.setListCompanies(list);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;

			}
		}
		return jobFair;
	}

	/**
	 * 关注企业
	 * 
	 * @param company_id
	 *            企业ID
	 * @return
	 */
	public int follow_company(String company_id) {
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "user/follow_company";
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("company_id", company_id));
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					JSONObject jsonObject = jObject.getJSONObject("data");
					if (jsonObject != null) {
						return 1;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

			}
		}
		return 0;
	}

	/**
	 * 取消关注企业
	 * 
	 * @param company_id
	 *            (已验证) 企业ID 可以批量取消。。","隔开
	 * @return
	 */
	public EorrerBean unfollow_company(String company_id) {
		EorrerBean eb = null;
		if (StringUtils.isEmpty(company_id)) {
			return eb;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "user/unfollow_company";
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("company_id", company_id));
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					eb = new EorrerBean();
					eb.status = jObject.getInt("status");
					if (jObject.has("errCode"))
						eb.errCode = jObject.getInt("errCode");
					if (jObject.has("errMsg"))
						eb.errMsg = jObject.getString("errMsg");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return eb;
			}
		}
		return eb;
	}

	/**
	 * 获取关注企业 (已验证)
	 * 
	 * @param company_id
	 *            企业ID
	 * @return
	 */
	public List<Company> followed_companies() {
		Company company;
		ArrayList<Company> newsList = null;
		List<NameValuePair> ln = new ArrayList<NameValuePair>();
		ln.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String request = NET_HOST + "user/followed_companies";
		String strResult = HttpTools.getHttpRequestString(ln, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(strResult);
				JSONObject Object = jsonObject.getJSONObject("data");
				if (!StringUtils.isEmpty(Object)) {
					JSONArray array = Object.getJSONArray("list");
					int n = array.length();
					newsList = new ArrayList<Company>(n);
					for (int i = 0; i < n; i++) {
						JSONObject data = array.getJSONObject(i);
						company = new Company();
						company.setId(data.getString("company_id"));
						company.setName(data.getString("company_name"));
						company.setFollowed_date(data
								.getString("followed_date").substring(0, 10));
						company.setRecruitNum(data.optInt("jobs_num"));
						newsList.add(company);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return newsList;

	}

	/**
	 * 企业招聘职位 （已验证）
	 * 
	 * @return
	 */
	public List<Position> company_recruit_jobs(String company_id) {
		Position position;
		ArrayList<Position> newsList = null;
		List<NameValuePair> ln = new ArrayList<NameValuePair>();
		ln.add(new BasicNameValuePair("company_id", company_id));
		String request = NET_HOST + "job/company_recruit_jobs";
		String strResult = HttpTools.getHttpRequestString(ln, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(strResult);
				if (jsonObject.has("status")
						&& jsonObject.optInt("status") == 1) {
					JSONObject Object = jsonObject.getJSONObject("data");
					if (!StringUtils.isEmpty(Object)) {
						JSONArray array = Object.getJSONArray("list");
						int n = array.length();
						if (n > 0) {
							newsList = new ArrayList<Position>(n);
							for (int i = 0; i < n; i++) {
								JSONObject data = array.getJSONObject(i);
								position = new Position();
								position.setIdStr(data.getString("job_id"));
								position.setName(data.getString("job_name"));
								position.setCompanyId(data
										.getString("company_id"));
								position.setCompanyName(data
										.getString("company_name"));
								position.setUpdateTime(data
										.getString("update_time"));
								position.setSalary(data.getString("salary"));
								position.setAddress(data
										.getString("work_place"));
								position.setIs_urgent(data
										.getInt("is_emergency"));
								newsList.add(position);
							}
						}
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}
		return newsList;

	}

	/**
	 * 职位收藏列表(已验证)
	 * 
	 * @return
	 */
	public List<Position> favorited_jobs() {
		Position position;
		ArrayList<Position> newsList = null;
		List<NameValuePair> ln = new ArrayList<NameValuePair>();
		ln.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String request = NET_HOST + "user/favorited_jobs";
		String strResult = HttpTools.getHttpRequestString(ln, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(strResult);
				JSONObject Object = jsonObject.getJSONObject("data");
				if (!StringUtils.isEmpty(Object)) {
					JSONArray array = Object.getJSONArray("list");
					int n = array.length();
					newsList = new ArrayList<Position>(n);
					for (int i = 0; i < n; i++) {
						JSONObject data = array.getJSONObject(i);
						position = new Position();
						position.setIdStr(data.getString("job_id"));
						position.setName(data.getString("job_name"));
						position.setCompanyId(data.getString("company_id"));
						position.setCompanyName(data.getString("company_name"));
						position.setFavorite_date(data.getString(
								"favorite_date").substring(0, 10));
						position.setAddress(data.getString("job_area"));
						position.setIs_stops(data.getBoolean("is_stop"));
						newsList.add(position);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return newsList;

	}

	/**
	 * 职位申请记录 （已验证）
	 * 
	 * @return
	 */
	public List<Position> applied_jobs() {
		Position position;
		ArrayList<Position> newsList = null;
		List<NameValuePair> ln = new ArrayList<NameValuePair>();
		ln.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String request = NET_HOST + "user/applied_jobs";
		String strResult = HttpTools.getHttpRequestString(ln, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(strResult);
				JSONObject Object = jsonObject.getJSONObject("data");
				if (!StringUtils.isEmpty(Object)) {
					JSONArray array = Object.getJSONArray("list");
					int n = array.length();
					newsList = new ArrayList<Position>(n);
					for (int i = 0; i < n; i++) {
						JSONObject data = array.getJSONObject(i);
						position = new Position();
						position.setIdStr(data.getString("job_id"));
						position.setName(data.getString("job_name"));
						position.setCompanyId(data.getString("company_id"));
						position.setCompanyName(data.getString("company_name"));
						position.setApply_time(data.getString("apply_time")
								.substring(0, 10));
						position.setAddress(data.getString("work_place"));
						position.setIs_stop(data.optInt("is_stop"));
						newsList.add(position);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return newsList;

	}

	/**
	 * 删除收藏职位
	 * 
	 * @param job_id
	 *            职位ID 职位id，批量取消用,分割。
	 * @return
	 */
	public EorrerBean delete_favorite_job(String job_id) {
		EorrerBean eb = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "user/delete_favorite_job";
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("job_id", job_id));
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					eb = new EorrerBean();
					eb.status = jObject.getInt("status");
					if (jObject.has("errCode"))
						eb.errCode = jObject.getInt("errCode");
					if (jObject.has("errMsg"))
						eb.errMsg = jObject.getString("errMsg");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return eb;
			}
		}
		return eb;
	}

	/**
	 * 阿里坐标转换百度坐标
	 * 
	 * @return
	 */
	public JobFair getCoordinates(String coords) {
		JobFair jobFair = null;
		List<NameValuePair> ln = new ArrayList<NameValuePair>();
		ln.add(new BasicNameValuePair("coords", coords + ";"));
		ln.add(new BasicNameValuePair("ak", "zzFNESIaCvMRnPN3SoKbF00B"));
		ln.add(new BasicNameValuePair("from", 3 + ""));
		ln.add(new BasicNameValuePair("to", 5 + ""));
		ln.add(new BasicNameValuePair("output", "json"));
		String request = "http://api.map.baidu.com/geoconv/v1/?";
		String strResult = HttpTools.getHttpRequestString(ln, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(strResult);
				JSONObject Object = jsonObject.getJSONObject("result");
				if (!StringUtils.isEmpty(Object)) {
					jobFair = new JobFair();
					jobFair.setLatitude(Object.getString("x"));
					jobFair.setLongitude(Object.getString("y"));
					return jobFair;
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return jobFair;
	}

	/**
	 * 刷新简历
	 * 
	 * @param resume_status
	 *            刷新状态
	 * @return
	 */
	public int refresh(String resume_status) {
		int state = 0;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "resume/refresh";
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("resume_status", resume_status));
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					state = jObject.getInt("status");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return state;

			}
		}
		return state;
	}

	/**
	 * 获取简历预览
	 * 
	 * @return
	 */
	public String reaumePreview() {
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "resume/preview";
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String result = HttpTools.getHttpRequestString(nv, request);
		return result;
	}

	/**
	 * 获取证书
	 * 
	 */
	public List<Certificate> get_certificates() {
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		ArrayList<Certificate> certificatelist = null;
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String request = NET_HOST + "resume/get_certificates";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					JSONArray array = jObject.getJSONArray("data");
					if (array != null) {
						certificatelist = new ArrayList<Certificate>();
						for (int i = 0; i < array.length(); i++) {
							JSONObject data = (JSONObject) array.get(i);
							Certificate certificate = new Certificate();
							certificate.setId(data.getString("id"));
							certificate.setUser_id(data.getString("user_id"));
							certificate.setObtained_year(data
									.optString("obtained_year"));
							certificate.setObtained_month(data
									.optString("obtained_month"));
							certificate.setCertificate_cn(data
									.optString("certificate_cn"));
							certificate.setCertificate_en(data
									.optString("certificate_en"));
							certificate.setFile_id(data.optString("file_id"));
							certificate.setDetail_cn(data
									.optString("detail_cn"));
							certificate.setDetail_en(data
									.optString("detail_en"));
							certificate.setModify_time(data
									.optString("modify_time"));
							certificate.setAdd_time(data.optString("add_time"));
							certificatelist.add(certificate);
						}
						return certificatelist;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}
		return certificatelist;
	}

	/**
	 * 删除证书
	 */
	public EorrerBean delete_certificate(String cert_id) {
		EorrerBean eb = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "resume/delete_certificates";
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("cert_id", cert_id));
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					eb = new EorrerBean();
					eb.status = jObject.getInt("status");
					if (jObject.has("errCode"))
						eb.errCode = jObject.getInt("errCode");
					if (jObject.has("errMsg"))
						eb.errMsg = jObject.getString("errMsg");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return eb;
			}
		}
		return eb;
	}

	/**
	 * 添加证书 (已验证)
	 */
	public int set_certificate(String obtained_year, String obtained_month,
			String certificate_cN, String detail_cn, String id) {
		int state = 0;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "resume/set_certificates";
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("id", id));
		nv.add(new BasicNameValuePair("obtained_year", obtained_year));
		nv.add(new BasicNameValuePair("obtained_month", obtained_month));
		nv.add(new BasicNameValuePair("certificate_cn", certificate_cN));
		nv.add(new BasicNameValuePair("detail_cn", detail_cn));
		// nv.add(new BasicNameValuePair("user_id",""));
		// nv.add(new BasicNameValuePair("certificate_en", ""));
		// nv.add(new BasicNameValuePair("file_id", ""));
		// nv.add(new BasicNameValuePair("detail_en", ""));
		// nv.add(new BasicNameValuePair("modify_time", ""));
		// nv.add(new BasicNameValuePair("add_time", ""));
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					state = jObject.getInt("status");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return -1;
			}
		}
		return state;
	}

	/**
	 * 获取培训 经历
	 */

	public List<Training> get_training_exps() {
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		ArrayList<Training> certificatelist = null;
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String request = NET_HOST + "resume/get_training_exps";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					JSONArray array = jObject.getJSONArray("data");
					if (array != null) {
						certificatelist = new ArrayList<Training>();
						for (int i = 0; i < array.length(); i++) {
							JSONObject data = (JSONObject) array.get(i);
							Training certificate = new Training();
							certificate.setId(data.getString("id"));
							certificate.setUser_id(data.optInt("user_id"));
							certificate.setInstitutions_cn(data
									.optString("institutions_cn"));
							certificate.setInstitutions_en(data
									.optString("institutions_en"));
							certificate.setBegin_year(data
									.optString("begin_year"));
							certificate.setBegin_month(data
									.optString("begin_month"));
							certificate.setEnd_year(data.optString("end_year"));
							certificate.setEnd_month(data
									.optString("end_month"));
							certificate.setCourse_cn(data
									.optString("course_cn"));
							certificate.setCourse_en(data
									.optString("course_en"));
							certificate.setCertificates_cn(data
									.optString("certificates_cn"));
							certificate.setCourse_en(data
									.optString("certificates_en"));
							certificate.setDetail_cn(data
									.optString("detail_cn"));
							certificate.setDetail_en(data
									.optString("detail_en"));
							certificate.setLocation(data.optString("location"));
							if (!StringUtils.isEmpty(certificate.getLocation())
									&& !"null"
											.equals(certificate.getLocation())) {
								certificate
										.setLocation_txt(AddressController.mapAreas
												.get(certificate.getLocation())
												.getValue());
							}
							// certificate.setLocation_txt(AddressController.mapAreas.get(certificate.getLocation()).getValue());
							certificate.setModify_time(data
									.optString("modify_time"));
							certificate.setAdd_time(data.optString("add_time"));
							certificatelist.add(certificate);
						}
						return certificatelist;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}
		return certificatelist;
	}

	/**
	 * 删除培训经历
	 */
	public EorrerBean delete_training_exp(String train_id) {
		EorrerBean eb = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "resume/delete_training_exp";
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("train_id", train_id));
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					eb = new EorrerBean();
					eb.status = jObject.getInt("status");
					if (jObject.has("errCode"))
						eb.errCode = jObject.getInt("errCode");
					if (jObject.has("errMsg"))
						eb.errMsg = jObject.getString("errMsg");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return eb;

			}
		}
		return eb;
	}

	/**
	 * 设置/添加培训经历
	 * 
	 * 接口地址
	 * 
	 * POST resume/set_training_exp
	 */
	public int set_training_exp(String institutions_cn, String begin_year,
			String begin_month, String end_year, String end_month,
			String certificates_cn, String detail_cn, String location, String id) {
		int state = 0;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "resume/set_training_exp";
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("id", id));
		nv.add(new BasicNameValuePair("user_id", ""));
		nv.add(new BasicNameValuePair("institutions_cn", institutions_cn));
		nv.add(new BasicNameValuePair("institutions_en", "institutions_en"));
		nv.add(new BasicNameValuePair("begin_year", begin_year + ""));
		nv.add(new BasicNameValuePair("begin_month", begin_month + ""));
		nv.add(new BasicNameValuePair("end_year", end_year + ""));
		nv.add(new BasicNameValuePair("end_month", end_month + ""));
		nv.add(new BasicNameValuePair("course_en", "course_en"));
		nv.add(new BasicNameValuePair("certificates_en", "certificates_en"));
		nv.add(new BasicNameValuePair("detail_en", "detail_en"));
		nv.add(new BasicNameValuePair("modify_time", "modify_time"));
		nv.add(new BasicNameValuePair("add_time", "add_time"));
		nv.add(new BasicNameValuePair("course_cn", ""));
		nv.add(new BasicNameValuePair("certificates_cn", certificates_cn));
		nv.add(new BasicNameValuePair("detail_cn", detail_cn));
		nv.add(new BasicNameValuePair("location", location));
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					state = jObject.getInt("status");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return -1;

			}
		}
		return state;
	}

	/**
	 * 获取技能和特长
	 */
	public List<Skills> get_skills() {
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		ArrayList<Skills> skillslist = null;
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String request = NET_HOST + "resume/get_skills";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					JSONArray array = jObject.getJSONArray("data");
					if (array != null) {
						skillslist = new ArrayList<Skills>();
						for (int i = 0; i < array.length(); i++) {
							JSONObject data = (JSONObject) array.get(i);
							Skills skills = new Skills();
							skills.setId(data.getString("id"));
							skills.setUser_id(data.optInt("user_id"));
							skills.setSkill_cn(data.optString("skill_cn"));
							skills.setSkill_en(data.optString("skill_en"));
							skills.setAbility(data.optInt("ability"));
							skills.setAbility_txt(FrameConfigures.map_master_degree
									.get(skills.getAbility() + "").value);
							skills.setDetail_cn(data.optString("detail_cn"));
							skills.setDetail_en(data.optString("detail_en"));
							skills.setFile_id(data.optString("file_id"));
							skills.setModify_time(data.optString("modify_time"));
							skills.setAdd_time(data.optString("add_time"));
							skillslist.add(skills);
						}
						return skillslist;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}
		return skillslist;
	}

	/**
	 * 删除技能与特长
	 */
	public EorrerBean delete_skill(String skill_id) {
		EorrerBean eb = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "resume/delete_skill";
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("skill_id", skill_id));
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					eb = new EorrerBean();
					eb.status = jObject.getInt("status");
					if (jObject.has("errCode"))
						eb.errCode = jObject.getInt("errCode");
					if (jObject.has("errMsg"))
						eb.errMsg = jObject.getString("errMsg");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return eb;

			}
		}
		return eb;
	}

	/**
	 * 添加 技能与特长
	 */
	public int set_skill(String skill_cn, int ability, String detail_cn,
			String id) {
		int state = 0;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "resume/set_skill";
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("skill_cn", skill_cn));
		nv.add(new BasicNameValuePair("ability", ability + ""));
		nv.add(new BasicNameValuePair("detail_cn", detail_cn));
		nv.add(new BasicNameValuePair("id", id));
		nv.add(new BasicNameValuePair("user_id", mApplication.user.getId()));
		/*
		 * nv.add(new BasicNameValuePair("skill_en", "skill_en")); nv.add(new
		 * BasicNameValuePair("detail_en", "detail_en")); nv.add(new
		 * BasicNameValuePair("file_id", 0 + "")); nv.add(new
		 * BasicNameValuePair("modify_time", "modify_time")); nv.add(new
		 * BasicNameValuePair("add_time", "add_time"));
		 */
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					state = jObject.getInt("status");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return -1;

			}
		}
		return state;
	}

	/**
	 * 获取语言
	 */
	public List<ResumeLanguage> get_languages() {
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		ArrayList<ResumeLanguage> skillslist = null;
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String request = NET_HOST + "resume/get_languages";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					JSONArray array = jObject.getJSONArray("data");
					if (array != null) {
						skillslist = new ArrayList<ResumeLanguage>();
						for (int i = 0; i < array.length(); i++) {
							JSONObject data = (JSONObject) array.get(i);
							ResumeLanguage skills = new ResumeLanguage();
							skills.userid = data.getString("user_id");
							skills.id = data.getString("id");
							skills.language_key = data.optString("language");
							skills.language_value = FrameConfigures.map_language
									.get(skills.language_key).value;
							skills.mastery_key = data.optString("ability");
							skills.mastery_value = FrameConfigures.map_master_degree
									.get(skills.mastery_key).value;
							skillslist.add(skills);
						}
						return skillslist;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}
		return skillslist;
	}

	/**
	 * 添加 语言
	 */
	public int set_language(int language, int ability, String id) {
		int state = 0;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "resume/set_language";
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("language", language + ""));
		nv.add(new BasicNameValuePair("ability", ability + ""));
		nv.add(new BasicNameValuePair("id", id ));
		nv.add(new BasicNameValuePair("user_id", mApplication.getUser().getId()));
		/*
		 * nv.add(new BasicNameValuePair("modify_time", "")); nv.add(new
		 * BasicNameValuePair("add_time", ""));
		 */
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					state = jObject.getInt("status");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return -1;

			}
		}
		return state;
	}

	/**
	 * 删除语言
	 */
	public EorrerBean delete_language(String lang_id) {
		EorrerBean eb = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "resume/delete_language";
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("lang_id", lang_id));
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					eb = new EorrerBean();
					eb.status = jObject.getInt("status");
					if (jObject.has("errCode"))
						eb.errCode = jObject.getInt("errCode");
					if (jObject.has("errMsg"))
						eb.errMsg = jObject.getString("errMsg");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return eb;

			}
		}
		return eb;
	}
}
