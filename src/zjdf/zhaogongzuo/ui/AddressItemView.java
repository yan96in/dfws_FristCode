/**
 * Copyright © 2014年4月10日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.ui;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.search.AddressActivity;
import zjdf.zhaogongzuo.entity.Areas;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 *<h2> 地址item项</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月10日
 * @version 
 * @modify 
 * 
 */
public class AddressItemView extends RelativeLayout{

	private Context context;
	/**背景*/
	private RelativeLayout rela_address_item;
	/**地址选择*/
	private CheckBox cbx_address;
	/**下一级图标*/
	private ImageView iv_next;
	/**地址对象*/
	private Areas area;
	
	public AddressItemView(Context context) {
		this(context, null);
	}
	/**<pre></pre>
	 * @param context
	 * @param attrs
	 */
	public AddressItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		LayoutInflater.from(context).inflate(R.layout.layout_search_address_item,
				this, true);
		rela_address_item=(RelativeLayout)findViewById(R.id.rela_item);
		cbx_address=(CheckBox)findViewById(R.id.cbx_title);
		iv_next=(ImageView)findViewById(R.id.iv_next);
		cbx_address.setFocusable(false);
	}
	
	/**
	 * 
	 *<pre>填充数据  </pre>
	 * @param area
	 */
	public void setArea(Areas area) {
		this.area=area;
		initDatas();
	}
	
	/**
	 * 
	 *<pre>填充数据  </pre>
	 * @param area
	 */
	public void setArea(Areas area,boolean state) {
		this.area=area;
		initDatas(state);
	}
	
	/**
	 * 初始化数据
	 *<pre>方法  </pre>
	 */
	private void initDatas(boolean state){
		if (area!=null) {
			cbx_address.setText(area.getValue());
			cbx_address.setClickable(false);
			if (area.getHassub()==1) {
				iv_next.setVisibility(View.VISIBLE);
			}else {
				iv_next.setVisibility(View.GONE);
			}
			cbx_address.setChecked(state);
		}
	}
	
	/**
	 * 初始化数据
	 *<pre>方法  </pre>
	 */
	private void initDatas(){
		if (area!=null) {
			cbx_address.setText(area.getValue());
			cbx_address.setClickable(false);
			if (area.getHassub()==1) {
				iv_next.setVisibility(View.VISIBLE);
			}else {
				iv_next.setVisibility(View.GONE);
			}
			if (AddressActivity.code.equals(area.getCode())) {
				cbx_address.setChecked(true);
			}else{
				cbx_address.setChecked(false);
			}
		}
	}
	
	/**
	 * 获取到地址对象
	 *<pre>方法  </pre>
	 * @return
	 */
	public Areas getAreas(){
		return area;
	}
	
	/**
	 * 获取到地址编码
	 *<pre>方法  </pre>
	 * @return
	 */
	public String getCode(){
		return area.getCode();
	}
	
	/**
	 * 获取到地址名称
	 *<pre>方法  </pre>
	 * @return
	 */
	public String getValue(){
		return area.getValue();
	}
	
	/**
	 * 设置监听
	 *<pre>方法  </pre>
	 * @param listener
	 */
	public void setOnItemClickListener(View.OnClickListener listener){
		if (rela_address_item!=null) {
			rela_address_item.setOnClickListener(listener);
		}
	}
	
	/**
	 * 设置选中状态
	 *<pre>方法  </pre>
	 * @param status
	 */
	public void setCheckedStatus(boolean status){
		if (cbx_address!=null)
			cbx_address.setChecked(status);
	}
	
	/**
	 * 获取选中状态
	 *<pre>方法  </pre>
	 * 
	 */
	public boolean getCheckedStatus(){
		return cbx_address.isChecked();
	}
	
	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return true;
	}
}
