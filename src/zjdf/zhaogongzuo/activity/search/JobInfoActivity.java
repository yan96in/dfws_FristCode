/**
 * Copyright © 2014年4月29日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.activity.search;

import java.util.List;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.adapter.JobInfoPageAdapter;
import zjdf.zhaogongzuo.controllers.PositionController;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.ui.CustomPoPuDialog;
import zjdf.zhaogongzuo.ui.CustomViewPager;
import zjdf.zhaogongzuo.ui.JobInfoItemView;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 *<h2> JobInfoActivity</h2>
 *<pre> 职位详细页 </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月29日
 * @version 
 * @modify 
 * 
 */
public class JobInfoActivity extends Activity {

	
	private Context context;
	/**主容器*/
	private ViewGroup mainView;
	/**页面容器*/
	private CustomViewPager viewPager;
	/**页面适配器*/
	private JobInfoPageAdapter pageAdapter;
	/**视图集合*/
	private List<JobInfoItemView> jobInfoItemViews;
	/**返回*/
	private ImageButton goback;
	/**更多*/
	private Button more;
	/**标题*/
	private TextView title;
	/**右滑*/
	private boolean isRight=false;
	/**左滑*/
	private boolean isLeft=false;
	/**第一页*/
	private int firstPage=0;
	/**前一页*/
	private int previous=0;
	/**后一页*/
	private int next=0;
	/**总的页数*/
	private int pageCount=0;
	/**当前页面*/
	private int currentPage=0;
	/**来源*/
	private String fromTag=PositionListActivity.TAG;
	private PopupWindow report_pop;
	private Button btn_report;
	/**当前的职位*/
	private Position mPosition;
	/**翻页对话框*/
	private Dialog dialog;
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context=this;
		fromTag=getIntent().getStringExtra("tags");
		currentPage=getIntent().getIntExtra("position", 0);
		initView();
		initDatas();
		gotoView(currentPage);
	}
	
	private void initView(){
		mainView=(ViewGroup)getLayoutInflater().inflate(R.layout.layout_jobinfo, null);
		goback=(ImageButton)mainView.findViewById(R.id.goback);
		more=(Button)mainView.findViewById(R.id.more);
		title=(TextView)mainView.findViewById(R.id.title);
		viewPager=(CustomViewPager)mainView.findViewById(R.id.viewpager);
		goback.setOnClickListener(listener);
		more.setOnClickListener(listener);
		viewPager.setOnPageChangeCallback(pageChangeListener);
		viewPager.setDirectionListener(new CustomViewPager.DirectionListener() {
			
			@Override
			public void ontouch() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void direction(boolean left, boolean right) {
				// TODO Auto-generated method stub
				isLeft=left;
				isRight=right;
				if (right&&currentPage==0) {
					CustomMessage.showToast(context, "已经是第一个职位了！", 0);
				}
				if (left&&currentPage==pageAdapter.getCount()-1) {
					if (PositionController.jobCounts>0&&PositionController.jobCounts>pageAdapter.getCount()&&PositionListActivity.adapter!=null) {
						if (!NetWorkUtils.checkNetWork(context)) {
							CustomMessage.showToast(context, "网络不稳定，请检查！", 0);
							return;
						}
						if (dialog!=null&&!dialog.isShowing()) {
							dialog.show();
						}
						PositionListActivity.loadNextPage(new PositionListActivity.LoadNextPageListener() {
							
							@Override
							public void loadFinish() {
								// TODO Auto-generated method stub
								List<JobInfoItemView> itemViews=PositionListActivity.adapter.getJobInfoAddItemViews(context);
								pageAdapter.addItems(itemViews);
								viewPager.setCurrentItem(currentPage);
								if (dialog!=null&&dialog.isShowing()) {
									dialog.dismiss();
								}
							}
							
							@Override
							public void loadEorror() {
								// TODO Auto-generated method stub
								if (dialog!=null&&dialog.isShowing()) {
									dialog.dismiss();;
								}
							}
						});
					}else if (PositionController.jobCounts==pageAdapter.getCount()) {
						CustomMessage.showToast(context, "已经是最后一个职位了！", 0);
						return;
					}
				}
			}
		});
		View view=getLayoutInflater().inflate(R.layout.layout_report_pop_bg, null);
		btn_report=(Button)view.findViewById(R.id.btn_report);
		btn_report.setOnClickListener(listener);
		report_pop=CustomPoPuDialog.createPopupWindow(context, view, R.drawable.shap_pop_bg, R.style.pop_anim_style);
		dialog=CustomMessage.createProgressDialog(context, "正在加载下一页...", true,12);
		setContentView(mainView);
	}
	
	private void initDatas(){
		if (fromTag.equalsIgnoreCase(PositionListActivity.TAG)) {
			jobInfoItemViews=PositionListActivity.adapter.getJobInfoItemViews(context);
			mPosition=PositionListActivity.adapter.getItem(currentPage);
		}
	}
	
	/**
	 * setup first page
	 * @param page first page
	 */
	private void gotoView(int page){
		viewPager.removeAllViews();
		pageAdapter=new JobInfoPageAdapter(context, jobInfoItemViews);
		viewPager.setAdapter(pageAdapter);
		viewPager.setCurrentItem(page,false);
	}
	
	private View.OnClickListener listener=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (v==goback) {
				finish();
			}
			if (v==more) {
				if (report_pop!=null&&!report_pop.isShowing()) {
					report_pop.showAsDropDown(more, -more.getWidth()/2, 0);
				}
			}
			if (v==btn_report) {
				if (mPosition==null||StringUtils.isEmpty(mPosition.getIdStr())) {
					CustomMessage.showToast(context, "职位不能为空！", 0);
					return;
				}
				Intent intent=new Intent(context, JobReportActivity.class);
				intent.putExtra("ids", mPosition.getIdStr());
				startActivity(intent);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				if (report_pop.isShowing()) {
					report_pop.dismiss();
				}
			}
		}
	};
	
	private CustomViewPager.OnPageChangeCallback pageChangeListener = new CustomViewPager.OnPageChangeCallback() {
		
		@Override
		public void onCurrentPage(int index) {
			// TODO Auto-generated method stub
			currentPage=index;
			mPosition=PositionListActivity.adapter.getItem(currentPage);
		}
		
		@Override
		public void changeView(boolean left, boolean right) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void OnPageStateChanged(int flag) {
			// TODO Auto-generated method stub
			
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
