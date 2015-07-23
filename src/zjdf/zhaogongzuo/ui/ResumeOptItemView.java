/**
 * Copyright © 2014年5月12日 FindJob www.veryeast.com
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
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 简历里面的选项item
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月12日
 * @version v1.0.0
 * @modify 
 */
public class ResumeOptItemView extends LinearLayout {

	/**容器*/
	private RelativeLayout rela_container;
	/**选项多选框*/
	private CheckBox cbx_option;
	private String keyString;
	private String valueString;
	private TextView title;
	
	
	public ResumeOptItemView(Context context) {
		this(context, null);
	}
	
	/**<pre></pre>
	 * @param context
	 * @param attrs
	 */
	public ResumeOptItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.layout_opt_item,
				this, true);
		rela_container=(RelativeLayout)findViewById(R.id.rela_container);
		title=(TextView)findViewById(R.id.txt_opt_title);
		cbx_option=(CheckBox)findViewById(R.id.cbx);
		cbx_option.setClickable(false);
	}
	
	/**
	 * 设置键值对
	 *<pre>方法  </pre>
	 * @param key 键
	 * @param value 值
	 */
	public void setKeyValue(String key,String value) {
		keyString=key;
		valueString=value;
		setText(key);
	}
	
	/**
	 * 
	 *<pre>返回key  </pre>
	 * @return
	 */
	public String getKey() {
		return keyString;
	}
	
	
	/**
	 * 
	 *<pre>返回value  </pre>
	 * @return
	 */
	public String getValue() {
		return valueString;
	}
	
	
	/**
	 * 设置标题
	 *<pre>方法  </pre>
	 * @param txt
	 */
	public void setText(String txt) {
		if (null!=txt) {
			title.setText(txt);
		}
	}
	
	/**设置选中状态*/
	public void setChecked(boolean check) {
		cbx_option.setChecked(check);
	}
	
	/**
	 * 
	 *<pre>获取选中状态  </pre>
	 * @return
	 */
	public boolean getIsChecked() {
		return cbx_option.isChecked();
	}
	
	/**
	 * 设置监听
	 *<pre> 此监听代替了 checkbox的监听方法 </pre>
	 * @param listener 监听
	 */
	public void setItemClickListener(View.OnClickListener listener) {
		if (null!=listener) {
			rela_container.setOnClickListener(listener);
		}
	}
}
