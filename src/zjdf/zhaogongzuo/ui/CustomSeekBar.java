/**
 * Copyright © 2014年3月25日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.ui;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 *<h2> CustomSeekBar</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年3月25日
 * @version 
 * @modify 
 * 
 */
public class CustomSeekBar extends FrameLayout {

	private Context context;
	private ProgressBar mSeekBar;
	private TextView presstitle;

	public CustomSeekBar(Context context) {
		this(context, null);
	}

	public CustomSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.layout_custom_seekbar,
				this, true);
		mSeekBar = (ProgressBar) findViewById(R.id.seekbar);
		presstitle = (TextView) findViewById(R.id.presstitle);
		mSeekBar.setFocusableInTouchMode(false);
		mSeekBar.setFocusable(false);
		mSeekBar.setClickable(false);
		
	}
	
	/**
	 * <pre>
	 * 设置进度值
	 * </pre>
	 * 
	 * @param progress
	 *            进度值
	 */
	public void setProgress(int progress) {
		if (mSeekBar != null) {
			mSeekBar.setProgress(progress);
		}
		setProgressTitle(progress+"%");
	}

	/**
	 * <pre>
	 * 设置进度
	 * </pre>
	 * 
	 * @param str
	 *            如：20%
	 */
	public void setProgressTitle(String str) {
		if (presstitle != null && !StringUtils.isEmpty(str)) {
			presstitle.setText(str);
		}
	}
}
