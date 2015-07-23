/**
 * Copyright © 2014年5月14日 FindJob www.veryeast.com
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

import java.util.List;

import zjdf.zhaogongzuo.R;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import zjdf.zhaogongzuo.activity.options.OptSalaryActivity;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.configures.enums.SalaryEnum;
import zjdf.zhaogongzuo.entity.OptionKeyValue;
import zjdf.zhaogongzuo.utils.StringUtils;

/**
 * 选择薪资
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月14日
 * @version v1.0.0
 * @modify 
 */
public final class SalarySelectDialog {

	private Context context;
	/**
	 * 自定义对话框
	 */
	private Dialog dialog;
	
	private TextView txt_title;
	/**
	 * 消息容器
	 */
	private LinearLayout linear_dialog_container;
	
	private OptSalaryActivity.IflashViewsCallback iflashViewsCallback;
	
	public SalarySelectDialog(Context context) {
		this.context = context;
		init();
	}

	private void init() {
		dialog = new Dialog(context, R.style.custom_dialog);
		dialog.setContentView(R.layout.layout_dalog_position_fiter);
		Window window = dialog.getWindow();
		window.setWindowAnimations(R.style.dialog_in_and_out);
		window.setGravity(Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		// lp.width = Configure.screenWidth; // 设置宽度
		dialog.getWindow().setAttributes(lp);
		linear_dialog_container = (LinearLayout) dialog
				.findViewById(R.id.linear_dialog_position_fiter_container);
		txt_title=(TextView)dialog.findViewById(R.id.txt_title);
	}

	private void initView(final SalaryEnum opt) {
		int n = 0;
		linear_dialog_container.removeAllViews();
		switch (opt) {
		case SALARY_MODE:
			txt_title.setText("选择薪资类型");
			n=FrameConfigures.list_salary_mode.size();
			for (OptionKeyValue entry : FrameConfigures.list_salary_mode) {
				OptionsItemView itemView = new OptionsItemView(context);
				final String key = entry.key;
				final Integer value = entry.value;
				itemView.setKeyValue(key, value);
				if (key.equals(OptSalaryActivity.mode_value)) {
					itemView.setChecked(true);
				}else{
					itemView.setChecked(false);
				}
				itemView.setItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						iflashViewsCallback.doFlash(opt, key, value);
						dismiss();
					}
				});
				linear_dialog_container.addView(itemView);
			}
			break;

		case SALARY_CURRENCY:
			txt_title.setText("选择货币类型");
			n=FrameConfigures.list_salary_currency.size();
			for (OptionKeyValue entry : FrameConfigures.list_salary_currency) {
				OptionsItemView itemView = new OptionsItemView(context);
				final String key = entry.key;
				final Integer value = entry.value;
				itemView.setKeyValue(key, value);
				if (key.equals(OptSalaryActivity.currency_value)) {
					itemView.setChecked(true);
				}else{
					itemView.setChecked(false);
				}
				itemView.setItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						iflashViewsCallback.doFlash(opt, key, value);
						dismiss();
					}
				});
				linear_dialog_container.addView(itemView);
			}
			
			break;
		case SALARY_SCOPE:
			txt_title.setText("选择薪资范围");
			List<OptionKeyValue> list=null;
			if(OptSalaryActivity.mode_code.equals("1")){
				list=FrameConfigures.list_salary_month;
			}else {
				list=FrameConfigures.list_salary_year;
			}
			if(list==null)
				break;
			n=list.size();
			for (OptionKeyValue entry : list) {
				OptionsItemView itemView = new OptionsItemView(context);
				final String key = entry.key;
				final Integer value = entry.value;
				itemView.setKeyValue(key, value);
				if (key.equals(OptSalaryActivity.scope_value)) {
					itemView.setChecked(true);
				}else{
					itemView.setChecked(false);
				}
				itemView.setItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						iflashViewsCallback.doFlash(opt,key, value);
						dismiss();
					}
				});
				linear_dialog_container.addView(itemView);
			}
			break;
		}
	}
	
	/**
	 * 设置数据修改监听
	 *<pre>方法  </pre>
	 * @param callback
	 */
	public void setOnDataFlashListener(OptSalaryActivity.IflashViewsCallback callback){
		iflashViewsCallback=callback;
	}

	public void show(SalaryEnum opt) {
		if (dialog == null) {
			return;
		}
		initView(opt);
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	public void dismiss() {
		if (dialog == null) {
			return;
		}
		if (dialog.isShowing()) {
			dialog.dismiss();
			;
		}
	}
}
