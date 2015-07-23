/**
 * Copyright © 2014年4月18日 FindJob www.veryeast.com
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

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.configures.enums.OptionsEnum;
import zjdf.zhaogongzuo.fragments.SearchFragment;
import zjdf.zhaogongzuo.ui.CustomFiterSelectionsDialog;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 *<h2> PositionFiterActivity</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月18日
 * @version 
 * @modify 
 * 
 */
public class PositionFiterActivity extends Activity {

	private Context context;
	/**返回*/
	private ImageButton goback;
	/**完成*/
	private Button btn_fiter_finish;
	
	/**更新时间*/
	private RelativeLayout rela_fiter_updatetime; 
	private TextView txt_fiter_updatetime;
	/**更新时间.默认选中-1:不限*/
	public static int updateTime_value=-1;
	public static String updateTime_key="不限";
	
	/**工作经验*/
	private RelativeLayout rela_fiter_works;
	private TextView txt_fiter_works;
	/**工作经验。默认选中0：不限*/
	public static int works_value=0;
	public static String works_key="不限";
	
	/**学历要求*/
	private RelativeLayout rela_fiter_educations;
	private TextView txt_fiter_educations;
	/**教育年限,默认选中0：不限*/
	public static int educations_value=0;
	public static String educations_key="不限";
	
	/**薪资待遇*/
	private RelativeLayout rela_fiter_salary;
	private TextView txt_fiter_salary;
	/**薪资待遇。默认0:不限*/
	public static int salary_value=0;
	public static String salary_key="不限";
	
	/**食宿情况*/
	private RelativeLayout rela_fiter_room_board;
	private TextView txt_fiter_room_board;
	/**食宿状况。默认值0：不限*/
	public static int room_board_value=0;
	public static String room_board_key="不限";
	
	/**职位性质*/
	private RelativeLayout rela_fiter_works_mode;
	private TextView txt_fiter_works_mode;
	/**工作性质。默认为0：不限*/
	public static int works_mode_value=0;
	public static String works_mode_key="不限";
	
	/**过滤选项对话框*/
	private CustomFiterSelectionsDialog fiterSelectionsDialog;
	final int color_select=Color.parseColor("#fffc9a39");
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_position_list_fiter);
		context=this;
		initView();
		initDialog();
		setClickListener();
