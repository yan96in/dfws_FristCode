/**
 * Copyright © 2014-2-28 XZBOX_TV www.veryeast.com
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
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**<h2> 自定义消息提示类<h2>
 * <pre> </pre>
 * @author 东方网升Eilin.Yang
 * @since 2014-2-28
 * @version 
 * @modify ""
 */
public class CustomMessage {

	private static Toast toast=null;
	/**
	 * <pre>自定义toast显示消息</pre>
	 * @param context
	 * @param views 自定义view，可以为null
	 * @param txt 显示内容
	 * @param gravity 显示位置
	 * @param duration 持续时间.1:长显示；0:短显示
	 * @return
	 */
	public static void showToast(Context context,View view,String txt,int gravity,int duration){
		if (toast==null) {
			toast =Toast.makeText(context, txt, duration);
		}
		if (view!=null) {
			toast.setView(view);
		}
		toast.setText(txt);
		toast.setGravity(gravity, 0, 0);
		toast.setDuration(duration);
		toast.show();
	}
	
	/**
	 * <pre>自定义toast显示消息</pre>
	 * @param context
	 * @param txt 显示内容
	 * @param gravity 显示位置
	 * @param duration 持续时间.1:长显示；0:短显示
	 * @return
	 */
	public static void showToast(Context context,String txt,int gravity,int duration){
		if (toast==null) {
			toast =Toast.makeText(context, txt, duration);
		}
		toast.setText(txt);
		toast.setGravity(gravity, 0, 0);
		toast.setDuration(duration);
		toast.show();
	}
	
	/**
	 * <pre>自定义toast显示消息,
	 * 显示在父容器的center位置</pre>
	 * @param context
	 * @param txt 显示内容
	 * @param duration 持续时间,1:长显示；0:短显示
	 * @return
	 */
	public static void showToast(Context context,String txt,int duration){
		if (toast==null) {
			toast =Toast.makeText(context, txt, duration);
		}
		toast.setText(txt);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(duration);
		toast.show();
	}
	
	/**
	 * 创建加载数据的对话框
	 *<pre>方法  </pre>
	 * @param context
	 * @param txt 提示信息
	 * @param showTxt 是否显示提示信息
	 * @return
	 */
	public static Dialog createProgressDialog(Context context,String txt,boolean showTxt) {
		
		Dialog dialog=new Dialog(context, R.style.custom_dialog);
		dialog.setContentView(R.layout.progress_dialog);
		dialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		((TextView) dialog.findViewById(R.id.txt)).setText(txt==null? "":txt);
		((TextView) dialog.findViewById(R.id.txt)).setVisibility(showTxt?View.VISIBLE:View.GONE);
		return dialog;
	}
	
	/**
	 * 创建加载数据的对话框
	 *<pre>方法  </pre>
	 * @param context
	 * @param txt 提示信息
	 * @param showTxt 是否显示提示信息
	 * @param textSize 字体大小
	 * @return
	 */
	public static Dialog createProgressDialog(Context context,String txt,boolean showTxt,float textSize) {
		
		Dialog dialog=new Dialog(context, R.style.custom_dialog);
		dialog.setContentView(R.layout.progress_dialog);
		dialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		((TextView) dialog.findViewById(R.id.txt)).setText(txt==null? "":txt);
		((TextView) dialog.findViewById(R.id.txt)).setTextSize(textSize);
		((TextView) dialog.findViewById(R.id.txt)).setVisibility(showTxt?View.VISIBLE:View.GONE);
		return dialog;
	}
}
