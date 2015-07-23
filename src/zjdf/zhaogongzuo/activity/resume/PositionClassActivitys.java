/**
 * Copyright © 2014年3月21日 FindJob www.veryeast.com
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

import java.util.List;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.more.SubscribeActivity;
import zjdf.zhaogongzuo.animation.AnimationBox;
import zjdf.zhaogongzuo.controllers.PositionClassifyController;
import zjdf.zhaogongzuo.entity.PositionClassify;
import zjdf.zhaogongzuo.fragments.SearchFragment;
import zjdf.zhaogongzuo.ui.PositionClassItemView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.umeng.analytics.MobclickAgent;

/**
 *<h2> PositionClassActivity</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年3月21日
 * @version 
 * @modify 
 * 
 */
public class PositionClassActivitys extends Activity {

	
	private Context context;
	/**职位分类编码*/
	public static String code="";
	/**职位分类名称*/
	public static String value = "";
	/**返回*/
	private ImageButton goback;
	/**提交*/
	private Button btn_submit;
	/**主页面*/
	private ScrollView scroll_page_main;
	/**子页面*/
	private ScrollView scroll_page_sub;
	/**主页面容器*/
	private LinearLayout linear_main;
	/**子页面容器*/
	private LinearLayout linear_sub;
	
	/**进入动画*/
	private Animation animation_in;
	/**退出动画*/
	private Animation animation_out;
	
	/**当前显示的是否是子页面*/
	private boolean isSub=false;
	/**返回按钮*/
		
	/**职位分类编码*/
	public static final String POSITION_CODES="position_code";
	/**职位分类名称*/
	public static final String POSITION_VALUES="position_value";
	
	private LinearLayout.LayoutParams params;
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search_position_classs);
		context=this;
		initView();
		initAnimations();
		initDatas();
	}
	
	private void initView(){
		goback=(ImageButton)findViewById(R.id.goback);
		btn_submit=(Button)findViewById(R.id.btn_submit);
		scroll_page_main=(ScrollView)findViewById(R.id.scroll_page_main);
		scroll_page_sub=(ScrollView)findViewById(R.id.scroll_page_sub);
		linear_main=(LinearLayout)findViewById(R.id.linear_main);
		linear_sub=(LinearLayout)findViewById(R.id.linear_sub);
		scroll_page_main.setVisibility(View.VISIBLE);
		scroll_page_sub.setVisibility(View.GONE);
		params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.height = 1;
		params.leftMargin = 40;
		goback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				code="";
				value="不限";
				Intent intent = new Intent();
				intent.putExtra(POSITION_CODES, code);
				intent.putExtra(POSITION_VALUES, value);
				((Activity)context).setResult(SubscribeActivity.REQUESTCODE_POSITIONCLASS, intent);
				finish();
			}
		});
	}
	
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
	
	private void initDatas(){
		code=SearchFragment.positionsClassCode;
		value=SearchFragment.positionsClassValue;
		if (PositionClassifyController.positionClassifies!=null&&PositionClassifyController.positionClassifies.size()>0) {
			int n=PositionClassifyController.positionClassifies.size();
			for (int i = 0; i < n; i++) {
				final PositionClassify positionClassify=PositionClassifyController.positionClassifies.get(i);
				final PositionClassItemView positionView=new PositionClassItemView(context);
				positionView.setPositionClassify(positionClassify);
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
								value= positionClassify.getValue();
								startIntent();
								finish();
							} else {
								code = "";
								value = "";
							}
						}
					}
				});
				linear_main.addView(positionView);
				if (i < n - 1) {
					View line = LayoutInflater.from(context).inflate(
							R.layout.layout_line, null);
					linear_main.addView(line, params);
				}else {
					params = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					params.height = 1;
					params.leftMargin = 1;
					params.bottomMargin=10;
					View line = LayoutInflater.from(context).inflate(
							R.layout.layout_line, null);
					linear_main.addView(line, params);
				}
			}
		}
	}
	
	
	/**
	 * 加载子控件
	 *<pre>方法  </pre>
	 * @param area 地区
	 */
	private void showSubAddressViews(PositionClassify positionClassify) {
		params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.height = 1;
		params.leftMargin = 40;
		if (null!=linear_sub&&linear_sub.getChildCount()>0) {
			linear_sub.removeAllViews();
		}
		if (positionClassify != null) {
			List<PositionClassify> pList = positionClassify.getSubList();
			if (pList != null && pList.size() > 0) {
				if (!pList.get(0).getCode().equals(positionClassify.getCode())) {
					PositionClassify pp=new PositionClassify();
					pp.setCode(positionClassify.getCode());
					pp.setPcode(positionClassify.getPcode());
					pp.setHasSub(false);
					pp.setSubList(null);
					pp.setValue(positionClassify.getValue());
					pList.add(0, pp);
				}
				
				int nn = pList.size();
				for (int i = 0; i < nn; i++) {
					final PositionClassify p = pList.get(i);
					if (p != null) { 
						final PositionClassItemView itemView = new PositionClassItemView( context );
						itemView.setPositionClassify(p);
						itemView.setOnItemClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										if (p.isHasSub()) {

										} else {
											boolean flag = itemView.getCheckedStatus();
											itemView.setCheckedStatus(!flag);
											if (!flag) {
												code = p.getCode();
												value= p.getValue();
												startIntent();
												finish();
											} else {
												code = "";
												value = "";
											}
										}
									}

								});
						linear_sub.addView(itemView);
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
		intent.putExtra(POSITION_CODES, code);
		intent.putExtra(POSITION_VALUES, value);
		this.setResult(SubscribeActivity.REQUESTCODE_POSITIONCLASS, intent);
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
