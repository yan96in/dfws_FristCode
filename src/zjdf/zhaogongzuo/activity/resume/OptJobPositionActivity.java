/**
 * Copyright © 2014年5月13日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.activity.resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import zjdf.zhaogongzuo.activity.resume.JobFavoriteActivity;
import zjdf.zhaogongzuo.animation.AnimationBox;
import zjdf.zhaogongzuo.controllers.PositionClassifyController;
import zjdf.zhaogongzuo.entity.Areas;
import zjdf.zhaogongzuo.entity.PositionClassify;
import zjdf.zhaogongzuo.entity.ResumeIntentionDesiredPosition;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.ui.PositionClassItemView;
import zjdf.zhaogongzuo.utils.MyHashMap;

/**
 * 职位多选
 * 
 * @author Eilin.Yang VearyEast
 * @since 2014年5月13日
 * @version v1.0.0
 * @modify
 */
public class OptJobPositionActivity extends Activity {

	private Context context;
	/** 职位分类编码 */
	public static String code = "";
	/** 职位分类名称 */
	public static String value = "";
	/** 返回 */
	private ImageButton goback;
	/** 主页面 */
	private ScrollView scroll_page_main;
	/** 子页面 */
	private ScrollView scroll_page_sub;
	/** 主页面容器 */
	private LinearLayout linear_main;
	/** 子页面容器 */
	private LinearLayout linear_sub;
	/** 已选职位 */
	private RelativeLayout rela_selected;
	/** 已选职位容器 */
	private LinearLayout linear_selected;
	/** 已选职位数量 */
	private TextView txt_selected_num;
	/** 所有分类控件 */
	private List<PositionClassItemView> allViews;
	/** 已选 */
	private MyHashMap<String, PositionClassify> map_selectedPosition;
	/** 已选 */
	private MyHashMap<String, TextView> map_selectedViews;

	/** 进入动画 */
	private Animation animation_in;
	/** 退出动画 */
	private Animation animation_out;

	/** 当前显示的是否是子页面 */
	private boolean isSub = false;
	/** 返回按钮 */

	/** 职位分类编码 */
	public static final String POSITION_CODE = "position_code";
	/** 职位分类名称 */
	public static final String POSITION_VALUE = "position_value";

