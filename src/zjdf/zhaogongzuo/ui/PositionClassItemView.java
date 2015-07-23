/**
 * Copyright © 2014年4月15日 FindJob www.veryeast.com
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
import zjdf.zhaogongzuo.activity.search.PositionClassActivity;
import zjdf.zhaogongzuo.entity.PositionClassify;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 *<h2> 职位分类每项View</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月15日
 * @version 
 * @modify 
 * 
 */
public class PositionClassItemView extends RelativeLayout {

	private Context context;
	/**背景*/
	private RelativeLayout rela_item;
	/**职位类别标题和选择*/
	private CheckBox cbx_title;
	/**下一级图标*/
	private ImageView iv_next;
	/**职位类别对象*/
	private PositionClassify positionClassify;
	
	public PositionClassItemView(Context context) {
		this(context, null);
	}
	/**<pre></pre>
	 * @param context
	 * @param attrs
	 */
	public PositionClassItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		LayoutInflater.from(context).inflate(R.layout.layout_search_address_item,
				this, true);
		rela_item=(RelativeLayout)findViewById(R.id.rela_item);
		cbx_title=(CheckBox)findViewById(R.id.cbx_title);
		iv_next=(ImageView)findViewById(R.id.iv_next);
		cbx_title.setFocusable(false);
		cbx_title.setClickable(false);
	}
	
	/**
	 * 
	 *<pre>填充数据  </pre>
	 * @param positionClassify
	 */
	public void setPositionClassify(PositionClassify positionClassify) {
		this.positionClassify=positionClassify;
		initDatas();
	}
	
	/**
	 * <pre>填充数据  </pre>
	 * @param positionClassify 职位分类
	 * @param state 选中状态
	 */
	public void setPositionClassify(PositionClassify positionClassify,boolean state) {
		this.positionClassify=positionClassify;
		initDatas(state);
	}
	
	/**
	 * 初始化数据
	 *<pre>方法  </pre>
	 */
	private void initDatas(){
		if (positionClassify!=null) {
			cbx_title.setText(positionClassify.getValue());
			if (positionClassify.isHasSub()) {
				iv_next.setVisibility(View.VISIBLE);
			}else {
				iv_next.setVisibility(View.GONE);
			}
			if (PositionClassActivity.code.equals(positionClassify.getCode())) {
				cbx_title.setChecked(true);
			}else{
				cbx_title.setChecked(false);
			}
		}
	}
	
	/**
	 * 初始化数据
	 *<pre>方法  </pre>
	 */
	private void initDatas(boolean state){
		if (positionClassify!=null) {
			cbx_title.setText(positionClassify.getValue());
			if (positionClassify.isHasSub()) {
				iv_next.setVisibility(View.VISIBLE);
			}else {
				iv_next.setVisibility(View.GONE);
			}
			cbx_title.setChecked(state);
		}
	}
	
	/**
	 * 获取到职位分类对象
	 *<pre>方法  </pre>
	 * @return
	 */
	public PositionClassify getPositionClassify(){
		return positionClassify;
	}
	
	/**
	 * 获取到职位分类编码
	 *<pre>方法  </pre>
	 * @return
	 */
	public String getCode(){
		return positionClassify.getCode();
	}
	
	/**
	 * 获取到职位分类名称
	 *<pre>方法  </pre>
	 * @return
	 */
	public String getValue(){
		return positionClassify.getValue();
	}
	
	/**
	 * 设置监听
	 *<pre>方法  </pre>
	 * @param listener
	 */
	public void setOnItemClickListener(View.OnClickListener listener){
		if (rela_item!=null) {
			rela_item.setOnClickListener(listener);
		}
	}
	
	/**
	 * 设置选中状态
	 *<pre>方法  </pre>
	 * @param status
	 */
	public void setCheckedStatus(boolean status){
		if (cbx_title!=null)
			cbx_title.setChecked(status);
	}
	
	/**
	 * 获取选中状态
	 *<pre>方法  </pre>
	 * 
	 */
	public boolean getCheckedStatus(){
		return cbx_title.isChecked();
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
