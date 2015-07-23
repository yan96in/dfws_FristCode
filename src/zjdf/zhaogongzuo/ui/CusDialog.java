package zjdf.zhaogongzuo.ui;

import zjdf.zhaogongzuo.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * <h2>自定义对话框</h2>
 * 定制的对话框，包含动画、样式自定义
 * @author Eilin.Yang
 * @version v1.0
 * @since 2013-9-10
 */
public class CusDialog {

	private Context context;
	private LayoutInflater inflater;
	/**
	 * 自定义对话框
	 */
	private Dialog dialog;
	private TextView btn_ok;//确定按钮
	private TextView btn_cancel;//取消按钮
	private TextView txt_content;//内容
	private TextView txt_dialog_update_title;//标题
	/**
	 * 消息容器
	 */
	private LinearLayout linear_container;
	/**
	 * 显示状态，true：已显示，false：没有显示
	 */
	private boolean status=false;
	/**
	 * 是否隐藏了。true：已隐藏，false：显示
	 */
	private boolean ishide=false;
	/**
	 * 是否初始化完成了消息内容控件
	 */
	private boolean isfinishmessageview=false;
	/**
	 * 是否初始化完"确定"按钮
	 */
	private boolean isfinishokbtn=false;

	public CusDialog(Context context){
		this.context=context;
		this.inflater=LayoutInflater.from(context);
		init();
	}
	private void init(){
		dialog=new Dialog(context, R.style.custom_dialog);
		dialog.setContentView(R.layout.layout_more_update);
		Window window=dialog.getWindow();
		window.setWindowAnimations(R.style.dialog_in_and_out);
		window.setGravity(Gravity.BOTTOM);
		btn_ok=(TextView)dialog.findViewById(R.id.txt_dialog_update_ok);
		btn_cancel=(TextView)dialog.findViewById(R.id.txt_dialog_update_cancel);
		txt_content=(TextView) dialog.findViewById(R.id.txt_dialog_update_content);
		txt_dialog_update_title=(TextView) dialog.findViewById(R.id.txt_dialog_update_title);
	}
	
	public void setOnclickListener(View.OnClickListener shareListener) {
		btn_cancel.setOnClickListener(shareListener);
		btn_ok.setOnClickListener(shareListener);

	}
	public void show() {
		if (dialog==null) {
			return;
		}
		status=dialog.isShowing();
		if (!status||ishide) {
			dialog.show();
			status=true;
			ishide=false;
			WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
					//lp.width = Configure.screenWidth; // 设置宽度
			dialog.getWindow().setAttributes(lp);
		}
	}

	public void hide() {
		if (status&&!ishide) {
			dialog.hide();
			ishide=true;
		}
	}

	public void dismiss() {
		if (status) {
			dialog.dismiss();
			status=false;
		}
	}

	public void cancel() {
		if (status) {
			dialog.cancel();
			status=false;
		}
	}

}
