/**
 * Copyright © 2014年5月15日 FindJob www.veryeast.com
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

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * 自定义PopupWindow对话框
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月15日
 * @version v1.0.0
 * @modify 
 */
public final class CustomPoPuDialog {

	/**
	 * 创建自定义PopupWindow
	 * @param context 
	 * @param mainView 主要布局文件
	 * @param bg_resid 背景
	 * @param animi_style 动画
	 * @return
	 */
	public static PopupWindow createPopupWindow(Context context,View mainView,int bg_resid,int animi_style){
			if (mainView==null) {
				return null;
			}
			final PopupWindow pop = new PopupWindow(mainView, ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			pop.setFocusable(true);
			pop.setTouchable(true);
			pop.setOutsideTouchable(true);
			pop.setBackgroundDrawable(context.getResources().getDrawable(
					bg_resid));
			pop.setAnimationStyle(animi_style);
			pop.setTouchInterceptor(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
						pop.dismiss();
					return false;
				}
			});
			mainView.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (event.getAction() == KeyEvent.ACTION_DOWN
							&& keyCode == KeyEvent.KEYCODE_MENU) {
						pop.dismiss();
					}
					return false;
				}
			});
			return pop;
	}
}
