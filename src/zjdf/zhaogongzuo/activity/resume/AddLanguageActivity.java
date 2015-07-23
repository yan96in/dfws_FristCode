package zjdf.zhaogongzuo.activity.resume;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.controllers.MyResumeConttroller;
import zjdf.zhaogongzuo.entity.OptionKeyValue;
import zjdf.zhaogongzuo.utils.NetWorkUtils;

/**
 * 添加语言能力
 * 
 * @author Administrator
 * 
 */
public class AddLanguageActivity extends Activity {
	private Button but_submit;// 保存
	private ImageButton but_return;// 返回

	private RelativeLayout rela_language;// 语言
	private TextView txt_language;
	private RelativeLayout rela_proficiency;// 熟练程度
	private TextView txt_proficiency;

	private Context context;// 上下文
	private MyResumeConttroller conttroller;// 控制器

	private int state;// 返回状态
	private int n;
	private int la;

	// 接收适配器 点击事件 传递过来的
	private String id;// 语言ID
	private String language_id;// 特长ID
	private String mastery_id;// 熟练程度ID

	private int language = 0;
	private String ablity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_resume_language_add_item);
		context = this;
		conttroller = new MyResumeConttroller(context);
		id = getIntent().getStringExtra("ids");
		language_id = getIntent().getStringExtra("language_id");
		mastery_id = getIntent().getStringExtra("mastery_id");
		initView();
		assignment();
	}

	// 如果是item传递过来的话
	private void assignment() {
		if (id != null) {
			txt_language.setText(language_id);
			txt_proficiency.setText(mastery_id);
		}
	}

	// 刷新UI
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			but_submit.setEnabled(true);
			switch (msg.what) {
			case 1:
				if (state == 1) {
					Toast.makeText(context, "保存成功", Toast.LENGTH_LONG).show();
					finish();
				} else {
					Toast.makeText(context, "保存失败", Toast.LENGTH_LONG).show();
				}
				break;
			}
		};
	};

	// 保存数据
	private void setData() {
		if (!NetWorkUtils.checkNetWork(context)) {
			Toast.makeText(context, "连接网络失败，请检查网络！", Toast.LENGTH_LONG).show();
			return;
		}
		String str_langunage = txt_language.getText().toString();
		ablity = txt_proficiency.getText().toString();
		if (str_langunage == null || str_langunage.trim().equals("")) {
			Toast.makeText(context, "语言不能为空！", Toast.LENGTH_LONG).show();
			return;

		}
		if (ablity == null || ablity.trim().equals("")) {
			Toast.makeText(context, "熟练不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		language = getLanguageCode(str_langunage);		
		if (ablity.contains("较差")) {
			n = 1;
		} else if (ablity.contains("一般")) {
			n = 2;
		} else if (ablity.contains("良好")) {
			n = 3;
		} else if (ablity.contains("熟练")) {
			n = 4;
		} else if (ablity.contains("精通")) {
			n = 5;
		}
		but_submit.setEnabled(false);
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				state = conttroller.set_language(language, n, id);
				handler.sendEmptyMessage(1);
			}
		}.start();

	}

	// 厉遍语言
	private Integer getLanguageCode(String value) {
		if (value == null) {
			return null;
		}
		for (OptionKeyValue element : FrameConfigures.list_language) {
			if (element != null) {
				if (element.key.equals(value)) {
					return element.value;
				}
			}

		}
		return null;

	}

	// 初始化
	private void initView() {
		// TODO Auto-generated method stub
		but_submit = (Button) findViewById(R.id.but_submit);
		but_return = (ImageButton) findViewById(R.id.but_return);
		rela_language = (RelativeLayout) findViewById(R.id.rela_language);
		txt_language = (TextView) findViewById(R.id.txt_language);
		rela_proficiency = (RelativeLayout) findViewById(R.id.rela_proficiency);
		txt_proficiency = (TextView) findViewById(R.id.txt_proficiency);

		but_submit.setOnClickListener(clickListener);
		but_return.setOnClickListener(clickListener);
		rela_language.setOnClickListener(clickListener);
		rela_proficiency.setOnClickListener(clickListener);

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
			// 保存
			case R.id.but_submit:
				setData();
				break;
			// 返回
			case R.id.but_return:
//				Intent intent = new Intent(context, LanguageActivity.class);
//				startActivity(intent);
				finish();
				break;
			// 语言
			case R.id.rela_language:
				Intent intent_RoonActivity = new Intent(context,
						LanguagesActivity.class);
				startActivityForResult(intent_RoonActivity, 1);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 掌握程度
			case R.id.rela_proficiency:
				Intent intent_LanguageProficiency = new Intent(context,
						LanguageProficiencyActivity.class);
				startActivityForResult(intent_LanguageProficiency, 2);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);

				break;
			}
		}
	};

	// 处理返回值
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 掌握语言
		case 1:
			if (data == null || "".equals(data)) {
				return;
			} else {
				txt_language.setText(data.getStringExtra("Languages"));
			}

			break;
		// 熟练程度
		case 2:
			if (data == null || "".equals(data)) {
				return;
			} else {
				txt_proficiency.setText(data
						.getStringExtra("LanguageProficiency"));
			}
			break;

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
