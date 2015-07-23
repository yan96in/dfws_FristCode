package zjdf.zhaogongzuo.activity.personal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.animation.AnimationBox;
import zjdf.zhaogongzuo.controllers.AddressController;
import zjdf.zhaogongzuo.entity.Areas;
import zjdf.zhaogongzuo.fragments.SearchFragment;
import zjdf.zhaogongzuo.ui.AddressItemView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 意向地点
 * 
 * @author Administrator
 * 
 */
public class IntentionPlaceActivity extends Activity {

	private CheckBox tv_loc_info;
	private String value = "";// 返回数据
	private Context context;// 上下文

	// 获取当前地址
	public static String values = "";
	/** 所在地 */
	private Areas localArea;
	/** 热门城市 */
	private List<Areas> hotAreas;
	/** 所有城市 */
	private List<Areas> allAreas;
	/** 热门城市 */
	private LinearLayout linear_hotareas;
	/** 所有城市 */
	private LinearLayout linear_allareas;
	private LinearLayout.LayoutParams params;
	private List<AddressItemView> allViews;
	private int count = 0;
	/** 主页 */
	private ScrollView scroll_page_main;
	/** 子页面 */
	private ScrollView scroll_page_sub;
	/** 子页面容器 */
	private LinearLayout linear_sub;

	/** 进入动画 */
	private Animation animation_in;
	/** 退出动画 */
	private Animation animation_out;

	/** 当前显示的是否是子页面 */
	private boolean isSub = false;
	/** 返回按钮 */

	/** 地区编码 */
	public static final String AREA_CODE = "area_code";
	/** 地区名称 */
	public static final String AREA_VALUE = "area_value";

	private ApplicationConfig applicationConfig;// 全局控制

