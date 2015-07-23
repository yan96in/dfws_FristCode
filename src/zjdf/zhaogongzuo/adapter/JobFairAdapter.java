/**
 * Copyright © 2014年3月28日 FindJob www.veryeast.com
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
import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.controllers.PersonalController;
import zjdf.zhaogongzuo.entity.JobFair;
import zjdf.zhaogongzuo.net.ImageLoader;
import zjdf.zhaogongzuo.utils.MD5Utils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * <h2>JobFairAdapter招聘会适配器</h2>
 * 
 * <pre> </pre>
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年3月28日
 * @version
 * @modify
 * 
 */
public class JobFairAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	/** 招聘会集合 */
	private List<JobFair> jobFairs;
	private ListView listView;
	/**图片下载*/
	private ImageLoader imageLoader;

	public JobFairAdapter(Context context, List<JobFair> jobFairs,
			ListView listView) {
		if (jobFairs == null) {
			this.jobFairs = new ArrayList<JobFair>();
		} else {
			this.jobFairs = jobFairs;
		}
		this.context = context;
		this.listView = listView;
		inflater = LayoutInflater.from(context);
		imageLoader=new ImageLoader(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int count = 0;
		if (jobFairs != null) {
			count = jobFairs.size();
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (jobFairs != null) {
			return jobFairs.get(position);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	//获取ID
	public int getItemObjectId(int position) {
		return jobFairs.get(position).getId();
	}
	
	public void addDatas(List<JobFair> list) {
		if (list!=null&&list.size()>0) {
			jobFairs.addAll(list);
			notifyDataSetChanged();
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.layout_mycenter_jobfair_item, null);
			holder = new ViewHolder();
			holder.iv_jobfair_pic = (ImageView) convertView.findViewById(R.id.iv_jobfair_pic);
			holder.txt_jobfair_address = (TextView) convertView.findViewById(R.id.txt_jobfair_address);
			holder.txt_jobfair_date = (TextView) convertView.findViewById(R.id.txt_jobfair_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (jobFairs != null && jobFairs.size() > 0) {
			JobFair jfair = jobFairs.get(position);
			if (jfair!=null) {
				holder.txt_jobfair_address.setText(jfair.getAddress());
				holder.txt_jobfair_date.setText(jfair.getDatetime());
				String url = jfair.getImageUrl();
				holder.iv_jobfair_pic.setTag(url);
				String name=MD5Utils.MD5(url);
				Bitmap bitmap=imageLoader.loadImage("JobFairAdapter", url, FrameConfigures.FOLDER_IMG, name, new ImageLoader.Callback() {
					
					@Override
					public void imageLoaded(String path, String names, Bitmap bp) {
						// TODO Auto-generated method stub
						if (!StringUtils.isEmpty(path)&&!StringUtils.isEmpty(names)&&bp!=null) {
							ImageView iView=(ImageView)listView.findViewWithTag(path);
							if (iView!=null) {
								iView.setImageBitmap(bp);
							}
						}
					}
				});
				if (bitmap==null) {
					bitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_jobfair_loading);
				}
				holder.iv_jobfair_pic.setImageBitmap(bitmap);
			}
		}
		return convertView;
	}

	private class ViewHolder {
		/** 宣传图 */
		public ImageView iv_jobfair_pic;
		/** 时间 */
		public TextView txt_jobfair_date;
		/** 地区 */
		public TextView txt_jobfair_address;
	}

}
