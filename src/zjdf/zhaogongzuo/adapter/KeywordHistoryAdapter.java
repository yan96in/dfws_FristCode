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
package zjdf.zhaogongzuo.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.entity.Areas;
import zjdf.zhaogongzuo.entity.Keywords;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 *<h2>职位搜索--关键字搜索--历史记录</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年3月20日
 * @version 
 * @modify 
 * 
 */
public class KeywordHistoryAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<Keywords> keywords;
	private String key;
	private boolean isKeyword=false;
	/**
	 * 
	 * @param context
	 * @param keywords 关键字联想集合
	 * @param key 关键字
	 * @param iskwd 是否关键字联想
	 */
	public KeywordHistoryAdapter(Context context,List<Keywords> keywords,String key,boolean iskwd){
		if (keywords!=null) {
			this.keywords=keywords;
			
		}else {
			this.keywords=new ArrayList<Keywords>();
		}
		this.context=context;
		inflater=LayoutInflater.from(context);
		this.key=key;
		isKeyword=iskwd;
		removeDuplicate();
		sort();
	}
	
	/**
	 * 按总数排序
	 */
	private void sort(){
		if (keywords!=null)
			Collections.sort(this.keywords, new Comparator<Keywords>() {
	
				@Override
				public int compare(Keywords lhs,  Keywords rhs) {
					// TODO Auto-generated method stub
					return Integer.valueOf(rhs.getCount()).compareTo(Integer.valueOf(lhs.getCount()));
				}
			});
	}
	
	/**
	 * 倒转集合顺序
	 *<pre>倒序  </pre>
	 */
	public void reverse() {
		if (keywords!=null&&keywords.size()>0) {
			Collections.reverse(keywords);
			notifyDataSetChanged();
		}
	}
	
	/**
	 * 清除数据
	 *<pre>方法  </pre>
	 */
	public void clear() {
		if (keywords!=null&&keywords.size()>0) {
			keywords.clear();
			notifyDataSetChanged();
		}
	}
	
	/**
	 * 获取选中的内容
	 *<pre>方法  </pre>
	 * @param position 选中的位置
	 * @return
	 */
	public String getContext(int position){
		if (keywords!=null&&keywords.size()>0) {
			return keywords.get(position).getKeywords();
		}
		return null;
	}
	
	/**
	 * 更新数据
	 *<pre>方法  </pre>
	 * @param list
	 * @param iskwd 是否关键字联想的
	 */
	public void setDatas(List<Keywords> list,boolean iskwd){
		if (keywords!=null&&list!=null) {
			keywords.clear();
			keywords.addAll(list);
			isKeyword=iskwd;
			removeDuplicate();
			sort();
			notifyDataSetChanged();
		}
	}
	
	/**
	 * 设置关键字
	 *<pre>方法  </pre>
	 * @param keyword 关键字
	 */
	public void setKeyword(String keyword){
		key=keyword;
	}
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return keywords.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Keywords getItem(int position) {
		// TODO Auto-generated method stub
		return keywords.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHoder hoder=null;
		if (convertView==null) {
			hoder=new ViewHoder();
			convertView=inflater.inflate(R.layout.layout_keyword_history_item, null);
			hoder.txt_position=(TextView)convertView.findViewById(R.id.txt_keyword_history_title);
			hoder.txt_keyword_history_num=(TextView)convertView.findViewById(R.id.txt_keyword_history_num);
			convertView.setTag(hoder);
		}else {
			hoder=(ViewHoder)convertView.getTag();
		}
		if (keywords!=null&&keywords.size()>0) {
			Keywords keyword=keywords.get(position);
			if (keyword!=null) {
				String keys=keyword.getKeywords();
				Log.i("MMM", "keys= "+keys);
				Log.i("MMM", "key= "+key);
				if (!StringUtils.isEmpty(key)&&!StringUtils.isEmpty(keys)&&keys.contains(key)) {
					keys=keys.replace(key, "<font color=\"red\">"+key+"</font>");
					Log.i("MMM", "keys= "+keys);
				}
				hoder.txt_position.setText(Html.fromHtml(keys));
				String counts="";
				if (isKeyword) {
					counts=keyword.getCount()+"条记录";
				}else {
					counts="共搜索"+keyword.getCount()+"次";
				}
				hoder.txt_keyword_history_num.setText(counts);
			}
		}
		return convertView;
	}
	
	private class ViewHoder{
		public TextView txt_position;
		public TextView txt_keyword_history_num;
	}
	
	/**
	 * 去除重复项
	 */
	private void removeDuplicate()      
	{      
		HashSet<Keywords> h = new HashSet<Keywords>(keywords);
		keywords.clear();      
		keywords.addAll(h);      
	}     

}