	private LinkedList<Areas> linkedList;// 地区
	private HashMap<String, TextView> textViewMap;// 地区
	private Drawable drawable;// 删除图片
	private LinearLayout line_city;
	private ImageButton resume_return;// 返回
	private Button resume_submit;// 确定

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_personal_micro_resume_place);
		context = getApplicationContext();
		applicationConfig = (ApplicationConfig) context.getApplicationContext();
		initView();
		initAnimations();
		initDatas();
	}
	/**
	 * 初始化 地理位置
	 * 
	 */
	private void initView() {

		tv_loc_info = (CheckBox) findViewById(R.id.tv_loc_info);
		resume_submit = (Button) findViewById(R.id.resume_submit);
		resume_return = (ImageButton) findViewById(R.id.resume_return);
		resume_submit.setOnClickListener(listener);
		resume_return.setOnClickListener(listener);

		scroll_page_main = (ScrollView) findViewById(R.id.scroll_page_main);
		scroll_page_sub = (ScrollView) findViewById(R.id.scroll_page_sub);

		line_city = (LinearLayout) findViewById(R.id.city_line);

		linear_hotareas = (LinearLayout) findViewById(R.id.linear_hotareas);
		linear_allareas = (LinearLayout) findViewById(R.id.linear_allareas);
		linear_sub = (LinearLayout) findViewById(R.id.linear_sub);
		scroll_page_sub.setVisibility(View.GONE);
		params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.height = 1;
		params.leftMargin = 40;
		isSub = false;


		drawable = getResources().getDrawable(R.drawable.ic_but_close);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		linkedList = new LinkedList<Areas>();
		textViewMap = new HashMap<String, TextView>();

		// 这里给 chebox 值 筛选的
		tv_loc_info.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					buttonView.setText(applicationConfig.city);
					tv_loc_info.setText(applicationConfig.city);
					value = applicationConfig.city;
				} else {
					buttonView.setText(applicationConfig.city);
				}

			}
		});

	}

	//点击事件
	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 提交
			case R.id.resume_submit:
				if (value != null && value.length() != 0) {
					Intent it = new Intent();
					it.putExtra("IntentionPlace", value);
					setResult(Activity.RESULT_OK, it);
				} else {
					startIntent();
				}
				finish();

				break;
			// 返回
			case R.id.resume_return:
				Intent it1 = new Intent();
				if (value != null && value.length() != 0) {
					it1.putExtra("IntentionPlace", value);
					setResult(Activity.RESULT_OK, it1);
				} else {
					startIntent();
				}
				finish();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 初始化动画
	 * 
	 * <pre>
	 * 方法
	 * </pre>
	 */
	private void initAnimations() {
		animation_in = AnimationBox.bindAnimation(context,
				R.anim.address_sub_in, new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						scroll_page_sub.setVisibility(View.VISIBLE);
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						isSub = true;
						// scroll_page_sub.requestFocus();
						scroll_page_main.setVisibility(View.INVISIBLE);
					}
				});
		animation_out = AnimationBox.bindAnimation(context,
				R.anim.address_sub_out, new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						scroll_page_main.setVisibility(View.VISIBLE);
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						scroll_page_sub.setVisibility(View.GONE);
						isSub = false;
						scroll_page_main.setFocusable(true);
						scroll_page_main.setFocusableInTouchMode(true);
						new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								scroll_page_main.requestFocus();
								scroll_page_main.bringToFront();
							}
						}, 800);
					}
				});
	}

	private void initDatas() {
		initHotAreas();
		initAllAreas();
	}

	/**
	 * 加载人们城市
	 */
	private void initHotAreas() {
		if (AddressController.hotAreas != null
				&& AddressController.hotAreas.size() > 0) {
			allViews = new ArrayList<AddressItemView>();
			int nhot = AddressController.hotAreas.size();
			count = nhot;
			for (int i = 0; i < nhot; i++) {
				final Areas area = AddressController.hotAreas.get(i);
				final AddressItemView areaView = new AddressItemView(context);
				areaView.setArea(area);
				areaView.setOnItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (area.getHassub() == 1) {
							showSubAddressViews(area);
						} else {
							boolean flag = areaView.getCheckedStatus();
							// clearCheckedStatus();
							areaView.setCheckedStatus(!flag);
							int mn = textViewMap.size() + 1;
							TextView textView = creatCity(mn, area);
							if (!flag) {
								// code = a.getCode();
								values = area.getValue();
								if (textViewMap.size() < 5) {
									textViewMap.put(area.getCode(), textView);
									line_city.addView(textViewMap.get(area
											.getCode()));
								} else {
									Toast.makeText(context, "超出5个了", 0);
								}
								// startIntent();
								// finish();
							} else {
								// code = "";
								textViewMap.remove(area.getCode());
								line_city.removeView(textView);
								values = "";
							}
						}
					}

				});
				allViews.add(i, areaView);
				linear_hotareas.addView(allViews.get(i));
				if (i < nhot - 1) {
					View line = LayoutInflater.from(context).inflate(
							R.layout.layout_line, null);
					linear_hotareas.addView(line, params);
				}
			}
		}
	}

	/**
	 * 加载所有城市
	 * 
	 * <pre>
	 * 方法
	 * </pre>
	 */
	private void initAllAreas() {
		if (AddressController.allAreas != null
				&& AddressController.allAreas.size() > 0) {
			if (allViews == null) {
				allAreas = new ArrayList<Areas>();
			}
			allAreas = AddressController.allAreas;
			int nall = allAreas.size();
			Collections.sort(allAreas, new Comparator<Areas>() {

				@Override
				public int compare(Areas lhs, Areas rhs) {
					// TODO Auto-generated method stub
					return lhs.getSortkey().compareTo(rhs.getSortkey());
				}
			});
			for (int i = 0; i < nall; i++) {
				final Areas area = allAreas.get(i);
				final AddressItemView areaView = new AddressItemView(context);
				areaView.setArea(area);
				areaView.setOnItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (area.getHassub() == 1) {
							showSubAddressViews(area);
						} else {
							boolean flag = areaView.getCheckedStatus();
							// clearCheckedStatus();
							areaView.setCheckedStatus(!flag);
							int mn = textViewMap.size() + 1;
							TextView textView = creatCity(mn, area);
							if (!flag) {
								// code = a.getCode();
								values = area.getValue();
								if (textViewMap.size() < 5) {
									textViewMap.put(area.getCode(), textView);
									line_city.addView(textViewMap.get(area
											.getCode()));
								} else {
									Toast.makeText(context, "超出5个了", 0);
								}
								// startIntent();
								// finish();
							} else {
								// code = "";
								textViewMap.remove(area.getCode());
								line_city.removeView(textView);
								values = "";
							}
						}
					}

				});
				allViews.add(count + i, areaView);
				linear_allareas.addView(allViews.get(count + i));
				if (i < nall - 1) {
					View line = LayoutInflater.from(context).inflate(
							R.layout.layout_line, null);
					linear_allareas.addView(line, params);
				} else {
					params = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					params.height = 1;
					params.leftMargin = 1;
					params.bottomMargin = 15;
					View line = LayoutInflater.from(context).inflate(
							R.layout.layout_line, null);
					linear_allareas.addView(line, params);
				}
			}
			count = count + nall;
		}
	}

	/**
	 * 加载子控件
	 * 
	 * @param area
	 *            地区
	 */
	private void showSubAddressViews(Areas area) {
		params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.height = 1;
		params.leftMargin = 40;
		if (null != linear_sub && linear_sub.getChildCount() > 0) {
			linear_sub.removeAllViews();
		}
		if (area != null) {
			List<Areas> aList = area.getAreas();
			if (aList != null && aList.size() > 0) {
				int nn = aList.size();
				for (int i = 0; i < nn; i++) {
					final Areas a = aList.get(i);
					if (a != null) {
						final AddressItemView aItemView = new AddressItemView(
								context);
						aItemView.setArea(a);
						aItemView
								.setOnItemClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										if (a.getHassub() == 1) {

										} else {
											boolean flag = aItemView
													.getCheckedStatus();
											aItemView.setCheckedStatus(!flag);
											int mn = textViewMap.size() + 1;
											TextView textView = creatCity(mn, a);
											if (!flag) {
												// code = a.getCode();
												values = a.getValue();
												if (textViewMap.size() < 5) {
													textViewMap.put(
															a.getCode(),
															textView);
													line_city
															.addView(textViewMap.get(a
																	.getCode()));
												} else {
													textViewMap.remove(a
															.getCode());
													line_city.removeView(textView);
													Toast.makeText(context,
															"超出5个了", 0);
												}
											} else {
												// code = "";
												textViewMap.remove(a.getCode());
												line_city.removeView(textView);
												values = "";
											}
										}
									}
								});
						linear_sub.addView(aItemView);
						if (i < nn - 1) {
							View line = LayoutInflater.from(context).inflate(
									R.layout.layout_line, null);
							linear_sub.addView(line, params);
						} else {
							params = new LinearLayout.LayoutParams(
									LinearLayout.LayoutParams.MATCH_PARENT,
									LinearLayout.LayoutParams.WRAP_CONTENT);
							params.height = 1;
							params.leftMargin = 1;
							View line = LayoutInflater.from(context).inflate(
									R.layout.layout_line, null);
							linear_sub.addView(line, params);
						}
					}
				}
				scroll_page_sub.setAnimation(animation_in);
				scroll_page_sub.startAnimation(animation_in);
			}
		}
	}

	// 动态生成 textview
	private TextView creatCity(int id, final Areas areas) {
		LinearLayout.LayoutParams layoutParams = null;
		TextView txt_city = new TextView(context);
		txt_city.setId(id);
		txt_city.setText(areas.getValue());
		txt_city.setTextColor(Color.BLACK);
		txt_city.setTextSize(25);
		txt_city.setFocusable(true);
		txt_city.setPadding(20, 0, 0, 0);
		txt_city.setBackgroundResource(R.drawable.ic_textview_bg1 );
		txt_city.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable,
				null);
		layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.leftMargin = 10;
		txt_city.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textViewMap.remove(areas.getCode());
			}
		});
		return txt_city;
	}

	/**
	 * @param keyword
	 *            返回值
	 */
	private void startIntent() {
		Intent intent = new Intent();
		intent.putExtra("IntentionPlace", values);
		setResult(Activity.RESULT_OK, intent);
	}

	// 监听返回键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && isSub) {
			startIntent();
			scroll_page_sub.setAnimation(animation_out);
			scroll_page_sub.startAnimation(animation_out);
			// finish();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent it = new Intent();
			if (value != null && value.length() != 0) {
				it.putExtra("IntentionPlace", value);
				setResult(Activity.RESULT_OK, it);
			} else {
				startIntent();
			}
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
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
