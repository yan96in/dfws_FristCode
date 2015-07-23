package zjdf.zhaogongzuo.entity;

/**
 * 技能和特长
 * 
 * @author Administrator
 * 
 */
public class Skills {
	private String id;// 技能ID
	private int user_id;// 用户ID
	private String skill_cn;// 技能名字
	private String skill_en;// 同上
	private int ability;// 技能熟练程度
	private String ability_txt;// 技能熟练程度txt
	private String detail_cn;// 详细类型
	private String detail_en;// 详细介绍
	private String file_id;// 附件ID
	private String modify_time;// 修改时间
	private String add_time;// 添加时间

	public String getAbility_txt() {
		return ability_txt;
	}

	public void setAbility_txt(String ability_txt) {
		this.ability_txt = ability_txt;
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

	public String getSkill_cn() {
		return skill_cn;
	}

	public void setSkill_cn(String skill_cn) {
		this.skill_cn = skill_cn;
	}

	public String getSkill_en() {
		return skill_en;
	}

	public void setSkill_en(String skill_en) {
		this.skill_en = skill_en;
	}

	public int getAbility() {
		return ability;
	}

	public void setAbility(int ability) {
		this.ability = ability;
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

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
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
