/**
 * Copyright © 2014年4月30日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.adapter;

import java.util.ArrayList;
import java.util.List;

import zjdf.zhaogongzuo.activity.search.PositionListActivity;
import zjdf.zhaogongzuo.ui.JobInfoItemView;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 职位详情适配器
 * @author Eilin.Yang  VearyEast 
 * @since 2014年4月30日
 * @version v1.0.0
 * @modify 
 */
public class JobInfoPageAdapter extends PagerAdapter {

	
	/**TAG*/
	private String TAG="JobInfoPageAdapter";
	/**上下文*/
	private Context context;
	/**视图集*/
	private List<JobInfoItemView> jobInfoItemViews;
	/**视图填充器*/
	private LayoutInflater inflater;
	
	/**
	 * 构造函数
	 * @param conte
	 * @param itemViews 视图集
	 */
	public JobInfoPageAdapter(Context context,List<JobInfoItemView> itemViews) {
		if (itemViews==null) {
			jobInfoItemViews=new ArrayList<JobInfoItemView>();
		}else {
			jobInfoItemViews=itemViews;
		}
		this.context=context;
		inflater=LayoutInflater.from(context);
	}
	
	/**
	 * 获取职位详情页面
	 * @param position 页面位置
	 * @return
	 */
	public JobInfoItemView getItemView(int position) {
		if (jobInfoItemViews!=null&&jobInfoItemViews.size()>0) {
			return jobInfoItemViews.get(position);
		}
		return null;
	}
	
	/**
	 * 修改View集合
	 * @param position 位置
	 * @param itemView 控件
	 */
	public void setItemView(int position, JobInfoItemView itemView) {
		if (jobInfoItemViews==null) {
			return;
		}
		jobInfoItemViews.set(position, itemView);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jobInfoItemViews.size();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View, java.lang.Object)
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg1 == arg0;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getItemPosition(java.lang.Object)
	 */
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#destroyItem(android.view.ViewGroup, int, java.lang.Object)
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		
		((ViewPager) container).removeView(jobInfoItemViews.get(position));
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#instantiateItem(android.view.ViewGroup, int)
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		JobInfoItemView item=jobInfoItemViews.get(position);
		item.start();
		((ViewPager) container).addView(item);
		jobInfoItemViews.set(position, item);
		return jobInfoItemViews.get(position);
	}
	
	/**
	 * 添加view
	 * @param position 位置
	 * @param itemView 职位界面
	 */
	public void addItem(int position, JobInfoItemView itemView) {
		if (jobInfoItemViews==null) {
			return;
		}
		jobInfoItemViews.add(itemView);
		notifyDataSetChanged();
	}
	
	/**
     * 添加view
     * @param readingViews a new list
     */
    public void addItems(List<JobInfoItemView> itemViews) {
		if (jobInfoItemViews!=null) {
			jobInfoItemViews.addAll(itemViews);
			notifyDataSetChanged();
		}
	}
}