	private LinearLayout.LayoutParams params;
	/** 不限 */
	private Button save;
	/** 已选地点图标 */
	private Drawable rightDrawable_A;
	/** 已选地点图标 */
	private Drawable rightDrawable_V;
	private LinearLayout.LayoutParams params_select;
	/** 请求路径 */
	private int requst;
	private int count = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_opt_position_mutilselected);
		context = this;
		requst = getIntent().getIntExtra("request", 0);
		initView();
		initAnimations();
		initDatas();
	}

	private void initView() {
		goback = (ImageButton) findViewById(R.id.goback);
		scroll_page_main = (ScrollView) findViewById(R.id.scroll_page_main);
		scroll_page_sub = (ScrollView) findViewById(R.id.scroll_page_sub);
		linear_main = (LinearLayout) findViewById(R.id.linear_main);
		linear_sub = (LinearLayout) findViewById(R.id.linear_sub);
		scroll_page_main.setVisibility(View.VISIBLE);
		scroll_page_sub.setVisibility(View.GONE);
		rela_selected = (RelativeLayout) findViewById(R.id.rela_selected);
		linear_selected = (LinearLayout) findViewById(R.id.linear_selected);
		linear_selected.setVisibility(View.GONE);
		txt_selected_num = (TextView) findViewById(R.id.txt_selected_num);
		goback = (ImageButton) findViewById(R.id.goback);
		save = (Button) findViewById(R.id.btn_save);
		rightDrawable_A = getResources().getDrawable(R.drawable.ic_select_up);
		rightDrawable_A.setBounds(0, 0, rightDrawable_A.getMinimumWidth(),
				rightDrawable_A.getMinimumHeight());
		rightDrawable_V = getResources().getDrawable(R.drawable.ic_select_up);
		rightDrawable_V.setBounds(0, 0, rightDrawable_V.getMinimumWidth(),
				rightDrawable_V.getMinimumHeight());
		goback.setOnClickListener(listener);
		save.setOnClickListener(listener);
		rela_selected.setOnClickListener(listener);
		params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.height = 1;
		params.leftMargin = 40;
		params_select = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params_select.topMargin = 3;
		params_select.bottomMargin = 3;
		params_select.leftMargin = 10;
		scroll_page_main.setOnTouchListener(touchListener);
		scroll_page_sub.setOnTouchListener(touchListener);
	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == goback) {
				if (isSub) {
					scroll_page_sub.setAnimation(animation_out);
					scroll_page_sub.startAnimation(animation_out);
				} else
					finish();
			}
			if (v == save) {
				if (map_selectedPosition==null||map_selectedPosition.getSize()==0) {
					CustomMessage.showToast(context, "请至少选择一个职位！", Gravity.CENTER, 0);
					return;
				}
				formatDatas();
				startIntent();
			}
			if (v == rela_selected) {
				switchSelectedLayout();
			}

		}
	};

	/**
	 * 初始化被选中的地区
	 */
	private void initSelected() {
		/*
		 * if (JobFavoriteActivity.desiredPositions!=null&&JobFavoriteActivity.
		 * desiredPositions.size()>0) { for (ResumeIntentionDesiredPosition item
		 * : JobFavoriteActivity.desiredPositions) { if (item!=null) {
		 * PositionClassify
		 * ar=PositionClassifyController.mapPositionClassifies.get
		 * (item.position_code); createTextView(ar); } } }
		 */
		txt_selected_num.setText(map_selectedPosition.getSize() + "/5");
	}

	/**
	 * 已选地点显示、隐藏切换
	 */
	private void switchSelectedLayout() {
		int visibility = linear_selected.getVisibility();
		if (visibility == View.GONE) {
			txt_selected_num.setCompoundDrawables(null, null, rightDrawable_A,
					null);
			linear_selected.setVisibility(View.VISIBLE);
		} else {
			txt_selected_num.setCompoundDrawables(null, null, rightDrawable_V,
					null);
			linear_selected.setVisibility(View.GONE);
		}
	}

	/**
	 * 是否选满了
	 * 
	 * @return
	 */
	private boolean isFull() {
		if (map_selectedPosition != null && map_selectedPosition.getSize() > 4) {
			CustomMessage.showToast(context, "最多只能选5个!", 0);
			return true;
		}
		return false;
	}

	/**
	 * 创建以选中地址
	 * 
	 * @param a
	 */
	private void createTextView(final PositionClassify p) {
		if (p == null || map_selectedPosition == null) {
			return;
		}
		if (isFull()) {
			return;
		}
		final String code = p.getCode();
		if (map_selectedPosition.containKey(code)) {
			return;
		}
		final TextView aView = (TextView) getLayoutInflater().inflate(
				R.layout.layout_textview, null);
		aView.setText(p.getValue());
		aView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				removeTextView(p);
				clearCheckedStatus(code);
			}
		});
		map_selectedPosition.addData(code, p);
		map_selectedViews.addData(code, aView);
		linear_selected.addView(map_selectedViews.getData(code), params_select);
		txt_selected_num.setText(map_selectedPosition.getSize() + "/5");
		return;
	}

	/**
	 * 移除TextView
	 * 
	 * @param a
	 */
	private void removeTextView(PositionClassify p) {
		if (p == null || map_selectedPosition == null
				|| map_selectedViews == null) {
			return;
		}
		final String code = p.getCode();
		map_selectedPosition.removeData(code);
		linear_selected.removeView(map_selectedViews.removeData(code));
		txt_selected_num.setText(map_selectedPosition.getSize() + "/5");
		if (map_selectedPosition.getSize() == 0) {
			linear_selected.setVisibility(View.GONE);
		}
		return;
	}

	/**
	 * 初始化选中状态
	 * 
	 * <pre>
	 * 方法
	 * </pre>
	 */
	private void clearCheckedStatus(String code) {
		for (PositionClassItemView itemView : allViews) {
			if (itemView != null) {
				if (code.equals(itemView.getCode())) {
					itemView.setCheckedStatus(false);
				}
			}
		}
	}

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
		map_selectedPosition = new MyHashMap<String, PositionClassify>(5);
		map_selectedViews = new MyHashMap<String, TextView>(5);
		allViews = new ArrayList<PositionClassItemView>();
		initSelected();
		if (PositionClassifyController.positionClassifies != null
				&& PositionClassifyController.positionClassifies.size() > 0) {
			int n = PositionClassifyController.positionClassifies.size();
			count = count + n;
			for (int i = 0; i < n; i++) {
				final PositionClassify positionClassify = PositionClassifyController.positionClassifies
						.get(i);
				final PositionClassItemView positionView = new PositionClassItemView(
						context);
				positionView.setPositionClassify(
						positionClassify,
						map_selectedPosition.containKey(positionClassify
								.getCode()) & !positionClassify.isHasSub());
				positionView.setOnItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (positionClassify.isHasSub()) {
							showSubAddressViews(positionClassify);
						} else {
							boolean flag = positionView.getCheckedStatus();
							positionView.setCheckedStatus(!flag);
							if (!flag) {
								code = positionClassify.getCode();
								value = positionClassify.getValue();
								if (isFull()) {
									positionView.setCheckedStatus(false);
								} else
									createTextView(positionClassify);
							} else {
								code = "";
								value = "";
								removeTextView(positionClassify);
							}
						}
					}
				});
				allViews.add(i, positionView);
				linear_main.addView(allViews.get(i));
				if (i < n - 1) {
					View line = LayoutInflater.from(context).inflate(
							R.layout.layout_line, null);
					linear_main.addView(line, params);
				} else {
					params = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					params.height = 1;
					params.leftMargin = 1;
					params.bottomMargin = 10;
					View line = LayoutInflater.from(context).inflate(
							R.layout.layout_line, null);
					linear_main.addView(line, params);
				}
			}
		}
	}

	/**
	 * 加载子控件
	 * 
	 * <pre>
	 * 方法
	 * </pre>
	 * 
	 * @param area
	 *            地区
	 */
	private void showSubAddressViews(PositionClassify positionClassify) {
		params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.height = 1;
		params.leftMargin = 40;
		if (null != linear_sub && linear_sub.getChildCount() > 0) {
			linear_sub.removeAllViews();
		}
		if (positionClassify != null) {
			List<PositionClassify> pList = positionClassify.getSubList();
			if (pList.size() > 0) {
				if (!pList.get(0).getCode().equals(positionClassify.getCode())) {
					PositionClassify pp = new PositionClassify();
					pp.setCode(positionClassify.getCode());
					pp.setPcode(positionClassify.getPcode());
					pp.setHasSub(false);
					pp.setSubList(null);
					pp.setValue(positionClassify.getValue());
					pList.add(0, pp);
				}
			}
			if (pList != null && pList.size() > 0) {
				int nn = pList.size();
				for (int i = 0; i < nn; i++) {
					final PositionClassify p = pList.get(i);
					if (p != null) {
						final PositionClassItemView itemView = new PositionClassItemView(
								context);
						itemView.setPositionClassify(p,
								map_selectedPosition.containKey(p.getCode()));
						itemView.setOnItemClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								if (p.isHasSub()) {

								} else {
									boolean flag = itemView.getCheckedStatus();
									itemView.setCheckedStatus(!flag);
									if (!flag) {
										code = p.getCode();
										value = p.getValue();
										// startIntent();
										// finish();
										if (isFull()) {
											itemView.setCheckedStatus(false);
										} else
											createTextView(p);
										scroll_page_sub
												.setAnimation(animation_out);
										scroll_page_sub
												.startAnimation(animation_out);
									} else {
										code = "";
										value = "";
										removeTextView(p);
									}
								}
							}

						});
						allViews.add(count + i, itemView);
						linear_sub.addView(allViews.get(count + i));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && isSub) {
			scroll_page_sub.setAnimation(animation_out);
			scroll_page_sub.startAnimation(animation_out);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 完成天传
	 * 
	 * <pre>
	 * 方法
	 * </pre>
	 * 
	 * @param keyword
	 *            关键字
	 */
	private void startIntent() {
		Intent intent = new Intent();
		intent.putExtra("code", code);
		intent.putExtra("value", value);
		this.setResult(requst, intent);
		finish();
	}

	/**
	 * 格式化数据
	 */
	private void formatDatas() {
		if (map_selectedPosition != null && map_selectedPosition.getSize() > 0) {
			HashMap<String, PositionClassify> map_p = map_selectedPosition
					.getHashMap();
			int nn = map_p.size();
			StringBuffer str_value = new StringBuffer(nn);
			StringBuffer str_code = new StringBuffer(nn);
			for (Map.Entry<String, PositionClassify> item : map_p.entrySet()) {
				if (item != null) {
					str_code.append(item.getKey() + ",");
					str_value.append(item.getValue().getValue() + ",");
				}
			}
			if (str_code.length() > 0) {
				str_code.deleteCharAt(str_code.lastIndexOf(","));
			}
			if (str_value.length() > 0) {
				str_value.deleteCharAt(str_value.lastIndexOf(","));
			}
			code = "";
			value = "";
			code = str_code.toString();
			value = str_value.toString();
		}
	}

	private OnTouchListener touchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			int y = 0;
			int sy = 0;
			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				y = (int) event.getY();
				if (Math.abs(y - sy) > 20
						&& (linear_selected.getVisibility() == View.VISIBLE)) {
					hand.sendEmptyMessageDelayed(0, 20);
				}
				break;
			case MotionEvent.ACTION_DOWN:
				sy = y = (int) event.getY();
				break;
			default:
				break;
			}
			return false;
		}

		Handler hand = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				linear_selected.setVisibility(View.GONE);
			}
		};
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
