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
package zjdf.zhaogongzuo.activity.options;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import android.app.Activity;
import android.content.Context;
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
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.controllers.PositionClassifyController;
import zjdf.zhaogongzuo.entity.OptionEntity;
import zjdf.zhaogongzuo.entity.PositionClassify;
import zjdf.zhaogongzuo.entity.ResumeIntentionDesiredCompanyType;
import zjdf.zhaogongzuo.entity.ResumeIntentionDesiredPosition;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.ui.IndustryItemView;
import zjdf.zhaogongzuo.ui.PositionClassItemView;
import zjdf.zhaogongzuo.utils.MyHashMap;
import zjdf.zhaogongzuo.utils.StringUtils;

/**
 * 意向企业多选
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月13日
 * @version v1.0.0
 * @modify 
 */
public class OptIndustryActivity extends Activity{

	private Context context;
	/**职位分类编码*/
	public static String code="";
	/**职位分类名称*/
	public static String value = "";
	/**返回*/
	private ImageButton goback;
	/**主页面*/
	private ScrollView scroll_page_main;
	/**子页面*/
	private ScrollView scroll_page_sub;
	/**主页面容器*/
	private LinearLayout linear_main;
	/**子页面容器*/
	private LinearLayout linear_sub;
	/**已选行业*/
	private RelativeLayout rela_selected;
	/**已选行业容器*/
	private LinearLayout linear_selected;
	/**已选行业数量*/
	private TextView txt_selected_num;
	/**所有分类控件*/
	private List<IndustryItemView> allViews;
	/**已选*/
	private MyHashMap<String,OptionEntity> map_selectedOpt;
	/**已选*/
	private MyHashMap<String,TextView> map_selectedViews;
	
	/**进入动画*/
	private Animation animation_in;
	/**退出动画*/
	private Animation animation_out;
	
	/**当前显示的是否是子页面*/
	private boolean isSub=false;
	/**返回按钮*/
		
	/**职位分类编码*/
	public static final String POSITION_CODE="position_code";
	/**职位分类名称*/
	public static final String POSITION_VALUE="position_value";
	