//		initValue();
		initDatas();
	}
	
	
	private void initView(){
		goback=(ImageButton)findViewById(R.id.goback);
		btn_fiter_finish=(Button)findViewById(R.id.btn_fiter_finish);
		rela_fiter_updatetime=(RelativeLayout)findViewById(R.id.rela_fiter_updatetime);
		txt_fiter_updatetime=(TextView)findViewById(R.id.txt_fiter_updatetime);
		
		rela_fiter_works=(RelativeLayout)findViewById(R.id.rela_fiter_works);
		txt_fiter_works=(TextView)findViewById(R.id.txt_fiter_works);
		
		rela_fiter_educations=(RelativeLayout)findViewById(R.id.rela_fiter_educations);
		txt_fiter_educations=(TextView)findViewById(R.id.txt_fiter_educations);
		
		rela_fiter_salary=(RelativeLayout)findViewById(R.id.rela_fiter_salary);
		txt_fiter_salary=(TextView)findViewById(R.id.txt_fiter_salary);
		
		rela_fiter_room_board=(RelativeLayout)findViewById(R.id.rela_fiter_room_board);
		txt_fiter_room_board=(TextView)findViewById(R.id.txt_fiter_room_board);
		
		rela_fiter_works_mode=(RelativeLayout)findViewById(R.id.rela_fiter_works_mode);
		txt_fiter_works_mode=(TextView)findViewById(R.id.txt_fiter_works_mode);
	}
	
	/**
	 * 初始化个属性值
	 *<pre>方法  </pre>
	 */
	public static void initValue(){
		updateTime_value=-1;
		updateTime_key="不限";
		works_value=0;
		works_key="不限";
		educations_value=0;
		educations_key="不限";
		salary_value=0;
		salary_key="不限";
		room_board_value=0;
		room_board_key="不限";
		works_mode_value=0;
		works_mode_key="不限";
	}
	
	private void initDatas(){
		if ("不限".equals(updateTime_key)) {
			txt_fiter_updatetime.setTextColor(Color.GRAY);
		}else {
			txt_fiter_updatetime.setTextColor(color_select);
		}
		txt_fiter_updatetime.setText(updateTime_key);
		
		if ("不限".equals(works_key)) {
			txt_fiter_works.setTextColor(Color.GRAY);
		}else {
			txt_fiter_works.setTextColor(color_select);
		}
		txt_fiter_works.setText(works_key);
		
		if ("不限".equals(educations_key)) {
			txt_fiter_educations.setTextColor(Color.GRAY);
		}else {
			txt_fiter_educations.setTextColor(color_select);
		}
		txt_fiter_educations.setText(educations_key);
		
		if ("不限".equals(salary_key)) {
			txt_fiter_salary.setTextColor(Color.GRAY);
		}else {
			txt_fiter_salary.setTextColor(color_select);
		}
		txt_fiter_salary.setText(salary_key);
		
		if ("不限".equals(room_board_key)) {
			txt_fiter_room_board.setTextColor(Color.GRAY);
		}else {
			txt_fiter_room_board.setTextColor(color_select);
		}
		txt_fiter_room_board.setText(room_board_key);
		
		if ("不限".equals(works_mode_key)) {
			txt_fiter_works_mode.setTextColor(Color.GRAY);
		}else {
			txt_fiter_works_mode.setTextColor(color_select);
		}
		txt_fiter_works_mode.setText(works_mode_key);
	}
	
	private void initDialog(){
		
		fiterSelectionsDialog=new CustomFiterSelectionsDialog(context);
		fiterSelectionsDialog.setOnDataFlashListener(new IflashViewsCallback() {
			
			@Override
			public void doFlash(OptionsEnum opt, String key, Integer value) {
				// TODO Auto-generated method stub
				if (StringUtils.isEmpty(key)) {
					return;
				}
				switch (opt) {
				case UPDATE_TIME:
					updateTime_key=key;
					updateTime_value=value;
					if ("不限".equals(key)) {
						txt_fiter_updatetime.setTextColor(Color.GRAY);
					}else {
						txt_fiter_updatetime.setTextColor(color_select);
					}
					txt_fiter_updatetime.setText(key);
					break;

				case WORKS:
					works_key=key;
					works_value=value;
					if ("不限".equals(key)) {
						txt_fiter_works.setTextColor(Color.GRAY);
					}else {
						txt_fiter_works.setTextColor(color_select);
					}
					txt_fiter_works.setText(key);
					break;
				case EDUCATIONS:
					educations_key=key;
					educations_value=value;
					if ("不限".equals(key)) {
						txt_fiter_educations.setTextColor(Color.GRAY);
					}else {
						txt_fiter_educations.setTextColor(color_select);
					}
					txt_fiter_educations.setText(key);
					break;
				case SALARY:
					salary_key=key;
					salary_value=value;
					if ("不限".equals(key)) {
						txt_fiter_salary.setTextColor(Color.GRAY);
					}else {
						txt_fiter_salary.setTextColor(color_select);
					}
					txt_fiter_salary.setText(key);
					break;
				case ROOM_BOARD:
					room_board_key=key;
					room_board_value=value;
					if ("不限".equals(key)) {
						txt_fiter_room_board.setTextColor(Color.GRAY);
					}else {
						txt_fiter_room_board.setTextColor(color_select);
					}
					txt_fiter_room_board.setText(key);
					break;
				case WORK_MODE:
					works_mode_key=key;
					works_mode_value=value;
					if ("不限".equals(key)) {
						txt_fiter_works_mode.setTextColor(Color.GRAY);
					}else {
						txt_fiter_works_mode.setTextColor(color_select);
					}
					txt_fiter_works_mode.setText(key);
					break;
				}
			}
		});
	}
	
	private void setClickListener(){
		rela_fiter_educations.setOnClickListener(listener);
		rela_fiter_room_board.setOnClickListener(listener);
		rela_fiter_salary.setOnClickListener(listener);
		rela_fiter_updatetime.setOnClickListener(listener);
		rela_fiter_works.setOnClickListener(listener);
		rela_fiter_works_mode.setOnClickListener(listener);
		goback.setOnClickListener(listener);
		btn_fiter_finish.setOnClickListener(listener);
	}
	
	
	private View.OnClickListener listener=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v==rela_fiter_updatetime) {
				fiterSelectionsDialog.show(OptionsEnum.UPDATE_TIME);
			}
			
			if (v==rela_fiter_works) {
				fiterSelectionsDialog.show(OptionsEnum.WORKS);
			}
			
			if (v==rela_fiter_educations) {
				fiterSelectionsDialog.show(OptionsEnum.EDUCATIONS);
			}
			if (v==rela_fiter_salary) {
				fiterSelectionsDialog.show(OptionsEnum.SALARY);
			}
			
			if (v==rela_fiter_room_board) {
				fiterSelectionsDialog.show(OptionsEnum.ROOM_BOARD);
			}
			
			if (v==rela_fiter_works_mode) {
				fiterSelectionsDialog.show(OptionsEnum.WORK_MODE);
			}
			if (v==goback) {
				finish();
			}
			if (v==btn_fiter_finish) {
				startIntent();
			}
		}
	};
	
	/**
	 * 完成天传
	 *<pre>方法  </pre>
	 * @param keyword 关键字
	 */
	private void startIntent(){
		Intent intent=new Intent(); 
		this.setResult(108,intent);
		finish();
	}
	
	public static interface IflashViewsCallback{
		void doFlash(OptionsEnum opt,String key,Integer value);
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
