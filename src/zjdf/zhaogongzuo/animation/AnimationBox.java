/**
 * Copyright © 2014年4月14日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ScrollView;

/**
 *<h2> 常用动画工具类</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月14日
 * @version 
 * @modify 
 * 
 */
public class AnimationBox {

	
	private AnimationBox(){
		
	}
	
	/**
	 * 给控件绑定动画
	 *<pre>方法  </pre>
	 * @param context
	 * @param ares 动画的资源文件
	 * @param view 要绑定动画的控件
	 * @param listener 动画过程监听
	 * @return
	 */
	public static Animation bindAnimation(Context context,int ares,AnimationListener listener){
		Animation animation=AnimationUtils.loadAnimation(context, ares);
		animation.setAnimationListener(listener);
		animation.setFillAfter(true);
		return animation;
	}
}
