/**
 * Copyright © 2014年4月17日 FindJob www.veryeast.com
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
import org.json.JSONException;
import org.json.JSONObject;

import zjdf.zhaogongzuo.entity.Company;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.net.HttpTools;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;

/**
 *<h2> 职位数据相关控制器</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月17日
 * @version 
 * @modify 
 * 
 */
public class PositionController extends BaseConttroller{

	public static int jobCounts=0;
	
	public PositionController(Context context){
		super(context);
	}
	
	/**
	 * 获取推荐的职位列表
	 *<pre>最多5个</pre>
	 * @param url	请求地址
	 * @param userToken 用户票据,必填
	 * @return 推荐的职位列表
	 */
	public List<Position> getRecommendPositions() {
		List<Position> positions=null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String result=HttpTools.getNetString_post(nv, NET_HOST+"user/recommended_jobs");
		if (!StringUtils.isEmpty(result)) {
			try {
				JSONObject jObject=new JSONObject(result);
				if (jObject.has("status")) {
					if (jObject.optInt("status")==1) {
						JSONObject data=jObject.getJSONObject("data");
						if (data.has("list")) {
							JSONArray array=data.getJSONArray("list");
							int nn=array.length();
							if (nn>0) {
								positions=new ArrayList<Position>(nn);
								for (int i = 0; i < nn; i++) {
									Position pp=parseRcommendPosition(array.getJSONObject(i));
									if (pp!=null) {
										positions.add(pp);
									}
								}
							}else {
								positions=getPositions();
							}
						}
					}else {
						positions=getPositions();
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return positions;
			}
		}else {
			positions=getPositions();
		}
		
		return positions;
	}
	
	/**
	 * 
	 *<pre>获取职位列表  </pre>
	 * @return List
	 */
	public List<Position> getPositions(){
		List<Position> positions=null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String result=HttpTools.getNetString_post(nv, NET_HOST+"job/search");
		if (!StringUtils.isEmpty(result)) {
			try {
				JSONObject jObject=new JSONObject(result);
				if (jObject.has("status")&&jObject.optInt("status")==1) {
					JSONObject data=jObject.getJSONObject("data");
					if (data.has("list")&&data.has("list")) {
						JSONArray array=data.getJSONArray("list");
						int nn=array.length()<6?array.length():6;
						positions=new ArrayList<Position>(nn);
						for (int i = 0; i < nn; i++) {
							Position pp=parsePosition(array.getJSONObject(i));
							if (pp!=null) {
								positions.add(pp);
							}
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return positions;
			}
		}
		
		return positions;
	}
	
	/**
	 * 
	 *<pre>获取职位列表  </pre>
	 * @param url 请求地址 ---必填
	 * @param keyword 关键字。为必须字段,可以为"" ---必填
	 * @param scope 关键字搜索范围。1为全文匹配，2为职位明匹配，3为企业名匹配。---必填
	 * @param page 页码，默认为1。
	 * @param size 每页大小 。默认为10。
	 * @param area_code 地区编码 .例：杭州 050100
	 * @param position_code 职位编码.例：前厅部员工1115
	 * @param updateTime 更新时间.-1:不限，0：今日最新，3：最近3天，5：最近5天，7：最近一周，14：最近两周，30：最近一个月，60：最近两个月，
	 * @param works 工作经验。 0:不限，1：一年以上，2：两年以上，3： 三年以上，4：五年以上，5：八年以上，6：十年以上
	 * @param educations 教育经验.0：不限，1：初中，2：高中：3：中技，4：中专，5：大专，6：本科，7：硕士，8：博士，9：博士后
	 * @param salary 薪资要求.0：不限，1:1001-2000,2:2001-3000,3:3001-5000,4：4500-6000,5:6001-8000,6:8001-10000
	 * @param room_board 是否提供食宿.0：不限，1：提供食宿，2：不提供，3：提供吃，4：提供住。
	 * @param work_mode 工作性质.0：不限，1：全职，2：兼职，3：实习，4：临时
	 * @return List
	 */
	public List<Position> getPositions(
			String keyword,
			int scope,
			int page,
			int size,
			String area_code,
			String position_code,
			int updateTime,
			int works,
			int educations,
			String salary,
			String room_board,
			String work_mode){
		jobCounts=0;
		List<Position> positions=null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("keyword", keyword));
		nv.add(new BasicNameValuePair("scope", scope+""));
		nv.add(new BasicNameValuePair("page", page+""));
		nv.add(new BasicNameValuePair("size", size+""));
		if (!StringUtils.isEmpty(area_code))
			nv.add(new BasicNameValuePair("area", area_code+""));
		if (!StringUtils.isEmpty(position_code))
			nv.add(new BasicNameValuePair("position", position_code+""));
			nv.add(new BasicNameValuePair("update_time", updateTime+""));
			nv.add(new BasicNameValuePair("work_year", works+""));
			nv.add(new BasicNameValuePair("education", educations+""));
			nv.add(new BasicNameValuePair("salary", salary+""));
			nv.add(new BasicNameValuePair("room_board", room_board+""));
			nv.add(new BasicNameValuePair("work_mode", work_mode+""));
		String result=HttpTools.getNetString_post(nv, NET_HOST+"job/search");
		if (!StringUtils.isEmpty(result)) {
			try {
				JSONObject jObject=new JSONObject(result);
				if (!StringUtils.isEmpty(jObject)&&jObject.optInt("status")==1) {
					JSONObject data=jObject.getJSONObject("data");
					if (!StringUtils.isEmpty(data)&&data.has("list")) {
						jobCounts=data.optInt("count");
						JSONArray array=data.getJSONArray("list");
						int nn=array.length();
						positions=new ArrayList<Position>(nn);
						for (int i = 0; i < nn; i++) {
							Position pp=parsePosition(array.getJSONObject(i));
							if (pp!=null) {
								positions.add(pp);
							}
						}
					}
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return positions;
			}
		}		
		return positions;
	}
	
	/**
	 * 解析职位列表的职位
	 *<pre>方法  </pre>
	 * @param object
	 * @return
	 */
	private Position parsePosition(JSONObject object){
		Position p=null;
		if (!StringUtils.isEmpty(object)) {
			p=new Position();
			String ids=object.optString("job_id");
			p.setIdStr(ids);
			p.setName(object.optString("job_name"));
			String cids=object.optString("company_id");
			p.setCompanyId(cids);
			p.setCompanyName(object.optString("company_name"));
			p.setUpdateTime(object.optString("update_time"));
			p.setSalary(object.optString("salary"));
			p.setAddress(object.optString("job_area"));
			int ishot=Integer.parseInt(object.optString("is_urgent"));
			p.setIs_urgent(ishot);
		}
		return p;
	}
	
	
	/**
	 * 解析推荐的（猜你想找的）职位列表的职位
	 *<pre>包涵反回过当前用户ID：“c_userid”
	 *没有解析</pre>
	 * @param object
	 * @return
	 */
	private Position parseRcommendPosition(JSONObject object){
		Position p=null;
		if (!StringUtils.isEmpty(object)) {
			p=new Position();
			String ids=object.optString("job_id");
			p.setIdStr(ids);
			p.setName(object.optString("job_name"));
			if (object.has("company_id")) {
				String cids=object.optString("company_id");
				p.setCompanyId(cids);
			}
			p.setCompanyName(object.optString("company_name"));
			p.setUpdateTime(object.optString("update_time"));
			p.setSalary(object.optString("salary"));
			p.setAddress(object.optString("work_place"));
//			int ishot=Integer.parseInt(object.optString("is_urgent"));
			p.setIs_urgent(object.optInt("is_urgent"));
		}
		
		return p;
	}
	
	/**
	 * 获取职位详情
	 *<pre>方法  </pre>
	 * @param apikey
	 * @param jobid		职位id
	 * @param user_ticket	用户票据(用于获取职位的收藏状态)
	 * @return
	 */
	public Position getPosition(String jobid) {
		Position position=null;
		if (StringUtils.isEmpty(jobid)) {
			return null;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("job_id", jobid));
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String result=HttpTools.getNetString_post(nv, NET_HOST+"job/detail");
		if (!StringUtils.isEmpty(result)) {
			try {
				JSONObject jObject=new JSONObject(result);
				if (jObject.has("status")&&jObject.optInt("status")==1) {
					JSONObject data=jObject.getJSONObject("data");
					position=parsePositionDetail(data);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
		return position;
	}
	
	
	/**
	 * 解析职位详细数据
	 * @param jObject
	 * @return 职位详情的实体对象
	 * @throws JSONException 
	 */
	private Position parsePositionDetail(JSONObject jObject) throws JSONException{
		if (StringUtils.isEmpty(jObject)) {
			return null;
		}
		Position p= new Position();
		String ids=jObject.getString("job_id");
		p.setIdStr(ids);
		p.setName(jObject.getString("job_name"));
		p.setCompanyId(jObject.getString("company_id"));
		p.setCompanyName(jObject.getString("company_name"));
		p.setUpdateTime(jObject.getString("update_time"));
		p.setIs_urgent(jObject.getInt("is_urgent"));
		p.setRecruitingNumbers(jObject.getString("recruit_num"));
		p.setDescription(jObject.getString("decription"));
		p.setSalary(jObject.getString("salary"));
		p.setAddress(jObject.getString("work_place"));
		p.setCondition(jObject.getString("conditions"));
		p.setFoodStatus(jObject.getString("room_board"));
		p.setCompanyaddress(jObject.getString("address"));
		p.setLongitude(jObject.getString("longitude"));
		p.setLatitude(jObject.getString("latitude"));
		p.setContacts(jObject.getString("contact_name"));
		p.setTelephone(jObject.getString("contact_tel"));
		p.setPhone(jObject.getString("contact_phone"));
		p.setEmail(jObject.getString("contact_email"));
		p.setWebsite(jObject.getString("website"));
		if(jObject.has("is_favorited"))
			p.setIs_favorited(jObject.getInt("is_favorited"));
		if(jObject.has("is_applied"))
			p.setIs_applied(jObject.getInt("is_applied"));
		return p;
	}
	
	
	/**
	 * 获取企业详情
	 *<pre>方法  </pre>
	 * @param apikey
	 * @param company_id	企业id
	 * @param user_ticket	用户票据(用于获取职位的收藏状态)
	 * @return
	 */
	public Company getCompany(String company_id) {
		Company company=null;
		if (StringUtils.isEmpty(company_id)) {
			return null;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("company_id", company_id));
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String result=HttpTools.getNetString_post(nv, NET_HOST+"job/company_detail");
		if (!StringUtils.isEmpty(result)) {
			try {
				JSONObject jObject=new JSONObject(result);
				if (jObject.has("status")&&jObject.optInt("status")==1) {
					JSONObject data=jObject.getJSONObject("data");
					company=parseCompany(data);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
		return company;
	}
	
	
	/**
	 * 解析企业详细数据
	 * @param jObject
	 * @return 企业详情的实体对象
	 * @throws JSONException 
	 */
	private Company parseCompany(JSONObject jObject) throws JSONException{
		if (StringUtils.isEmpty(jObject)) {
			return null;
		}
		Company company=new Company();
		String ids=jObject.getString("company_id");
		company.setId(ids);
		company.setName(jObject.getString("company_name"));
		company.setTrade(jObject.getString("industry"));
		company.setStarLevel(jObject.getString("star_level"));
		company.setProperty(jObject.getString("company_nature"));
		company.setScale(jObject.getString("company_size"));
		company.setAddress(jObject.getString("address"));
		company.setIntroduce(jObject.getString("description"));
		company.setContacts(jObject.getString("contact_name"));
		company.setTelephone(jObject.getString("contact_tel"));
		company.setPhone(jObject.getString("contact_phone"));
		company.setEmail(jObject.getString("contact_email"));
		company.setLongtitude(jObject.getString("longitude"));
		company.setLatitude(jObject.getString("latitude"));
		company.setWebsite(jObject.getString("website"));
		company.setGrade(jObject.getString("employ_index"));
		if(jObject.has("is_followed"))
			company.setIsFollowed(jObject.getInt("is_followed"));
		return company;
	}
	
	/**
	 * 申请职位
	 * @param uri 请求地址
	 * @param jobIds 职位id。 多个id中间用","隔开.
	 * @param user_ticket 用户票据。
	 * @param client_type 设备类型，1：Android 2：iOS
	 * @return
	 */
	public EorrerBean applyJobs(String jobIds){
		EorrerBean eb=null;
		if (StringUtils.isEmpty(jobIds)||StringUtils.isEmpty(mApplication.user_ticket)) {
			return eb;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("job_id", jobIds));
		nv.add(new BasicNameValuePair("client_id", "1"));
		String result=HttpTools.getNetString_post(nv, NET_HOST+"user/apply");
		if (!StringUtils.isEmpty(result)) {
			try {
				JSONObject jObject=new JSONObject(result);
				if (jObject.has("status")) {
					eb=new EorrerBean();
					eb.status=jObject.optInt("status");
					if(jObject.has("errCode"))
						eb.errCode=jObject.optInt("errCode");
					if(jObject.has("errMsg"))
						eb.errMsg=jObject.optString("errMsg");
					return eb;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return eb;
			}
		}
		return eb;
	}
	
	/**
	 * 收藏职位
	 * @param uri 请求地址
	 * @param jobIds 职位id。 多个id中间用","隔开.
	 * @param user_ticket 用户票据。
	 * @return
	 */
	public EorrerBean favoriteJobs(String jobIds){
		EorrerBean eb=null;
		if (StringUtils.isEmpty(jobIds)||StringUtils.isEmpty(mApplication.user_ticket)) {
			return eb;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("job_id", jobIds));
		String result=HttpTools.getNetString_post(nv, NET_HOST+"user/add_favorite_job");
		if (!StringUtils.isEmpty(result)) {
			try {
				JSONObject jObject=new JSONObject(result);
				if (jObject.has("status")) {
					eb=new EorrerBean();
					eb.status=jObject.optInt("status");
					if (jObject.has("errCode"))
						eb.errCode=jObject.optInt("errCode");
					if (jObject.has("errMsg"))
						eb.errMsg=jObject.optString("errMsg");
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return eb;
			}
		}
		return eb;
	}
	
	/**
	 * 职位举报
	 * @param job_id 职位id
	 * @param feed_back_type  举报类型(1.虚假招聘；2.冒用公司；3.分类错误；4.职介；5.其他),多选用,分隔，例：1,2,4,5。
	 * @param description 情况说明(最多输入300字)
	 * @param p_mobile 联系人手机号
	 * @param p_email 联系人邮箱
	 * @return
	 */
	public EorrerBean report(String job_id,String feed_back_type,String description,String p_mobile,String p_email) {
		EorrerBean eb=null;
		if (StringUtils.isEmpty(job_id)) {
			return eb;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("job_id", job_id));
		if (!StringUtils.isEmpty(feed_back_type)) {
			nv.add(new BasicNameValuePair("feed_back_type", feed_back_type));
		}
		if (!StringUtils.isEmpty(description)) {
			nv.add(new BasicNameValuePair("description", description));
		}
		if (!StringUtils.isEmpty(p_mobile)) {
			nv.add(new BasicNameValuePair("p_mobile", p_mobile));
		}
		if (!StringUtils.isEmpty(p_email)) {
			nv.add(new BasicNameValuePair("p_email", p_email));
		}
		String result=HttpTools.getNetString_post(nv, NET_HOST+"job/report");
		if (!StringUtils.isEmpty(result)) {
			try {
				JSONObject jObject=new JSONObject(result);
				if (jObject.has("status")) {
					eb=new EorrerBean();
					eb.status=jObject.getInt("status");
					if (jObject.has("errCode"))
						eb.errCode=jObject.getInt("errCode");
					if (jObject.has("errMsg"))
						eb.errMsg=jObject.getString("errMsg");
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return eb;
			}
		}
		return eb;
	}
	
	/**
	 * 关注企业
	 * 
	 * @param company_id  ,可以是多个“,”号，隔开的
	 *            企业ID
	 * @return
	 */
	public EorrerBean follow_company(String company_id) {
		EorrerBean eb=null;
		if (StringUtils.isEmpty(company_id)||StringUtils.isEmpty(mApplication.user_ticket)) {
			return eb;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("company_id", company_id));
		String sreResult = HttpTools.getHttpRequestString(nv, NET_HOST + "user/follow_company");
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					eb=new EorrerBean();
					eb.status=jObject.getInt("status");
					if(jObject.has("errCode"))
						eb.errCode=jObject.getInt("errCode");
					if(jObject.has("errMsg"))
						eb.errMsg=jObject.getString("errMsg");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return eb;
			}
		}
		return eb;
	}
	
	/**
	 * 取消关注企业
	 * 
	 * @param company_id
	 *            (已验证) 企业ID 可以批量取消。。","隔开
	 * @return
	 */
	public EorrerBean unfollow_company(String company_id) {
		EorrerBean eb=null;
		if (StringUtils.isEmpty(company_id)||StringUtils.isEmpty(mApplication.user_ticket)) {
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
					eb=new EorrerBean();
					eb.status=jObject.getInt("status");
					if(jObject.has("errCode"))
						eb.errCode=jObject.getInt("errCode");
					if(jObject.has("errMsg"))
						eb.errMsg=jObject.getString("errMsg");
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
	 * 删除收藏职位
	 * 
	 * @param job_id
	 *            职位ID  职位id，批量取消用,分割。
	 * @return
	 */
	public EorrerBean delete_favorite_job(String job_id) {
		EorrerBean eb=null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		String request = NET_HOST + "user/delete_favorite_job";
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("job_id", job_id));
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					eb=new EorrerBean();
					eb.status=jObject.getInt("status");
					if(jObject.has("errCode"))
						eb.errCode=jObject.getInt("errCode");
					if(jObject.has("errMsg"))
						eb.errMsg=jObject.getString("errMsg");
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
