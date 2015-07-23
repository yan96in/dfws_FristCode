/**
 * Copyright © 2014-3-15 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import zjdf.zhaogongzuo.MainActivity;
import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.mycenter.AttentionPositionActivity;
import zjdf.zhaogongzuo.activity.mycenter.JobFairActivity;
import zjdf.zhaogongzuo.activity.mycenter.ModifyPasswordActivity;
import zjdf.zhaogongzuo.activity.mycenter.MyResumeActivity;
import zjdf.zhaogongzuo.activity.mycenter.PositionApplyLogActivity;
import zjdf.zhaogongzuo.activity.mycenter.PositionFavoriteActivity;
import zjdf.zhaogongzuo.activity.personal.LoginActivity;
import zjdf.zhaogongzuo.activity.personal.MicroresumeActivity;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.configures.enums.ResumeEnum;
import zjdf.zhaogongzuo.controllers.HttpPostFile;
import zjdf.zhaogongzuo.controllers.MyResumeConttroller;
import zjdf.zhaogongzuo.controllers.PersonalController;
import zjdf.zhaogongzuo.databases.handlers.UserDatabaseHelper;
import zjdf.zhaogongzuo.databases.sharedpreferences.UserKeeper;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.entity.State;
import zjdf.zhaogongzuo.ui.CameraDialog;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.ui.CustomSeekBar;
import zjdf.zhaogongzuo.utils.BitmapTools;
import zjdf.zhaogongzuo.utils.DeviceUtils;
import zjdf.zhaogongzuo.utils.FileAccess;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

/**
 * <h2> <h2>
 * 
 * <pre> </pre>
 * 
 * @author 东方网升Eilin.Yang
 * @since 2014-3-15
 * @version
 * @modify ""
 */
public class MycenterFragment extends BaseFragment {

	private Context context;
	/** 注销 */
	private Button btn_logout;
	/** 用户头像 */
	private ImageView iv_protrait;
	/** 用户名称 */
	private TextView txt_username;
	/** 简历完善进度 */
	private CustomSeekBar sb_resume_progress;
	/** 我的简历 */
	private Button btn_myresume;
	/** 刷新简历 */
	private Button btn_refresh_resume;
	/** 我的简历模块 */
	private LinearLayout linear_myresume;
	/** 创建简历的模块 */
	private LinearLayout linear_create_resume;
	/** 创建微简历 */
	private Button btn_create_resume;
	/** 职位申请记录 */
	private RelativeLayout rela_apply_log;
	/** 职位申请记录提醒 */
	private TextView txt_apply_notice;
	/** 职位收藏夹 */
	private RelativeLayout rela_favorite;
	/** 职位收藏夹提醒 */
	private TextView txt_favorite_notice;
	/** 关注的企业 */
	private RelativeLayout rela_attention;
	/** 关注的企业提醒 */
	private TextView txt_attention_notice;
	/** 招聘会 */
	private RelativeLayout rela_jobfair;
	/** 招聘会提醒 */
	private ImageView iv_jobfair_notice;
	/** 修改密码 */
	private RelativeLayout rela_modify_password;
	
	private PersonalController personalControllers;// 个人中心控制器
	private int state;// 返回状态
	private boolean status;// 登录返回状态
//	private SharedPreferences sp = null;// 保存账号密码
	private String username;// 用户
	private String userpass;// 密码
	private CameraDialog cameraDialog;// 自定义拍照相册对话框
	private String bturl="";
	private static Bitmap mBitmap;// 个人图像
	private boolean stuts;// 图片上传返回状态
	
	private MyResumeConttroller myResumeConttroller;// 控制器
	private int resume_status = 0;// 简历公开状态
	private int flag;// 返回码
	
	private static final String TAG = "MainActivity";
	private static final int TAKE_PICTURE = 11;
	private static final int CROP_PICTURE = 13;
	private static final int CHOOSE_PICTURE = 15;
	private static final String IMAGE_FILE_LOCATION = "file:///sdcard/veryeast/pra/usericon.png";
	private Uri imageUri;//to store the big bitmap

