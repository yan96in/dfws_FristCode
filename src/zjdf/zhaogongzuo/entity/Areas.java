package zjdf.zhaogongzuo.entity;

import java.util.List;

/**
 * 
 *<h2> 地址对象</h2>
 *<pre>  </pre>
 * @author Chen.Hao VeryEast
 * @since 2014年4月10日
 * @version 
 * @modify 
 *
 */
public class Areas {
	
	/**排序字段*/
	private String sortkey;
	/**地址编码*/
	private String code;
	/**父类id*/
	private String parentcode;
	/**地址名称*/
	private String value;// 城市名
	/**是否热门城市。0：不是；1:是*/
	private int ishot;
	/**是否有下一级地址.0:没有;1:有*/
	private int hassub;
	/**下一级地址.当hassub=0时不存在下一级,当hassub=1时有下一级地址*/
	private List<Areas> areas;

	
	/**
	 * @param 
	 */
	public Areas() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	/**
	 * @param sortkey 排序字段
	 * @param code 地址编码
	 * @param value 地址名称
	 * @param ishot 是否热门城市
	 * @param hassub 是否有下一级地址
	 * @param areas 下一级地址。当hassub=1时存在，
	 */
	public Areas(String sortkey,String code, String value, int ishot, int hassub,
			List<Areas> areas) {
		super();
		this.sortkey=sortkey;
		this.code = code;
		this.value = value;
		this.ishot = ishot;
		this.hassub = hassub;
		this.areas = areas;
	}

	

	/**
	 * @return the sortkey
	 */
	public String getSortkey() {
		return sortkey;
	}
	
	/**
	 * @param sortkey the sortkey to set
	 */
	public void setSortkey(String sortkey) {
		this.sortkey = sortkey;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getIshot() {
		return ishot;
	}

	public void setIshot(int ishot) {
		this.ishot = ishot;
	}

	public int getHassub() {
		return hassub;
	}

	public void setHassub(int hassub) {
		this.hassub = hassub;
	}
	
	
	/** 获取子类地址
	 * @return the areas
	 */
	public List<Areas> getAreas() {
		return areas;
	}

	/**
	 * 添加子类地址
	 * @param areas the areas to set
	 */
	public void setAreas(List<Areas> areas) {
		this.areas = areas;
	}

	/**父类编码
	 * @return the parentcode
	 */
	public String getParentcode() {
		return parentcode;
	}



	/**父类编码
	 * @param parentcode the parentcode to set
	 */
	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Areas [sortkey=" + sortkey + ", code=" + code + ", parentcode="
				+ parentcode + ", value=" + value + ", ishot=" + ishot
				+ ", hassub=" + hassub + ", areas=" + areas + "]";
	}
	
	
	
}
