package zjdf.zhaogongzuo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.entity.MicroResume;
import zjdf.zhaogongzuo.entity.ResumeEducation;
import zjdf.zhaogongzuo.entity.ResumeInformation;
import zjdf.zhaogongzuo.entity.ResumeIntentionDesiredCompanyType;
import zjdf.zhaogongzuo.entity.ResumeIntentionDesiredJob;
import zjdf.zhaogongzuo.entity.ResumeIntentionDesiredLocation;
import zjdf.zhaogongzuo.entity.ResumeIntentionDesiredPosition;
import zjdf.zhaogongzuo.entity.ResumeJobIntention;
import zjdf.zhaogongzuo.entity.ResumeWorks;
import zjdf.zhaogongzuo.entity.Status;
import zjdf.zhaogongzuo.net.HttpTools;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.util.Log;

/**
 * 简历内容基本信息读取和更改
 * 
 * @author Administrator
 * 
 */
public class ResumeInformationControllers extends MyResumeConttroller {

	public ResumeInformationControllers(Context context) {
		super(context);
	}

	/**
	 * 我的简历 状体初始化
	 * 
	 * @return
	 */
	public Status getStatus() {

		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		Status status = null;
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String request = NET_HOST + "resume/status";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					JSONObject data = jObject.getJSONObject("data");
					if (data != null) {
						status = new Status();
						status.setCompletion(data.optDouble("completion"));
						status.setAvatar(data.getString("avatar"));
						status.setJob_status(data.optInt("job_status"));
						status.setPrivacy(data.optInt("privacy"));
						status.setMobile(data.optString("mobile"));
						if (status.getJob_status() == 0) {
							status.setJob_status_text("正在找工作");
						} else {
							status.setJob_status_text(data
									.getString("job_status_text"));
						}
						status.setInfo_text(data.getString("info_text"));
						status.setIntention_text(data
								.getString("intention_text"));
						status.setEducation_text(data
								.getString("education_text"));
						status.setExperience_text(data
								.getString("experience_text"));
						status.setLanguage_text(data.getString("language_text"));
						status.setSkill_text(data.getString("skill_text"));
						status.setTraining_text(data.getString("training_text"));
						status.setCertificate_text(data
								.getString("certificate_text"));
						return status;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}
		return status;
	}

	/**
	 * 获取简历基本信息
	 * 
	 * 接口地址
	 * 
	 * POST resume/get_base
	 */

	public ResumeInformation get_base() {
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		ResumeInformation information = null;
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String request = NET_HOST + "resume/get_base";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					JSONObject data = jObject.getJSONObject("data");
					if (data != null) {
						information = new ResumeInformation();
						information.setName(data.getString("true_name_cn"));
						information.setGender(data.optInt("gender"));
						information.setGender_text(FrameConfigures.map_gender.get(information.getGender() + "").value);
						information.setBirthday(data.getString("birthday"));
						information.setWork_exps(data.optInt("work_year"));
						// information.setWork_exps_text(FrameConfigures.map_works.get(information.getWork_exps()
						// + "").value);
						if (data.getString("graduation_time") == "null"|| "".equals(data.getString("graduation_time"))) {
							information.setGraduate_date("空");
						} else {
							information.setGraduate_date(data.getString("graduation_time"));
						}
						information.setEdu(data.optInt("degree"));
						if (information.getEdu() == 0) {
							information.setEdu_text("空");
						} else {
							information
									.setEdu_text(FrameConfigures.map_educations
											.get(information.getEdu() + "").value);
						}
						information.setCurrent_place(data
								.optString("current_location"));
						if (information.getCurrent_place() != null
								&& !"null".equals(information
										.getCurrent_place())) {
							information
									.setCurrent_place_text(AddressController.mapAreas
											.get(information.getCurrent_place()
													+ "").getValue());
						}
						information.setDomicile_place(data
								.optString("domicile_location"));
						if (information.getDomicile_place() != null
								&& !"null".equals(information
										.getDomicile_place())) {
							information
									.setDomicile_place_text(AddressController.mapAreas
											.get(information
													.getDomicile_place() + "")
											.getValue());
						}
						information.setPhone(data.optString("mobile"));
						information.setHeight(data.optInt("height"));
						information.setEmail(data.getString("email"));
						information.setWeight(data.optInt("weight"));
						information.setId_type(data.optString("id_type"));
						if (information.getId_type().contains("0")) {
							information.setId_type_text("空");
						} else {
							information
									.setId_type_text(FrameConfigures.map_id_type
											.get(information.getId_type()).value);
						}
						information.setId_num(data.getString("id_number"));
						return information;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}
		return information;
	}

