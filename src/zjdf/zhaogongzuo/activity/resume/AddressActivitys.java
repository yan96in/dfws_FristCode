/**
 * Copyright © 2014年3月20日 FindJob www.veryeast.com
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
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.more.SubscribeActivity;
import zjdf.zhaogongzuo.animation.AnimationBox;
import zjdf.zhaogongzuo.controllers.AddressController;
import zjdf.zhaogongzuo.entity.Areas;
import zjdf.zhaogongzuo.fragments.SearchFragment;
import zjdf.zhaogongzuo.ui.AddressItemView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * <h2>地址选择</h2>
 * 
 * <pre></pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年3月20日
 * @version
 * @modify       用于我的简历  里面，选择地址，记住地址
 */
public class AddressActivitys extends Activity {

	private Context context;
	public static String code = "";
	public static String value = "";
	/** 所在地 */
	private Areas localArea;
	/** 热门城市 */
	private List<Areas> hotAreas;
	/** 所有城市 */
	private List<Areas> allAreas;
	/** 当前城市 */
	private AddressItemView localAreaView;
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
	
	/**进入动画*/
	private Animation animation_in;
	/**退出动画*/
	private Animation animation_out;
	
	/**当前显示的是否是子页面*/
	private boolean isSub=false;
	/**返回按钮*/
	
	
	/**地区编码*/
	public static final String AREA_CODE="area_code";
	/**地区名称*/
	public static final String AREA_VALUE="area_value";
	/**返回*/
	private ImageButton goback;
	/**不限*/
	private Button unlimited;
	/**来源*/
	private int request=0;
	private String areaCode;
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search_address);
		context = this;
		request=getIntent().getIntExtra("request", 0);
		areaCode=getIntent().getStringExtra("areaCode");
		initView();
		initAnimations();
		initDatas();
	}

	private void initView() {
		scroll_page_main = (ScrollView) findViewById(R.id.scroll_page_main);
		scroll_page_sub = (ScrollView) findViewById(R.id.scroll_page_sub);
		localAreaView = (AddressItemView) findViewById(R.id.view_local_area);
		linear_hotareas = (LinearLayout) findViewById(R.id.linear_hotareas);
		linear_allareas = (LinearLayout) findViewById(R.id.linear_allareas);
		linear_sub = (LinearLayout) findViewById(R.id.linear_sub);
		goback=(ImageButton)findViewById(R.id.goback);
		unlimited=(Button)findViewById(R.id.unlimited);
		goback.setOnClickListener(listener);
		unlimited.setOnClickListener(listener);
		scroll_page_sub.setVisibility(View.GONE);
		params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.height = 1;
		params.leftMargin = 40;
		isSub=false;
	}
	
	private View.OnClickListener listener=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v==goback) {
				finish();
			}
			if (v==unlimited) {
				code="";
				value="不限";
				Intent intent = new Intent();
				intent.putExtra(AREA_CODE, code);
				intent.putExtra(AREA_VALUE, value);
				((Activity)context).setResult(SubscribeActivity.REQUESTCODE_ADDRESS, intent);
				finish();
			}
		}
	};
	
	/**
	 * 初始化动画
	 *<pre>方法  </pre>
	 */
	private void initAnimations(){
		animation_in=AnimationBox.bindAnimation(context, R.anim.address_sub_in, new AnimationListener() {
			
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
				isSub=true;
//				scroll_page_sub.requestFocus();
				scroll_page_main.setVisibility(View.INVISIBLE);
			}
		});
		animation_out=AnimationBox.bindAnimation(context, R.anim.address_sub_out, new AnimationListener() {
			
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
				isSub=false;
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
		if (request==0) {
			code=SearchFragment.areaCode;
			value=SearchFragment.areaValue;
		}
		initLocalArea();
		initHotAreas();
		initAllAreas();
	}
	
	/**
	 * 初始化本地信息
	 */
	private void initLocalArea(){
		if (AddressController.mArea!=null) {
			localAreaView.setArea(AddressController.mArea,areaCode==null?false:(AddressController.mArea.getCode().equals(areaCode)));
			localAreaView.setOnItemClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					code = AddressController.mArea.getCode();
					value= AddressController.mArea.getValue();
					startIntent();
					finish();
				}

			});
		}
	}

	/**
	 * 加载人们城市
	 * 
	 * <pre>
	 * 方法
	 * </pre>
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
				areaView.setArea(area,areaCode==null?false:(area.getCode().equals(areaCode)));
				areaView.setOnItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (area.getHassub() == 1) {
							showSubAddressViews(area);
						} else {
//							boolean flag = areaView.getCheckedStatus();
//							clearCheckedStatus();
//							areaView.setCheckedStatus(!flag);
//							if (!flag) {
								code = area.getCode();
								value= area.getValue();
								startIntent();
								finish();
//							} else {
//								code = "";
//								value = "";
//							}
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
				areaView.setArea(area,areaCode==null?false:(area.getCode().equals(areaCode)));
				areaView.setOnItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (area.getHassub() == 1) {
							showSubAddressViews(area);
						} else {
//							boolean flag = areaView.getCheckedStatus();
//							clearCheckedStatus();
//							areaView.setCheckedStatus(!flag);
//							if (!flag) {
								code = area.getCode();
								value= area.getValue();
								startIntent();
								finish();
//							} else {
//								code = "";
//								value= "";
//							}
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
	 * 初始化选中状态
	 * 
	 * <pre>
	 * 方法
	 * </pre>
	 */
	private void clearCheckedStatus() {
		for (AddressItemView itemView : allViews) {
			itemView.setCheckedStatus(false);
		}
	}

	/**
	 * 加载子控件
	 *<pre>方法  </pre>
	 * @param area 地区
	 */
	private void showSubAddressViews(Areas area) {
		params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.height = 1;
		params.leftMargin = 40;
		if (null!=linear_sub&&linear_sub.getChildCount()>0) {
			linear_sub.removeAllViews();
		}
		if (area != null) {
			List<Areas> aList = area.getAreas();
			if (aList != null && aList.size() > 0) {
				if (!aList.get(0).getCode().equals(area.getCode())&&!"370000".equals(area.getCode())) {
					Areas aa=new Areas();
					aa.setSortkey(area.getSortkey());
					aa.setAreas(null);
					aa.setCode(area.getCode());
					aa.setHassub(0);
					aa.setIshot(area.getIshot());
					aa.setParentcode(area.getParentcode());
					aa.setValue(area.getValue());
					aList.add(0, aa);
				}
				int nn = aList.size();
				for (int i = 0; i < nn; i++) {
					final Areas a = aList.get(i);
					if (a != null) { 
						final AddressItemView aItemView = new AddressItemView(context);
						aItemView.setArea(a,areaCode==null?false:(a.getCode().equals(areaCode)));
						aItemView
								.setOnItemClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										if (a.getHassub() == 1) {

										} else {
//											boolean flag = aItemView
//													.getCheckedStatus();
//											clearCheckedStatus();
//											aItemView.setCheckedStatus(!flag);
//											if (!flag) {
												code = a.getCode();
												value= a.getValue();
												startIntent();
												finish();
//											} else {
//												code = "";
//												value = "";
//											}
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
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK&&isSub) {
			scroll_page_sub.setAnimation(animation_out);
			scroll_page_sub.startAnimation(animation_out);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 完成天传
	 *<pre>方法  </pre>
	 * @param keyword 关键字
	 */
	private void startIntent(){
		Intent intent = new Intent();
		intent.putExtra(AREA_CODE, code);
		intent.putExtra(AREA_VALUE, value);
		this.setResult(SubscribeActivity.REQUESTCODE_ADDRESS, intent);
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