	private LinearLayout.LayoutParams params;
	/**不限*/
	private Button save;
	/**已选地点图标*/
	private Drawable rightDrawable_A;
	/**已选地点图标*/
	private Drawable rightDrawable_V;
	private LinearLayout.LayoutParams params_select;
	/**请求路径*/
	private int requst;
	private int count = 0;
	private TextView title;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_opt_position_mutilselected);
		context=this;
		requst=getIntent().getIntExtra("request", 0);
		initView();
		initAnimations();
		initDatas();	
	}
	
	private void initView(){
		goback=(ImageButton)findViewById(R.id.goback);
		scroll_page_main=(ScrollView)findViewById(R.id.scroll_page_main);
		scroll_page_sub=(ScrollView)findViewById(R.id.scroll_page_sub);
		linear_main=(LinearLayout)findViewById(R.id.linear_main);
		linear_sub=(LinearLayout)findViewById(R.id.linear_sub);
		scroll_page_main.setVisibility(View.VISIBLE);
		scroll_page_sub.setVisibility(View.GONE);
		rela_selected=(RelativeLayout)findViewById(R.id.rela_selected);
		linear_selected=(LinearLayout)findViewById(R.id.linear_selected);
		linear_selected.setVisibility(View.GONE);
		txt_selected_num=(TextView)findViewById(R.id.txt_selected_num);
		goback=(ImageButton)findViewById(R.id.goback);
		save=(Button)findViewById(R.id.btn_save);
		title=(TextView)findViewById(R.id.title);
		rightDrawable_A=getResources().getDrawable(R.drawable.ic_select_up);
		rightDrawable_A.setBounds(0, 0, rightDrawable_A.getMinimumWidth(), rightDrawable_A.getMinimumHeight());
		rightDrawable_V=getResources().getDrawable(R.drawable.ic_select_up);
		rightDrawable_V.setBounds(0, 0, rightDrawable_V.getMinimumWidth(), rightDrawable_V.getMinimumHeight());
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
		params_select.topMargin=3;
		params_select.bottomMargin=3;
		params_select.leftMargin=10;
		scroll_page_main.setOnTouchListener(touchListener);
		scroll_page_sub.setOnTouchListener(touchListener);
		title.setText(R.string.myresume_intention_industry);
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
				if (map_selectedOpt==null||map_selectedOpt.getSize()<1) {
					CustomMessage.showToast(context, "请至少选择一个意向企业！", Gravity.CENTER, 0);
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
	 * 初始化被选中的地区
	 */
	private void initSelected(){
		if (JobFavoriteActivity.desiredCompanyTypes!=null&&JobFavoriteActivity.desiredCompanyTypes.size()>0) {
			for (ResumeIntentionDesiredCompanyType item : JobFavoriteActivity.desiredCompanyTypes) {
				if (item!=null) {
					OptionEntity oe=FrameConfigures.map_industry.get(item.industry_code);
					createTextView(oe);
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
			txt_selected_num.setCompoundDrawables(null, null, rightDrawable_A, null);
			linear_selected.setVisibility(View.VISIBLE);
		}else {
			txt_selected_num.setCompoundDrawables(null, null, rightDrawable_V, null);
			linear_selected.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 是否选满了
	 * @return
	 */
	private boolean isFull(){
		if(map_selectedOpt!=null&&map_selectedOpt.getSize()>4){
			return true;
		}
		return false;
	}
	
	/**
	 * 创建以选中地址
	 * @param a
	 */
	private void createTextView(final OptionEntity p){
		if (p==null||map_selectedOpt==null) {
			return;
		}
		if (isFull()) {
			return;
		}
		final String code=p.code;
		if(map_selectedOpt.containKey(code)){
			return;
		}
		final TextView aView=(TextView)getLayoutInflater().inflate(R.layout.layout_textview, null);
		aView.setText(p.value);
		aView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				removeTextView(p);
				clearCheckedStatus(code);
			}
		});
		map_selectedOpt.addData(code, p);
		map_selectedViews.addData(code, aView);
		linear_selected.addView(map_selectedViews.getData(code),params_select);
		txt_selected_num.setText(map_selectedOpt.getSize()+"/5");
		return;
	}
	
	/**
	 * 移除TextView
	 * @param a
	 */
	private void removeTextView(OptionEntity p){
		if (p==null||map_selectedOpt==null||map_selectedViews==null) {
			return;
		}
		final String code=p.code;
		map_selectedOpt.removeData(code);
		linear_selected.removeView(map_selectedViews.removeData(code));
		txt_selected_num.setText(map_selectedOpt.getSize()+"/5");
		if (map_selectedOpt.getSize()==0) {
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
		for (IndustryItemView itemView : allViews) {
			if (itemView!=null) {
				if (code.equals(itemView.getCode())) {
					itemView.setCheckedStatus(false);
				}
			}
		}
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
		map_selectedOpt=new MyHashMap<String, OptionEntity>(5);
		map_selectedViews=new MyHashMap<String, TextView>(5);
		allViews = new ArrayList<IndustryItemView>();
		initSelected();
		if (FrameConfigures.list_industry!=null&&FrameConfigures.list_industry.size()>0) {
			int n=FrameConfigures.list_industry.size();
			count=count+n;
			for (int i = 0; i < n; i++) {
				final OptionEntity opt=FrameConfigures.list_industry.get(i);
				final IndustryItemView itemView=new IndustryItemView(context);
				itemView.setOptEntity(opt,map_selectedOpt.containKey(opt.code)&opt.hassub!=1);
				itemView.setOnItemClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (opt.hassub==1) {
							showSubAddressViews(opt);
						} else {
							boolean flag = itemView.getCheckedStatus();
							itemView.setCheckedStatus(!flag);
							if (!flag) {
								code = opt.code;
								value= opt.value;
								if (isFull()) {
									itemView.setCheckedStatus(false);
									CustomMessage.showToast(context, "最多只能选5个!", 0);
								}else
									createTextView(opt);
							} else {
								code = "";
								value = "";
								removeTextView(opt);
							}
						}
					}
				});
				allViews.add(i,itemView);
				linear_main.addView(allViews.get(i));
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
	private void showSubAddressViews(OptionEntity opt) {
		params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.height = 1;
		params.leftMargin = 40;
		if (null!=linear_sub&&linear_sub.getChildCount()>0) {
			linear_sub.removeAllViews();
		}
		if (opt != null) {
			List<OptionEntity> pList = opt.subEntities;
			if (pList != null && pList.size() > 0) {
				int nn = pList.size();
				if (!pList.get(0).code.equals(opt.code)) {
					OptionEntity oo=new OptionEntity();
					oo.code=opt.code;
					oo.pcode=opt.pcode;
					oo.hassub=0;
					oo.subEntities=null;
					oo.value=opt.value;
					pList.add(0, oo);
				}
				for (int i = 0; i < nn; i++) {
					final OptionEntity p = pList.get(i);
					if (p != null) { 
						final IndustryItemView itemView = new IndustryItemView( context );
						itemView.setOptEntity(p,map_selectedOpt.containKey(p.code));
						itemView.setOnItemClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										if (p.hassub==1) {

										} else {
											boolean flag = itemView.getCheckedStatus();
											itemView.setCheckedStatus(!flag);
											if (!flag) {
												code = p.code;
												value= p.value;
//												startIntent();
//												finish();
												if (isFull()) {
													itemView.setCheckedStatus(false);
													CustomMessage.showToast(context, "最多只能选5个!", 0);
												}else
													createTextView(p);
												scroll_page_sub.setAnimation(animation_out);
												scroll_page_sub.startAnimation(animation_out);
											} else {
												code = "";
												value = "";
												removeTextView(p);
											}
										}
									}

								});
						allViews.add(count+i,itemView);
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
		if (map_selectedOpt!=null&&map_selectedOpt.getSize()>0) {
			HashMap<String, OptionEntity> map_p=map_selectedOpt.getHashMap();
			int nn=map_p.size();
			List<ResumeIntentionDesiredCompanyType> companyTypes=new ArrayList<ResumeIntentionDesiredCompanyType>(nn);
			ResumeIntentionDesiredCompanyType companyType=null;
			for (Map.Entry<String, OptionEntity> item : map_p.entrySet()) {
				if (item!=null) {
					companyType=new ResumeIntentionDesiredCompanyType();
					companyType.industry_code=item.getKey();
					companyType.industry_value=item.getValue().value;
					if (StringUtils.isEmpty(item.getValue().pcode)||"null".equalsIgnoreCase(item.getValue().pcode)) {
						companyType.parent_industry_code=item.getKey();
					}else
						companyType.parent_industry_code=item.getValue().pcode;
					companyTypes.add(companyType);
				}
			}
			JobFavoriteActivity.desiredCompanyTypes=companyTypes;
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