	/**
	 * 设置基本信息
	 * 
	 * @return
	 */
	public int set_base(String name, int gender, String birthday,
			int work_exps, String graduate_date, int education,
			String current_place, String domicile_place, String phone,
			String height, String email, String weight, int id_type,
			String id_number) {
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		int number = 0;
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("true_name_cn", name));
		nv.add(new BasicNameValuePair("gender", gender + ""));
		nv.add(new BasicNameValuePair("birthday", birthday));
		nv.add(new BasicNameValuePair("work_year", work_exps + ""));
		nv.add(new BasicNameValuePair("graduation_time", graduate_date));
		nv.add(new BasicNameValuePair("degree", education + ""));
		nv.add(new BasicNameValuePair("current_location", current_place));
		nv.add(new BasicNameValuePair("domicile_location", domicile_place));
		nv.add(new BasicNameValuePair("mobile", phone));
		nv.add(new BasicNameValuePair("height", height + ""));
		nv.add(new BasicNameValuePair("email", email));
		nv.add(new BasicNameValuePair("weight", weight + ""));
		nv.add(new BasicNameValuePair("id_type", id_type + ""));
		nv.add(new BasicNameValuePair("id_number", id_number));
		String request = NET_HOST + "resume/set_base";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (jObject.has("status")) {
						number = jObject.getInt("status");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return -1;
			}
		}
		return number;
	}

	/**
	 * 获取求职意向
	 * 
	 * 接口地址
	 * 
	 * POST resume/get_intention
	 */
	public ResumeJobIntention get_intentions() {
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		ResumeJobIntention intension = null;
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String request = NET_HOST + "resume/get_intention";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				Log.i("TEST", "sreResult=" + sreResult);
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status") && jObject.getInt("status") == 1
						&& jObject.has("data")) {
					intension = new ResumeJobIntention();
					JSONObject data = jObject.getJSONObject("data");
					List<ResumeIntentionDesiredCompanyType> companyTypes = parseResumeIntentionCompanyTypes(data
							.getJSONArray("PersonDesiredCompanyType"));
					if (companyTypes != null && companyTypes.size() > 0) {
						intension.id = companyTypes.get(0).user_id;
						intension.userid = companyTypes.get(0).user_id;
					}
					ResumeIntentionDesiredJob job = parseResumeIntentionDesiredJobs(
							data.getJSONArray("PersonDesiredJob")).get(0);
					List<ResumeIntentionDesiredLocation> locations = parseResumeIntentionDesiredLocation(data
							.getJSONArray("PersonDesiredLocation"));
					List<ResumeIntentionDesiredPosition> positions = parseResumeIntentionDesiredPositions(data
							.getJSONArray("PersonDesiredPosition"));
					intension.desiredCompanyTypes = companyTypes;
					intension.desiredJob = job;
					intension.desiredLocations = locations;
					intension.desiredPositions = positions;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}
		return intension;
	}

	/**
	 * 解析求职意向————企业类别
	 * 
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	private List<ResumeIntentionDesiredCompanyType> parseResumeIntentionCompanyTypes(
			JSONArray array) throws JSONException {
		List<ResumeIntentionDesiredCompanyType> companyTypes = null;
		if (array != null && array.length() > 0) {
			int n = array.length();
			companyTypes = new ArrayList<ResumeIntentionDesiredCompanyType>(n);
			ResumeIntentionDesiredCompanyType companyType = null;
			for (int i = 0; i < n; i++) {
				JSONObject object = array.getJSONObject(i);
				companyType = new ResumeIntentionDesiredCompanyType();
				companyType.c_id = object.getString("id");
				companyType.user_id = object.getString("user_id");
				companyType.parent_industry_code = object.getString("industry");
				if (!StringUtils.isEmpty(companyType.parent_industry_code)) {
					companyType.parent_industry_value = FrameConfigures.map_industry
							.get(companyType.parent_industry_code) == null ? ""
							: FrameConfigures.map_industry
									.get(companyType.parent_industry_code).value;
				}
				companyType.industry_code = object.getString("company_type");
				if (!StringUtils.isEmpty(companyType.industry_code)) {
					companyType.industry_value = FrameConfigures.map_industry
							.get(companyType.industry_code).value;
				}
				if (object.has("star")) {
					companyType.star_code = object.getString("star");
					if (!StringUtils.isEmpty(companyType.star_code)) {
						companyType.star_value = FrameConfigures.map_star_level
								.get(companyType.star_code).value;
					}
				}
				companyType.modify_time = object.getString("modify_time");
				companyType.add_time = object.getString("add_time");
				companyTypes.add(companyType);
			}

		}
		return companyTypes;
	}

	/**
	 * 解析求职意向————基本信息
	 * 
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	private List<ResumeIntentionDesiredJob> parseResumeIntentionDesiredJobs(
			JSONArray array) throws JSONException {
		List<ResumeIntentionDesiredJob> desiredJobs = null;
		if (array != null && array.length() > 0) {
			int n = array.length();
			desiredJobs = new ArrayList<ResumeIntentionDesiredJob>(n);
			ResumeIntentionDesiredJob desiredJob = null;
			for (int i = 0; i < n; i++) {
				JSONObject object = array.getJSONObject(i);
				desiredJob = new ResumeIntentionDesiredJob();
				desiredJob.user_id = object.getString("user_id");
				desiredJob.work_mode_code = object.getString("work_mode");
				desiredJob.work_mode_value = FrameConfigures.map_work_mode
						.get(desiredJob.work_mode_code).value;
				desiredJob.current_salary_mode_key = object
						.getString("current_salary_mode");
				if (!StringUtils.isEmpty(desiredJob.current_salary_mode_key))
					desiredJob.current_salary_mode_value = FrameConfigures.map_salary_mode
							.get(desiredJob.current_salary_mode_key).value;
				desiredJob.current_salary_currency_key = object
						.getString("current_salary_currency");
				if (!StringUtils
						.isEmpty(desiredJob.current_salary_currency_key))
					desiredJob.current_salary_currency_value = FrameConfigures.map_salary_currency
							.get(desiredJob.current_salary_currency_key).value;
				desiredJob.current_salary = object.getString("current_salary");
				desiredJob.current_salary_is_show = object
						.getString("current_salary_is_show");
				desiredJob.desired_salary_mode_key = object
						.getString("desired_salary_mode");
				if (!StringUtils.isEmpty(desiredJob.desired_salary_mode_key))
					desiredJob.desired_salary_mode_value = FrameConfigures.map_salary_mode
							.get(desiredJob.desired_salary_mode_key).value;
				desiredJob.desired_salary_currency_key = object
						.getString("desired_salary_currency");
				if (!StringUtils
						.isEmpty(desiredJob.desired_salary_currency_key))
					desiredJob.desired_salary_currency_value = FrameConfigures.map_salary_currency
							.get(desiredJob.desired_salary_currency_key).value;
				desiredJob.desired_salary_key = object
						.getString("desired_salary");
				if (!StringUtils.isEmpty(desiredJob.desired_salary_key)) {
					if (desiredJob.desired_salary_mode_key.contains("1")) {
						if (FrameConfigures.map_salary_month
								.containsKey(desiredJob.desired_salary_key)) {
							desiredJob.desired_salary_value = FrameConfigures.map_salary_month
									.get(desiredJob.desired_salary_key).value;
						}
					} else if (FrameConfigures.map_salary_year
							.containsKey(desiredJob.desired_salary_key)) {
						desiredJob.desired_salary_value = FrameConfigures.map_salary_year
								.get(desiredJob.desired_salary_key).value;
					}
				} else {
					desiredJob.desired_salary_key = "0";
					desiredJob.desired_salary_value = "不限";
				}
				desiredJob.desired_salary_is_show = object
						.getString("desired_salary_is_show");
				desiredJob.arrival_time_key = object.getString("arrival_time");
				if (!StringUtils.isEmpty(desiredJob.arrival_time_key))
					desiredJob.arrival_time_value = FrameConfigures.map_arrival_time
							.get(desiredJob.arrival_time_key).value;
				desiredJob.modify_time = object.getString("modify_time");
				desiredJob.add_time = object.getString("add_time");
				desiredJobs.add(desiredJob);
			}

		}
		return desiredJobs;
	}

	/**
	 * 解析求职意向————意向地点
	 * 
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	private List<ResumeIntentionDesiredLocation> parseResumeIntentionDesiredLocation(
			JSONArray array) throws JSONException {
		List<ResumeIntentionDesiredLocation> desiredLocations = null;
		if (array != null && array.length() > 0) {
			int n = array.length();
			desiredLocations = new ArrayList<ResumeIntentionDesiredLocation>(n);
			ResumeIntentionDesiredLocation desiredLocation = null;
			for (int i = 0; i < n; i++) {
				JSONObject object = array.getJSONObject(i);
				desiredLocation = new ResumeIntentionDesiredLocation();
				desiredLocation.l_id = object.getString("id");
				desiredLocation.user_id = object.getString("user_id");
				desiredLocation.location_code = object.getString("location");
				if (!StringUtils.isEmpty(desiredLocation.location_code)
						&& !"null"
								.equalsIgnoreCase(desiredLocation.location_code)) {
					desiredLocation.location_value = AddressController.mapAreas
							.get(desiredLocation.location_code) == null ? ""
							: AddressController.mapAreas.get(
									desiredLocation.location_code).getValue();
				}
				desiredLocation.modify_time = object.getString("modify_time");
				desiredLocation.add_time = object.getString("add_time");
				desiredLocations.add(desiredLocation);
			}

		}
		return desiredLocations;
	}

	/**
	 * 解析求职意向————意向职位
	 * 
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	private List<ResumeIntentionDesiredPosition> parseResumeIntentionDesiredPositions(
			JSONArray array) throws JSONException {
		List<ResumeIntentionDesiredPosition> desiredPositions = null;
		if (array != null && array.length() > 0) {
			int n = array.length();
			desiredPositions = new ArrayList<ResumeIntentionDesiredPosition>(n);
			ResumeIntentionDesiredPosition desiredPosition = null;
			for (int i = 0; i < n; i++) {
				JSONObject object = array.getJSONObject(i);
				desiredPosition = new ResumeIntentionDesiredPosition();
				desiredPosition.p_id = object.getString("id");
				desiredPosition.user_id = object.getString("user_id");
				desiredPosition.position_code = object.getString("position");
				if (!StringUtils.isEmpty(desiredPosition.position_code)
						&& !"null"
								.equalsIgnoreCase(desiredPosition.position_code))
					desiredPosition.position_value = PositionClassifyController.mapPositionClassifies
							.get(desiredPosition.position_code) == null ? ""
							: PositionClassifyController.mapPositionClassifies
									.get(desiredPosition.position_code)
									.getValue();
				desiredPosition.modify_time = object.getString("modify_time");
				desiredPosition.add_time = object.getString("add_time");
				desiredPositions.add(desiredPosition);
			}

		}
		return desiredPositions;
	}

	/**
	 * 获取微简历
	 * 
	 * @return
	 */
	public MicroResume getMicroResume() {
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		MicroResume m = null;
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String request = NET_HOST + "resume/get_micro_resume";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status") && jObject.getInt("status") == 1) {
					m = new MicroResume();
					JSONObject data = jObject.getJSONObject("data");
					m.user_id = data.getString("user_id");
					m.mobile = data.getString("mobile");
					m.modify_time = data.getString("modify_time");
					m.true_name_cn = data.getString("true_name_cn");
					m.birthday = data.getString("birthday");
					m.gender_key = data.getString("gender");
					m.gender_value = FrameConfigures.map_gender
							.get(m.gender_key).value;
					m.height = data.getString("height");
					m.degree_key = data.getString("degree");
					m.degree_value = FrameConfigures.map_educations
							.get(m.degree_key).value;
					m.person_desired_location_key = data
							.getString("person_desired_location");
					if (!StringUtils.isEmpty(m.person_desired_location_key)
							&& m.person_desired_location_key.contains(",")) {
						String[] locale = m.person_desired_location_key
								.split(",");
						StringBuffer location_value = new StringBuffer(
								locale.length);
						for (int i = 0; i < locale.length; i++) {
							location_value.append(AddressController.mapAreas
									.get(locale[i]).getValue() + ",");
						}
						location_value.deleteCharAt(location_value
								.lastIndexOf(","));
						m.person_desired_location_value = location_value
								.toString();
					}
					m.person_desired_position_key = data
							.getString("person_desired_position");
					if (!StringUtils.isEmpty(m.person_desired_position_key)
							&& m.person_desired_position_key.contains(",")) {
						String[] locale = m.person_desired_position_key
								.split(",");
						StringBuffer location_value = new StringBuffer(
								locale.length);
						for (int i = 0; i < locale.length; i++) {
							location_value
									.append(PositionClassifyController.mapPositionClassifies
											.get(locale[i]).getValue() + ",");
						}
						location_value.deleteCharAt(location_value
								.lastIndexOf(","));
						m.person_desired_position_value = location_value
								.toString();
					}
					m.desired_salary_key = data.getString("desired_salary");
					m.desired_salary_value = FrameConfigures.map_salary_month
							.get(m.desired_salary_key).value;

				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}
		return m;
	}

	/**
	 * 更新求职意向
	 * 
	 * @param jobIntention
	 * 
	 */
	public EorrerBean setIntention(ResumeJobIntention jobIntention) {
		EorrerBean eb = null;
		if (jobIntention == null) {
			return eb;
		}
		Log.i("TEST",
				"companyTypesToPostString="
						+ jobIntention.companyTypesToPostString());
		Log.i("TEST",
				"desiredJobsToPostString="
						+ jobIntention.desiredJobsToPostString());
		Log.i("TEST",
				"desiredLocationsToPostString="
						+ jobIntention.desiredLocationsToPostString());
		Log.i("TEST",
				"desiredPositionsToPostString="
						+ jobIntention.desiredPositionsToPostString());
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("PersonDesiredCompanyType", jobIntention
				.companyTypesToPostString()));
		nv.add(new BasicNameValuePair("PersonDesiredJob", jobIntention
				.desiredJobsToPostString()));
		nv.add(new BasicNameValuePair("PersonDesiredLocation", jobIntention
				.desiredLocationsToPostString()));
		nv.add(new BasicNameValuePair("PersonDesiredPosition", jobIntention
				.desiredPositionsToPostString()));
		String request = NET_HOST + "resume/set_intention";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					eb = new EorrerBean();
					eb.status = jObject.getInt("status");
					if (jObject.has("errCode")) {
						eb.errCode = jObject.getInt("errCode");
					}
					if (jObject.has("errMsg")) {
						eb.errMsg = jObject.getString("errMsg");
					}
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
	 * 更新求职意向
	 * 
	 * @param work_mode
	 *            工作类型 必填
	 * @param work_place
	 *            工作地区编码 必填
	 * @param intention_position
	 *            意向职位编码 必填
	 * @param intention_industry
	 *            意向行业编码 必填
	 * @param star_level
	 *            星级 可以不填，缺省值为?<0的
	 * @param arrive_time
	 *            到岗时间 可以不填，缺省值为?<0的
	 * @param current_salary_mode
	 *            目前薪资类型，月薪或者年薪 必填
	 * @param current_salary_currency
	 *            目前薪资货币 必填
	 * @param current_salary
	 *            目前薪资数额 必填
	 * @param current_salary_isshow
	 *            企业是否可查看目前薪资 必填
	 * @param desired_salary_mode
	 *            期望薪资类型，月薪或者年薪 必填
	 * @param desired_salary_currency
	 *            期望薪资货币 必填
	 * @param desired_salary
	 *            期望薪资范围 必填
	 * @param desired_salary_isshow
	 *            期望薪资是否显示，否则显示面议 必填
	 * @return
	 */
	public int setJobIntention(int work_mode, String work_place,
			String intention_position, String intention_industry,
			int star_level, int arrive_time, int current_salary_mode,
			int current_salary_currency, int current_salary,
			int current_salary_isshow, int desired_salary_mode,
			int desired_salary_currency, int desired_salary,
			int desired_salary_isshow) {

		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		int number = -1;
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("work_mode", work_mode + ""));
		nv.add(new BasicNameValuePair("work_place", work_place));
		nv.add(new BasicNameValuePair("intention_position", intention_position));
		nv.add(new BasicNameValuePair("intention_industry", intention_industry));
		if (star_level > -1) {
			nv.add(new BasicNameValuePair("star_level", star_level + ""));
		}
		if (arrive_time > -1) {
			nv.add(new BasicNameValuePair("arrive_time", arrive_time + ""));
		}
		nv.add(new BasicNameValuePair("current_salary_mode",
				current_salary_mode + ""));
		nv.add(new BasicNameValuePair("current_salary_currency",
				current_salary_currency + ""));
		nv.add(new BasicNameValuePair("current_salary", current_salary + ""));
		nv.add(new BasicNameValuePair("current_salary_isshow",
				current_salary_isshow + ""));
		nv.add(new BasicNameValuePair("desired_salary_mode",
				desired_salary_mode + ""));
		nv.add(new BasicNameValuePair("desired_salary_currency",
				desired_salary_currency + ""));
		nv.add(new BasicNameValuePair("desired_salary", desired_salary + ""));
		nv.add(new BasicNameValuePair("desired_salary_isshow",
				desired_salary_isshow + ""));
		String request = NET_HOST + "resume/set_intention";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (jObject.has("status")) {
						number = jObject.getInt("status");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return -1;
			}
		}
		return number;
	}

	/**
	 * 解析教育经历
	 * 
	 * @param object
	 *            教育经历Json对象
	 * @return
	 * @throws JSONException
	 */
	private ResumeEducation parseEducation(JSONObject object)
			throws JSONException {
		if (object == null) {
			return null;
		}
		ResumeEducation education = new ResumeEducation();
		education.id = object.getString("id");
		education.userid = object.getString("user_id");
		education.start_time_year = object.getString("begin_year");
		education.start_time_month = object.getString("begin_month");
		education.end_time_year = object.getString("end_year");
		education.end_time_month = object.getString("end_month");
		education.school_key = object.getString("school_id");
		education.school_value = object.getString("school_cn");
		education.major_key = object.getString("major_id");
		education.major_value = object.getString("major_cn");
		education.edu_degree_key = object.getString("degree");
		education.edu_degree_value = FrameConfigures.map_educations
				.get(education.edu_degree_key).value;
		education.area_key = object.getString("location");
		if (education.area_key != null && !"null".equals(education.area_key)
				&& !"".equals(education.area_key)) {
			education.area_value = AddressController.mapAreas.get(
					education.area_key).getValue();
		}
		education.study_abroad_key = object.getString("is_overseas");
		education.study_abroad_value = (education.study_abroad_key
				.contains("1") ? "是" : "否");
		return education;
	}

	/**
	 * 获取教育经历
	 * 
	 * @return List<ResumeEducation>
	 */
	public List<ResumeEducation> getEducations() {
		List<ResumeEducation> educations = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String request = NET_HOST + "resume/get_edu_exps";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.optInt("status") == 1 && jObject.has("data")) {
					JSONArray array = jObject.getJSONArray("data");
					int an = array.length();
					if (an > 0) {
						educations = new ArrayList<ResumeEducation>(an);
						ResumeEducation education = null;
						for (int i = 0; i < an; i++) {
							education = parseEducation(array.getJSONObject(i));
							educations.add(education);
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}
		return educations;
	}

	/**
	 * 保存、修改教育经历.当edu_id为""时添加新的教育经验
	 * 
	 * @param education
	 *            教育经历对象
	 * @return
	 */
	public int setJobEducation(ResumeEducation education) {
		int state = -1;
		if (education == null) {
			return state;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("id", education.id));
		nv.add(new BasicNameValuePair("user_id", education.userid));
		nv.add(new BasicNameValuePair("begin_year", education.start_time_year));
		nv.add(new BasicNameValuePair("begin_month", education.start_time_month));
		nv.add(new BasicNameValuePair("end_year", education.end_time_year));
		nv.add(new BasicNameValuePair("end_month", education.end_time_month));
		nv.add(new BasicNameValuePair("school_cn", education.school_value));
		nv.add(new BasicNameValuePair("major_id", education.major_key));
		nv.add(new BasicNameValuePair("major_cn", education.major_value));
		nv.add(new BasicNameValuePair("degree", education.edu_degree_key));
		nv.add(new BasicNameValuePair("location", education.area_key));
		nv.add(new BasicNameValuePair("is_overseas", education.study_abroad_key));
		// nv.add(new BasicNameValuePair("school_id",education.school_key));
		String sreResult = HttpTools.getHttpRequestString(nv, NET_HOST
				+ "resume/set_edu_exp");
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					state = jObject.optInt("status");
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
	 * 删除教育经历
	 * 
	 * @param edu_id
	 *            教育经历id
	 * @return
	 */
	public EorrerBean deleteJobEducation(String edu_id) {
		EorrerBean eb = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("edu_exp_id", edu_id));
		String sreResult = HttpTools.getHttpRequestString(nv, NET_HOST
				+ "resume/delete_edu_exp");
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
	 * 解析工作经验
	 * 
	 * @param object
	 * @return
	 * @throws JSONException
	 */
	private ResumeWorks parseWorks(JSONObject object) throws JSONException {
		if (object == null) {
			return null;
		}
		ResumeWorks works = new ResumeWorks();
		works.id = object.optString("id");
		works.userid = object.getString("p_userid");
		works.begin_time_year = object.getString("begin_year");
		works.begin_time_month = object.getString("begin_month");
		works.end_time_year = object.getString("end_year");
		works.end_time_month = object.getString("end_month");
		works.enterprise_value = object.getString("company_name_cn");
		works.industry_key = object.getString("company_industry");
		if (works.industry_key != null || "".equals(works.industry_key)) {
			// works.industry_value =
			// FrameConfigures.map_company_type.get(works.industry_key).value;
			if (works.industry_key.contains("1")) {
				works.industry_value = "酒店业";
			}
			if (works.industry_key.contains("2")) {
				works.industry_value = "餐饮业";
			}
			if (works.industry_key.contains("3")) {
				works.industry_value = "娱乐业";
			}
			if (works.industry_key.contains("4")) {
				works.industry_value = "旅行社";
			}
			if (works.industry_key.contains("5")) {
				works.industry_value = "旅游用品业";
			}
			if (works.industry_key.contains("6")) {
				works.industry_value = "其他行业";
			}
		}
		works.position_key = object.getString("position_id");
		works.position_value = PositionClassifyController.mapPositionClassifies
				.get(works.position_key).getValue();
		works.area_key = object.getString("location");
		if (!StringUtils.isEmpty(works.area_key)
				&& !"null".equals(works.area_key)) {
			works.area_value = AddressController.mapAreas.get(works.area_key)
					.getValue();
		}
		return works;
	}

	/**
	 * 获取工作经验列表
	 * 
	 * @return
	 */
	public List<ResumeWorks> getResumeWorks() {
		List<ResumeWorks> works = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String sreResult = HttpTools.getHttpRequestString(nv, NET_HOST
				+ "resume/get_work_exps");
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status") && jObject.getInt("status") == 1) {
					JSONArray array = jObject.getJSONArray("data");
					int nn = array.length();
					if (nn > 0) {
						works = new ArrayList<ResumeWorks>(nn);
						for (int i = 0; i < nn; i++) {
							ResumeWorks work = parseWorks(array
									.getJSONObject(i));
							if (work != null)
								works.add(work);
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}
		return works;
	}

	/**
	 * 修改或者添加工作经验 当work的id为“”是是添加工作经验
	 * 
	 * @param work
	 *            工作经验对象
	 * @return
	 */
	public int setResumeWorks(ResumeWorks work) {
		int state = -1;
		if (work == null) {
			return state;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		if (!StringUtils.isEmpty(work.id)) {
			nv.add(new BasicNameValuePair("id", work.id + ""));
		}
		nv.add(new BasicNameValuePair("user_id", work.userid));
		nv.add(new BasicNameValuePair("begin_year", work.begin_time_year));
		nv.add(new BasicNameValuePair("begin_month", work.begin_time_month));
		nv.add(new BasicNameValuePair("end_year", work.end_time_year));
		nv.add(new BasicNameValuePair("end_month", work.end_time_month));
		nv.add(new BasicNameValuePair("company_name_cn", work.enterprise_value));
		nv.add(new BasicNameValuePair("company_industry", work.industry_key));
		nv.add(new BasicNameValuePair("position_id", work.position_key));
		nv.add(new BasicNameValuePair("location", work.area_key));
		String sreResult = HttpTools.getHttpRequestString(nv, NET_HOST
				+ "resume/set_work_exp");
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					state = jObject.optInt("status");
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
	 * 删除工作经历
	 * 
	 * @param work_id
	 *            工作经历id
	 * @return
	 */
	public EorrerBean deleteResumeWorks(String work_id) {
		EorrerBean eb = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("work_exp_id", work_id));
		String sreResult = HttpTools.getHttpRequestString(nv, NET_HOST
				+ "resume/delete_work_exp");
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

	// 设置求职状态
	public int set_state(int state) {
		int flag = -1;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("job_status", state + ""));
		String sreResult = HttpTools.getHttpRequestString(nv, NET_HOST
				+ "resume/set_base");
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					flag = jObject.optInt("status");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return -1;
			}
		}
		return flag;
	}

	// 设置公开程度
	public int set_publicstate(int state) {
		int flag = -1;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("privacy", state + ""));
		String sreResult = HttpTools.getHttpRequestString(nv, NET_HOST
				+ "resume/set_base");
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					flag = jObject.optInt("status");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return -1;
			}
		}
		return flag;
	}
}
