package zjdf.zhaogongzuo.activity.more;

import java.util.ArrayList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import zjdf.zhaogongzuo.adapter.GuideAdapter;
import zjdf.zhaogongzuo.ui.CustomViewPager;
import zjdf.zhaogongzuo.ui.CustomViewPager.DirectionListener;

/**
 * 欢迎界面
 * 
 * @author Administrator
 * 
 */
public class GuideActivity extends Activity implements OnPageChangeListener {
	private CustomViewPager vp;
	private GuideAdapter vpAdapter;// 图片适配器
	private List<View> views;// 集合
	private ImageView[] dots;
	// 默认
	private int currentIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_more_job_guide);

		initViews();
		initDots();
	}

	// 初始化数据
	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);
		views = new ArrayList<View>();
		View view5 = inflater.inflate(R.layout.layout_more_job_guide_5, null);
		//
		views.add(inflater.inflate(R.layout.layout_more_job_guide_1, null));
		views.add(inflater.inflate(R.layout.layout_more_job_guide_2, null));
		views.add(inflater.inflate(R.layout.layout_more_job_guide_3, null));
		views.add(inflater.inflate(R.layout.layout_more_job_guide_4, null));
		views.add(view5);
		// 添加适配器
		vpAdapter = new GuideAdapter(views);

		vp = (CustomViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		// 监听
		vp.setOnPageChangeListener(this);
		view5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		vp.setDirectionListener(new DirectionListener() {

			@Override
			public void ontouch() {
				// TODO Auto-generated method stub

			}

			@Override
			public void direction(boolean left, boolean right) {
				// TODO Auto-generated method stub
				if (currentIndex == 4) {
					if (left && !right) {
						finish();
					}
				}

			}
		});
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

		dots = new ImageView[views.size()];

		// 循环显示
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// ״̬
	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = position;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		// ״̬
		setCurrentDot(arg0);
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