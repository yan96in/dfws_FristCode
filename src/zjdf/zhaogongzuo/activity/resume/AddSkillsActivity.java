package zjdf.zhaogongzuo.activity.resume;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.controllers.MyResumeConttroller;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 添加技能和特长
 * 
 * @author Administrator
 * 
 */
public class AddSkillsActivity extends Activity {
	private ImageButton image_but;// 返回
	private Button txt_posit;// 保存
	private EditText sk_name;// 名字
	private TextView sk_skl;// 熟练程度
	private EditText sk_des;// 描述

	private Context context;// 上下文
	private MyResumeConttroller myResumeConttroller;// 控制器
	private int state;// 返回状态

	private RelativeLayout rela_cer;// 熟练程度
	int n;
	
	//获取  列表传递过来的
	private  String  id;//特长ID
	private String name;//用户名
	private String ability;//技能熟练程度
	private String detail;//描述
	
	private Drawable drawable = null;// 删除图片

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_resume_skills_add_item);
		context = this;
		myResumeConttroller = new MyResumeConttroller(context);
		
		id=getIntent().getStringExtra("ids");
		name=getIntent().getStringExtra("name");
		ability=getIntent().getStringExtra("ability");
		detail=getIntent().getStringExtra("detail");
		initView();
		assignment();
	}
	
	//如果是item跳过来的 就赋值
	private void assignment(){
		if (!StringUtils.isEmpty(id)) {
			sk_name.setText(name);
			sk_skl.setText(ability);
			sk_des.setText(detail);
		}
		
	}

	// 刷新UI
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			txt_posit.setEnabled(true);
			switch (msg.what) {
			case 1:
				if (state == 1) {
					Toast.makeText(context, "保存成功", Toast.LENGTH_LONG).show();
					finish();
				} else {
					Toast.makeText(context, "保存失败", Toast.LENGTH_LONG).show();
				}
				break;

			default:
				break;
			}
		};
	};

	// 请求服务器
	private void setData() {
		
		if (!NetWorkUtils.checkNetWork(context)) {
			Toast.makeText(context, "连接网络失败，请检查网络！", Toast.LENGTH_LONG).show();
			return;
		}
		final String st_name = sk_name.getText().toString();
		final String st_skl = sk_skl.getText().toString();
		final String st_des = sk_des.getText().toString();
		if (st_skl.contains("一般")) {
			n = 1;
		} else if (st_skl.contains("良好")) {
			n = 2;
		} else if (st_skl.contains("流利")) {
			n = 3;
		} else if (st_skl.contains("精通")) {
			n = 4;
		}		
		if ( st_name == null ||  st_name.trim().equals("")) {
			Toast.makeText(context, "技能名称不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		if ( st_skl == null ||  st_skl.trim().equals("")) {
			Toast.makeText(context, "熟练程度不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
/*		if ( st_des == null ||  st_des.trim().equals("")) {
			Toast.makeText(context, "描述不能为空！", Toast.LENGTH_LONG).show();
			return;
		}*/
		
		txt_posit.setEnabled(false);
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				state = myResumeConttroller.set_skill(st_name, n, st_des,id);
				handler.sendEmptyMessage(1);
			}
		}.start();

	}

	// 初始化
	private void initView() {
		// TODO Auto-generated method stub
		image_but = (ImageButton) findViewById(R.id.image_but);
		txt_posit = (Button) findViewById(R.id.txt_posit);
		sk_name = (EditText) findViewById(R.id.sk_name);
		sk_skl = (TextView) findViewById(R.id.sk_skl);
		sk_des = (EditText) findViewById(R.id.sk_des);
		rela_cer = (RelativeLayout) findViewById(R.id.rela_cer);
		drawable = getResources().getDrawable(R.drawable.ic_close_gary);
		
		image_but.setOnClickListener(clickListener);
		txt_posit.setOnClickListener(clickListener);
		rela_cer.setOnClickListener(clickListener);
		sk_name.setOnClickListener(clickListener);
		sk_des.setOnClickListener(clickListener);
		
		
		sk_name.addTextChangedListener(username_delect);
		sk_name.setOnTouchListener(username_ontouch);
		sk_des.addTextChangedListener(describe_delect);
		sk_des.setOnTouchListener(describe_ontouch);

	}

	// 监听器
	private View.OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			clickListener(v);
		}

		private void clickListener(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 返回
			case R.id.image_but:
//				Intent intent=new Intent(context,SkillsActivity.class);
//				startActivity(intent);
				finish();
				break;
			// 保存
			case R.id.txt_posit:
				setData();
				break;
			// 熟练程度
			case R.id.rela_cer:
				Intent intent_LanguageProficiency = new Intent(context,
						LanguageProficiencyActivity.class);
				startActivityForResult(intent_LanguageProficiency, 2);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			case R.id.sk_name:
				if (sk_name.length() > 1) {
					sk_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					return;
				} else {
					sk_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			}
		}
	};

	// 处理返回值
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 熟练程度
		case 2:
			if (data == null||"".equals(data)) {
				return;
			}else {			
				sk_skl.setText(data.getStringExtra("LanguageProficiency"));
			}
			break;

		}
	};
	
	// 监听账号输入框 显示 X图片
		private TextWatcher username_delect = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(s)) {
					sk_name.setCompoundDrawablesWithIntrinsicBounds(null, null,
							drawable, null);
					return;
				} else {
					sk_name.setCompoundDrawablesWithIntrinsicBounds(null, null,
							null, null);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		};

		// 触屏 删除账号
		private OnTouchListener username_ontouch = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					int curx = (int) event.getX();
					if (curx > v.getWidth() - 38
							&& !TextUtils.isEmpty(sk_name.getText())) {
						sk_name.setText("");
						sk_name.setCompoundDrawablesWithIntrinsicBounds(null,
								null, drawable, null);
						int cacheInputType = sk_name.getInputType();
						sk_name.setInputType(InputType.TYPE_NULL);
						sk_name.onTouchEvent(event);
						sk_name.setInputType(cacheInputType);
						sk_name.setCompoundDrawablesWithIntrinsicBounds(null,
								null, drawable, null);
						return true;
					}
					break;
				}
				return false;
			}
		};
		
		// 监听账号输入框 显示 X图片
			private TextWatcher describe_delect = new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before,
						int count) {
					// TODO Auto-generated method stub
					if (!TextUtils.isEmpty(s)) {
						sk_des.setCompoundDrawablesWithIntrinsicBounds(null, null,
								drawable, null);
						return;
					} else {
						sk_des.setCompoundDrawablesWithIntrinsicBounds(null, null,
								null, null);
					}
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
				}

				@Override
				public void afterTextChanged(Editable s) {

				}
			};

			// 触屏 删除账号
			private OnTouchListener describe_ontouch = new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					switch (event.getAction()) {
					case MotionEvent.ACTION_UP:
						int curx = (int) event.getX();
						if (curx > v.getWidth() - 38
								&& !TextUtils.isEmpty(sk_des.getText())) {
							sk_des.setText("");
							sk_des.setCompoundDrawablesWithIntrinsicBounds(null,
									null, drawable, null);
							int cacheInputType = sk_des.getInputType();
							sk_des.setInputType(InputType.TYPE_NULL);
							sk_des.onTouchEvent(event);
							sk_des.setInputType(cacheInputType);
							sk_des.setCompoundDrawablesWithIntrinsicBounds(null,
									null, drawable, null);
							return true;
						}
						break;
					}
					return false;
				}
			};

			/* (non-Javadoc)
			 * @see android.app.Activity#onResume()
			 */
			@Override
			protected void onResume() {
				// TODO Auto-generated method stub
				super.onResume();
				/**
				 * 此处调用基本统计代码
				 */
				MobclickAgent.onResume(this);
			}
			
			@Override
			public void onPause() {
				super.onPause();
				/**
				 * 此处调用基本统计代码
				 */
				MobclickAgent.onPause(this);
			}
}
