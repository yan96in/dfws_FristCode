package zjdf.zhaogongzuo.ui;

import zjdf.zhaogongzuo.R;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.TextView;

public class CameraDialog {

	/**
	 * 上下文
	 */
	private Context context;
	/**
	 * 社会化分享对话框
	 */
	private Dialog shareDialog;
	/**
	 * 显示状态，true：已显示，false：没有显示
	 */
	private boolean status=false;
	/**
	 * 是否隐藏了。true：已隐藏，false：显示
	 */
	private boolean ishide=false;
	private Button text_pictures;//拍照
	private Button text_album;//相册选取
	/**取消*/
	private TextView txt_share__cancel;
	
	public CameraDialog(Context context){
		this.context=context;
		initView();
	}
	
	public void initView(){
		shareDialog=new Dialog(context, R.style.custom_dialog);
		shareDialog.setContentView(R.layout.layout_mycenter_myresume_camera);
		Window window=shareDialog.getWindow();
		window.setWindowAnimations(R.style.dialog_in_and_out);
		window.setGravity(Gravity.BOTTOM);
		text_pictures=(Button)shareDialog.findViewById(R.id.text_pictures);
		text_album=(Button)shareDialog.findViewById(R.id.text_album);
		txt_share__cancel=(TextView) shareDialog.findViewById(R.id.txt_share__cancel);

		txt_share__cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				dismiss();
			}
		});
	}

	public void show() {
		if (shareDialog==null) {
			return;
		}
		status=shareDialog.isShowing();
		if (!status||ishide) {
			shareDialog.show();
			status=true;
			ishide=false;
			WindowManager.LayoutParams lp = shareDialog.getWindow().getAttributes();
					//lp.width = Configure.screenWidth; // 设置宽度
					shareDialog.getWindow().setAttributes(lp);
		}
	}

	public void hide() {
		if (status&&!ishide) {
			shareDialog.hide();
			ishide=true;
		}
	}

	public void dismiss() {
		if (status) {
			shareDialog.dismiss();
			status=false;
		}
	}

	public void cancel() {
		if (status) {
			shareDialog.cancel();
			status=false;
		}
	}
	
	/**
	 * <pre>设置监听</pre>
	 * @param shareListener
	 */
	public void setOnclickListener(View.OnClickListener shareListener) {
		txt_share__cancel.setOnClickListener(shareListener);
		text_pictures.setOnClickListener(shareListener );
		text_album.setOnClickListener(shareListener);

	}

}