	/**头像款*/
	private int w=0;
	/**头像高*/
	private int h=0;
	/**简历操作状态*/
	private ResumeEnum resumeFunction;
	private boolean isPhoto=false;
	private HttpPostFile postFile;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		personalControllers = new PersonalController(context);
		cameraDialog = new CameraDialog(context);
		myResumeConttroller = new MyResumeConttroller(context);
		postFile=new HttpPostFile(context);
		w=DeviceUtils.dip2px(context, 90);
		h=DeviceUtils.dip2px(context, 120);
		imageUri = Uri.parse(IMAGE_FILE_LOCATION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {Log.d("ppp", "onCreateView");
		View mycenter = inflater.inflate(R.layout.layout_fragment_mycenter,
				container, false);
		initView(mycenter);
		setListener();
		setupView(MainActivity.state);
		return mycenter;
	}

	// 刷新界面
	@SuppressLint("ShowToast")
	private Handler handler = new Handler() {
		@SuppressLint("ShowToast")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (logoutEorrerBean!=null) {
					if (logoutEorrerBean.status==1) {
						CustomMessage.showToast(context, "注销成功！", 0);
						mApplication.user_ticket=null;
						mApplication.user.setId("");
						mApplication.user.setName("");
						mApplication.user.setPassword("");
						UserKeeper.clearUser(context);
						startActivity(new Intent(context, LoginActivity.class));
						MainActivity.mainActivity.setTabSelection(0);
					}else {
						CustomMessage.showToast(context, "注销失败！"+logoutEorrerBean.errMsg, 0);
					}
				} else {
					CustomMessage.showToast(context, "注销失败！", 0);
				}
				break;
			case 2:Log.d("ppp", "handleMessage(Message msg)-------------what="+msg.what);
				iv_protrait.setImageBitmap(mBitmap);
				break;
			case 3:Log.d("ppp", "handleMessage(Message msg)-------------what="+msg.what);
				// 获取 pathName
				iv_protrait.setImageBitmap(mBitmap);
				break;
			case 4:
				if (flag == 1) {
					Toast.makeText(context, "刷新成功！", Toast.LENGTH_LONG).show();
				} else{
					Toast.makeText(context, "三分钟内只准刷新一次！！", Toast.LENGTH_LONG).show();
				}
				break;

			}
		};
	};
	// 刷新简历
	private void setRefresh() {
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 1);
			return;
		}
		mExecutorService.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				flag = myResumeConttroller.refresh(resume_status + "");
				handler.sendEmptyMessage(4);
			}
		});
	}

	//错误对象
	private EorrerBean logoutEorrerBean;
	// 注销
	private void doLogout() {
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 1);
			return;
		}
		mExecutorService.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				logoutEorrerBean = personalControllers.logout();
				handler.sendEmptyMessage(1);
			}
		});
	}

	// 获取网络图片
	public void getBitamp(final String url) {
		Log.d("ppp", "getBitamp(final State state)");
		if (StringUtils.isEmpty(url)) {
			iv_protrait.setImageResource(R.drawable.ic_touxiang_90_120);
			return;
		}
		// 获取网络图片资源
		mExecutorService.submit(new Runnable() {
			@Override
			public void run() {
				mBitmap = BitmapTools.loadImageAndStore(FrameConfigures.FOLDER_PRA, url, "usericon.png");
				handler.sendEmptyMessage(2);
			}
		});

	}

	// 初始化
	private void initView(View view) {
		btn_logout = (Button) view.findViewById(R.id.btn_logout);
		iv_protrait = (ImageView) view.findViewById(R.id.iv_protrait);
		txt_username = (TextView) view.findViewById(R.id.txt_username);
		sb_resume_progress = (CustomSeekBar) view
				.findViewById(R.id.sb_resume_progress);
		btn_myresume = (Button) view.findViewById(R.id.btn_myresume);
		btn_refresh_resume = (Button) view
				.findViewById(R.id.btn_refresh_resume);
		linear_myresume = (LinearLayout) view
				.findViewById(R.id.linear_myresume);
		linear_create_resume = (LinearLayout) view
				.findViewById(R.id.linear_create_resume);
		btn_create_resume = (Button) view.findViewById(R.id.btn_create_resume);
		rela_apply_log = (RelativeLayout) view
				.findViewById(R.id.rela_apply_log);
		txt_apply_notice = (TextView) view.findViewById(R.id.txt_apply_notice);
		rela_favorite = (RelativeLayout) view.findViewById(R.id.rela_favorite);
		txt_favorite_notice = (TextView) view
				.findViewById(R.id.txt_favorite_notice);
		rela_attention = (RelativeLayout) view
				.findViewById(R.id.rela_attention);
		txt_attention_notice = (TextView) view
				.findViewById(R.id.txt_attention_notice);
		rela_jobfair = (RelativeLayout) view.findViewById(R.id.rela_jobfair);
		iv_jobfair_notice = (ImageView) view
				.findViewById(R.id.iv_jobfair_notice);
		rela_modify_password = (RelativeLayout) view
				.findViewById(R.id.rela_modify_password);

	}

	// 添加监听
	private void setListener() {
		btn_logout.setOnClickListener(listener);
		btn_myresume.setOnClickListener(listener);
		btn_refresh_resume.setOnClickListener(listener);
		btn_create_resume.setOnClickListener(listener);
		rela_apply_log.setOnClickListener(listener);
		rela_favorite.setOnClickListener(listener);
		rela_attention.setOnClickListener(listener);
		rela_jobfair.setOnClickListener(listener);
		rela_modify_password.setOnClickListener(listener);
		iv_protrait.setOnClickListener(listener);

	}

	// 监听器
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 注销
			case R.id.btn_logout:
				doLogout();
				break;
			// 拍照或者选取相册
			case R.id.iv_protrait:
				showPicturePicker(context);
				break;

			case R.id.btn_myresume:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 1);
					return;
				}
				startActivity(new Intent(context, MyResumeActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 刷新简历
			case R.id.btn_refresh_resume:
				MobclickAgent.onEvent(context, "mycenter_refresh_resume");
				setRefresh();
				break;
			case R.id.btn_create_resume:
				MobclickAgent.onEvent(context, "mycenter_create_resume");
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 1);
					return;
				}
				startActivity(new Intent(context,
						MicroresumeActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			case R.id.rela_apply_log:
				MobclickAgent.onEvent(context, "mycenter_position_apply_log");
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 1);
					return;
				}
				startActivity(new Intent(context,
						PositionApplyLogActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 职位收藏
			case R.id.rela_favorite:
				MobclickAgent.onEvent(context, "mycenter_position_favorites");
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 1);
					return;
				}
				startActivity(new Intent(context,
						PositionFavoriteActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 关注企业
			case R.id.rela_attention:
				MobclickAgent.onEvent(context, "mycenter_followed_enterprise");
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 1);
					return;
				}
				startActivity(new Intent(context,
						AttentionPositionActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 招聘会列表
			case R.id.rela_jobfair:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 1);
					return;
				}
				startActivity(new Intent(context, JobFairActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);				
				break;
			// 修改密码
			case R.id.rela_modify_password:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 1);
					return;
				}
				startActivity(new Intent(context, ModifyPasswordActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;

			}
		}
	};
	
	/**
	 * 初始化控件状态
	 * @param state 状态
	 * @param resumefunction 简历操作状态
	 */
	public void setupView(State state) {
		if (mApplication.user!=null&&mApplication.user.getName() != null) 
			txt_username.setText(mApplication.user.getName());
		if (state != null) {
			if (mApplication.user.getName() != null) {
//				txt_username.setText(mApplication.user.getName());
				if (state.getIs_need_fill_micro_resume()==1) {
					resumeFunction=ResumeEnum.CREATE;
					initViewsDatas(null);
				}else {
					resumeFunction=ResumeEnum.READ;
					initViewsDatas(state);
				}
			}
		}
	}
	
	/**
	 * 初始化数据状态
	 * @param state
	 */
	private void initViewsDatas(State state){
		switchResumeLayout();
		if (state==null) {
			sb_resume_progress.setProgress(0);
			txt_apply_notice.setText("0个申请");
			txt_favorite_notice.setText("0个收藏");
			txt_attention_notice.setText("0个关注");
			resume_status = 0;
		}else {
			int number = (int) ((state.getResume_complete()) * 100);
			if (number == 0) {
				sb_resume_progress.setProgress(0);
			}
			sb_resume_progress.setProgress(number);
			txt_apply_notice.setText(state.getApplied_num()
					+ "个申请");
			txt_favorite_notice.setText(state
					.getFavorited_num() + "个收藏");
			txt_attention_notice.setText(state
					.getFollowed_num() + "个关注");
			resume_status = state.getResume_status();
			if (!isPhoto) {
				bturl=state.getAvatar();
				getBitamp(bturl);
			}
		}
	}

	/**
	 * 切换简历操作布局
	 */
	private void switchResumeLayout(){
		if (resumeFunction==ResumeEnum.CREATE) {
			linear_create_resume.setVisibility(View.VISIBLE);
			linear_myresume.setVisibility(View.GONE);
		}else {
			linear_create_resume.setVisibility(View.GONE);
			linear_myresume.setVisibility(View.VISIBLE);
		}
	}
		
	// 上传图像0
	private void setUserImage() {
		Log.d("ppp", "setUserImage()-----");
		final File file=new File(FrameConfigures.FOLDER_PRA+"usericon.png");
		if (file.exists()) {
			mExecutorService.submit(new Runnable() {
				
				@Override
				public void run() {
					boolean ff=postFile.postFile(file);
					Log.d("ppp", "postFile(file)-----state="+ff);
					handler.sendEmptyMessage(3);
				}
			});
		}
	}

	/**
	 * 获取 返回照片 处理照片
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);Log.d("ppp", "onActivityResult");
		if(resultCode != Activity.RESULT_OK){//result is not correct
			Log.e(TAG, "requestCode = " + requestCode);
			Log.e(TAG, "resultCode = " + resultCode);
			Log.e(TAG, "data = " + data);
			return;
		}else{
			switch (requestCode) {
			case TAKE_PICTURE:
				Log.d("ppp", "TAKE_PICTURE-----");//it seems to be null
				//TODO sent to crop
				cropImageUri(imageUri, w, h, CROP_PICTURE);
				
				break;
			case CROP_PICTURE://from crop_big_picture
				Log.d("ppp", "CROP_PICTURE-----");//it seems to be null
				if(imageUri != null){
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					iv_protrait.setImageBitmap(bitmap);
					setUserImage();
				}
				break;
			
			case CHOOSE_PICTURE:
				Log.d("ppp", "CHOOSE_PICTURE-----");//it seems to be null
				if(imageUri != null){
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					iv_protrait.setImageBitmap(bitmap);
					setUserImage();
				}
				break;
			default:
				break;
			}
		}
	}
	
	// 对话框 选择相片或者拍照
	public void showPicturePicker(final Context context) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("图片来源");
		builder.setNegativeButton("取消", null);
		builder.setItems(new String[] { "拍照", "相册" },
				new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = null;
						switch (which) {
						case 0:
							if(imageUri == null)
								Log.e(TAG, "image uri can't be null");
							//capture a big bitmap and store it in Uri
							intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
							intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
							startActivityForResult(intent, TAKE_PICTURE);
							break;

						case 1:
							intent = new Intent(Intent.ACTION_GET_CONTENT, null);
							intent.setType("image/*");
							intent.putExtra("crop", "true");
							intent.putExtra("aspectX", 3);
							intent.putExtra("aspectY", 4);
							intent.putExtra("outputX", w+10);
							intent.putExtra("outputY", h+10);
							intent.putExtra("scale", true);
							intent.putExtra("return-data", false);
							intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
							intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
							intent.putExtra("noFaceDetection", false); // no face detection
							startActivityForResult(intent, CHOOSE_PICTURE);
							break;
						}
					}
				});
		builder.create().show();
	}

	/**
	 * 剪切失败
	 * @param uri
	 * @param outputX
	 * @param outputY
	 * @param requestCode
	 */
	private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 3);
		intent.putExtra("aspectY", 4);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, requestCode);
	}
	
	/**
	 * 获取图片
	 * @param uri
	 * @return
	 * @throws IOException 
	 */
	private Bitmap decodeUriAsBitmap(Uri uri){
		Bitmap bitmap = null;isPhoto=true;
		FileOutputStream outputStream=null;
		Log.d("ppp", "decodeUriAsBitmap(Uri uri)-----");//it seems to be null
		try {
			bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
			outputStream=new FileOutputStream(FrameConfigures.FOLDER_PRA+"usericon.png");
			bitmap.compress(CompressFormat.PNG, 100, outputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				outputStream.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
            try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		mBitmap=bitmap;
		return bitmap;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();Log.d("ppp", "onResume()");
		 MobclickAgent.onPageStart("MycenterFragment"); 
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();Log.d("ppp", "onPause()");isPhoto=false;
		MobclickAgent.onPageEnd("MycenterFragment");
	}
	
	/* (non-Javadoc)
	 * @see zjdf.zhaogongzuo.fragments.BaseFragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		if (personalControllers!=null) {
//			personalControllers.CloseDB();
//		}
	}
}
