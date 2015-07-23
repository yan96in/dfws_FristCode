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
package zjdf.zhaogongzuo.utils;

import java.util.HashMap;

/**
 * 限制大小的HashMap
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月13日
 * @version v1.0.0
 * @modify 
 */
public class MyHashMap<K,V> {

	private HashMap<K, V> table;
	private int mSize=0;
	
	/**
	 * 此hashmap将限定大小，最大值不超过size
	 * @param size 最大大小
	 */
	public MyHashMap(int size) {
		mSize=(size<0?0:size);
		table=new HashMap<K, V>(mSize);
	}
	
	/**
	 * 添加数据
	 * @param key
	 * @param value
	 */
	public void addData(K key, V value){
		if (table==null||key==null) {
			return;
		}
		int n=table.size();
		if (n>mSize-1||table.containsKey(key)) {
			return ;
		}
		table.put(key, value);
		
	}
	
	/**
	 * 获取数据
	 */
	public V getData(Object key) {
		if (table==null||table.isEmpty()||key==null) {
			return null;
		}
		return table.get(key);
	}
	
	/**
	 * 移除数据
	 */
	public V removeData(Object key) {
		if (table==null||table.isEmpty()) {
			return null;
		}
		return table.remove(key);
	}
	
	/**
	 * 是否包含某个键
	 * @param key
	 * @return
	 */
	public boolean containKey(Object key) {
		if (table==null||table.isEmpty()) {
			return false;
		}
		return table.containsKey(key);
	}
	
	/**
	 * 清除所有数据
	 */
	public void clearData() {
		if (table==null) {
			return;
		}
		table.clear();
	}
	
	/**
	 * 获取大小
	 * @return
	 */
	public int getSize(){
		return table.size();
	}
	
	/**
	 * 获取数据集
	 * @return
	 */
	public HashMap<K, V> getHashMap(){
		return table;
	}
}
