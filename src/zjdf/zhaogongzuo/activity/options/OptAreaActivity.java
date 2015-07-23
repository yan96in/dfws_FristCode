/**
 * Copyright © 2014年5月12日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.activity.options;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.resume.JobFavoriteActivity;
import zjdf.zhaogongzuo.animation.AnimationBox;
import zjdf.zhaogongzuo.controllers.AddressController;
import zjdf.zhaogongzuo.entity.Areas;
import zjdf.zhaogongzuo.entity.ResumeIntentionDesiredLocation;
import zjdf.zhaogongzuo.fragments.SearchFragment;
import zjdf.zhaogongzuo.ui.AddressItemView;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.MyHashMap;
import zjdf.zhaogongzuo.utils.StringUtils;
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
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 选择工作地点
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月12日
 * @version v1.0.0
 * @modify 
 */
public class OptAreaActivity extends Activity {

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
	/**已选地点*/
	private RelativeLayout rela_selected;
	/**已选地点容器*/
	private LinearLayout linear_selected;
	/**已选地点数量*/
	private TextView txt_area_selected_num;
	
	private LinearLayout.LayoutParams params;
	/**所有地点*/
	private List<AddressItemView> allViews;
	/**已选*/
	private MyHashMap<String,Areas> map_selectedArea;
	/**已选*/
	private MyHashMap<String,TextView> map_selectedViews;
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
	private Button save;
	/**已选地点图标*/
	private Drawable rightDrawable_A;
	/**已选地点图标*/
	private Drawable rightDrawable_V;
	private LinearLayout.LayoutParams params_select;
	/**请求路径*/
	private int requst;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_opt_area_mutilselected);
		context = this;
		requst=getIntent().getIntExtra("request", 0);
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
		rela_selected=(RelativeLayout)findViewById(R.id.rela_selected);
		linear_selected=(LinearLayout)findViewById(R.id.linear_selected);
		linear_selected.setVisibility(View.GONE);
		txt_area_selected_num=(TextView)findViewById(R.id.txt_area_selected_num);
		goback=(ImageButton)findViewById(R.id.goback);
		save=(Button)findViewById(R.id.btn_save);
		goback.setOnClickListener(listener);
		save.setOnClickListener(listener);
		rela_selected.setOnClickListener(listener);
		scroll_page_sub.setVisibility(View.GONE);
		rightDrawable_A=getResources().getDrawable(R.drawable.ic_select_up);
		rightDrawable_A.setBounds(0, 0, rightDrawable_A.getMinimumWidth(), rightDrawable_A.getMinimumHeight());
		rightDrawable_V=getResources().getDrawable(R.drawable.ic_select_up);
		rightDrawable_V.setBounds(0, 0, rightDrawable_V.getMinimumWidth(), rightDrawable_V.getMinimumHeight());
		params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.height = 1;
		params.leftMargin = 40;
		params_select = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params_select.topMargin=3;
		params_select.bottomMargin=3;
		params_select.leftMargin=10;
		isSub=false;
		scroll_page_main.setOnTouchListener(touchListener);
		scroll_page_sub.setOnTouchListener(touchListener);
	}
	
	private View.OnClickListener listener=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v==goback) {
				if (isSub) {
					scroll_page_sub.setAnimation(animation_out);
					scroll_page_sub.startAnimation(animation_out);
				}else
					finish();
			}
			if (v==save) {
				if (map_selectedArea==null||map_selectedArea.getSize()<1) {
					CustomMessage.showToast(context, "请至少选择一个地点！", Gravity.CENTER, 0);
					return;
				}
				formatDatas();
				startIntent();
			}
			if (v==rela_selected) {
				switchSelectedLayout();
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

	/**
	 * 初始化数据
	 */
	private void initDatas() {
		map_selectedArea=new MyHashMap<String, Areas>(5);
		map_selectedViews=new MyHashMap<String, TextView>(5);
		initSelected();
		initLocalArea();
		initHotAreas();
		initAllAreas();
	}
	
	/**
	 * 初始化被选中的地区
	 */
	private void initSelected(){
		if (JobFavoriteActivity.desiredLocations!=null&&JobFavoriteActivity.desiredLocations.size()>0) {
			for (ResumeIntentionDesiredLocation item : JobFavoriteActivity.desiredLocations) {
				if (item!=null) {
					Areas ar=AddressController.mapAreas.get(item.location_code);
					createTextView(ar);
				}
			}
		}
	}
	
	/**
	 * 已选地点显示、隐藏切换
	 */
	private void switchSelectedLayout(){
		int visibility=linear_selected.getVisibility();
		if (visibility==View.GONE) {
			txt_area_selected_num.setCompoundDrawables(null, null, rightDrawable_A, null);
			linear_selected.setVisibility(View.VISIBLE);
		}else {
			txt_area_selected_num.setCompoundDrawables(null, null, rightDrawable_V, null);
			linear_selected.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 是否选满了
	 * @return
	 */
	private boolean isFull(){
		if(map_selectedArea!=null&&map_selectedArea.getSize()>4){
			return true;
		}
		return false;
	}
	
	/**
	 * 创建以选中地址
	 * @param a
	 */
	private void createTextView(final Areas a){
		if (a==null||map_selectedArea==null) {
			return;
		}
		if (isFull()) {
			return;
		}
		final String code=a.getCode();
		if(map_selectedArea.containKey(code)){
			return;
		}
		final TextView aView=(TextView)getLayoutInflater().inflate(R.layout.layout_textview, null);
		aView.setText(a.getValue());
		aView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				removeTextView(a);
			}
		});
		map_selectedArea.addData(code, a);
		map_selectedViews.addData(code, aView);
		linear_selected.addView(map_selectedViews.getData(code),params_select);
		txt_area_selected_num.setText(map_selectedArea.getSize()+"/5");
		return;
	}
	
	/**
	 * 移除TextView
	 * @param a
	 */
	private void removeTextView(Areas a){
		if (a==null||map_selectedArea==null||map_selectedViews==null) {
			return;
		}
		final String code=a.getCode();
		map_selectedArea.removeData(code);
		linear_selected.removeView(map_selectedViews.removeData(code));
		txt_area_selected_num.setText(map_selectedArea.getSize()+"/5");
		if (map_selectedArea.getSize()==0) {
			linear_selected.setVisibility(View.GONE);
		}
		clearCheckedStatus(code);
		clearLocalAreaState(code);
		return;
	}
	
	/**
	 * 初始化本地信息
	 */
	private void initLocalArea(){
		if (AddressController.mArea!=null) {
			localArea=AddressController.mArea;
			localAreaView.setArea(localArea,map_selectedArea.containKey(localArea.getCode()));
			localAreaView.setOnItemClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					boolean flag = localAreaView.getCheckedStatus();
//					clearCheckedStatus();
					localAreaView.setCheckedStatus(!flag);
					if (!flag) {
						code = localArea.getCode();
						value= localArea.getValue();
						if (isFull()) {
							localAreaView.setCheckedStatus(false);
							CustomMessage.showToast(context, "最多只能选5个!", 0);
						}else{
							createTextView(localArea);
							checkedStatus(localArea.getCode());
						}
					} else {
						code = "";
						value = "";
						removeTextView(localArea);
//						clearCheckedStatus(localArea.getCode());
					}
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
				areaView.setArea(area,map_selectedArea.containKey(area.getCode())&(area.getHassub() != 1));
				areaView.setOnItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (area.getHassub() == 1) {
							showSubAddressViews(area);
						} else {
							boolean flag = areaView.getCheckedStatus();
//							clearCheckedStatus();
							areaView.setCheckedStatus(!flag);
							if (!flag) {
								code = area.getCode();
								value= area.getValue();
								if (isFull()) {
									areaView.setCheckedStatus(false);
									CustomMessage.showToast(context, "最多只能选5个!", 0);
								}else{
									createTextView(area);
									checkedStatus(area.getCode());
									checkedLocalAreaState(area.getCode());
								}
							} else {
								code = "";
								value = "";
								removeTextView(area);
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
				areaView.setArea(area,map_selectedArea.containKey(area.getCode())&(area.getHassub() != 1));
				areaView.setOnItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (area.getHassub() == 1) {
							showSubAddressViews(area);
						} else {
							boolean flag = areaView.getCheckedStatus();
//							clearCheckedStatus();
							areaView.setCheckedStatus(!flag);
							if (!flag) {
								code = area.getCode();
								value= area.getValue();
								if (isFull()) {
									areaView.setCheckedStatus(false);
									CustomMessage.showToast(context, "最多只能选5个!", 0);
								}else{
									createTextView(area);
									checkedStatus(area.getCode());
									checkedLocalAreaState(area.getCode());
								}
							} else {
								code = "";
								value= "";
								removeTextView(area);
							}
						}
					}

				});
				allViews.add(count + i, areaView);
				linear_allareas.addView(allViews.get(count+i));
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
			count=count+nall;
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
	 * 设置本地状态
	 * @param code
	 */
	private void clearLocalAreaState(String code){
		if (localArea!=null) {
			if (!StringUtils.isEmpty(localArea.getCode())&&localArea.getCode().equals(code)) {
				localAreaView.setCheckedStatus(false);
			}
		}
	}
	
	/**
	 * 设置本地状态
	 * @param code
	 */
	private void checkedLocalAreaState(String code){
		if (localArea!=null) {
			if (!StringUtils.isEmpty(localArea.getCode())&&localArea.getCode().equals(code)) {
				localAreaView.setCheckedStatus(true);
			}
		}
	}
	/**
	 * 初始化选中状态
	 * 
	 * <pre>
	 * 方法
	 * </pre>
	 */
	private void clearCheckedStatus(String code) {
		for (AddressItemView itemView : allViews) {
			if (itemView!=null) {
				if (code.equals(itemView.getCode())) {
					itemView.setCheckedStatus(false);
				}
			}
		}
	}
	
	/**
	 * 初始化选中状态
	 * 
	 * <pre>
	 * 方法
	 * </pre>
	 */
	private void checkedStatus(String code) {
		for (AddressItemView itemView : allViews) {
			if (itemView!=null) {
				if (code.equals(itemView.getCode())) {
					itemView.setCheckedStatus(true);
				}
			}
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
						final AddressItemView aItemView = new AddressItemView(
								context);
						aItemView.setArea(a,map_selectedArea.containKey(a.getCode()));
						aItemView
								.setOnItemClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										if (a.getHassub() == 1) {

										} else {
											boolean flag = aItemView
													.getCheckedStatus();
//											clearCheckedStatus();
											aItemView.setCheckedStatus(!flag);
											if (!flag) {
												code = a.getCode();
												value= a.getValue();
//												startIntent();
//												finish();
												if (isFull()) {
													aItemView.setCheckedStatus(false);
													CustomMessage.showToast(context, "最多只能选5个!", 0);
												}else{
													createTextView(a);
													checkedStatus(a.getCode());
													checkedLocalAreaState(a.getCode());
												}
												scroll_page_sub.setAnimation(animation_out);
												scroll_page_sub.startAnimation(animation_out);
											} else {
												code = "";
												value = "";
												removeTextView(a);
											}
										}
									}

								});
						allViews.add(count+i,aItemView);
						linear_sub.addView(allViews.get(count+i));
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
				count=nn;
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
		this.setResult(requst);
		finish();
	}
	
	/**
	 * 格式化数据
	 */
	private void formatDatas(){
		if (map_selectedArea!=null&&map_selectedArea.getSize()>0) {
			HashMap<String, Areas> map_a=map_selectedArea.getHashMap();
			int nn=map_a.size();
			List<ResumeIntentionDesiredLocation> locations=new ArrayList<ResumeIntentionDesiredLocation>(nn);
			ResumeIntentionDesiredLocation location=null;
			for (Map.Entry<String, Areas> item : map_a.entrySet()) {
				if (item!=null) {
					location=new ResumeIntentionDesiredLocation();
					location.location_code=item.getKey();
					location.location_value=item.getValue().getValue();
					locations.add(location);
				}
			}
			JobFavoriteActivity.desiredLocations=locations;
		}else {
			
//			JobFavoriteActivity.desiredLocations=null;
		}
	}
	
	private OnTouchListener touchListener=new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			int y=0;
			int sy=0;
			switch (event.getAction()) { 
            case MotionEvent.ACTION_MOVE: 
                y=(int) event.getY();
                if (Math.abs(y-sy)>20&&(linear_selected.getVisibility()==View.VISIBLE)) {
                	hand.sendEmptyMessageDelayed(0, 20); 
				}
                break; 
            case MotionEvent.ACTION_DOWN: 
                sy=y=(int) event.getY();
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
