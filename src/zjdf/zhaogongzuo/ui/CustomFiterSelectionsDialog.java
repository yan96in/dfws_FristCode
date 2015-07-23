package zjdf.zhaogongzuo.ui;

import zjdf.zhaogongzuo.R;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import zjdf.zhaogongzuo.activity.search.PositionFiterActivity;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.configures.enums.OptionsEnum;
import zjdf.zhaogongzuo.entity.OptionKeyValue;

/**
 * 对话框选项 <h2>CustomFiterSelectionsDialog</h2>
 * 
 * <pre> </pre>
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年4月21日
 * @version
 * @modify
 * 
 */
public class CustomFiterSelectionsDialog {

	private Context context;
	/**
	 * 自定义对话框
	 */
	private Dialog dialog;
	
	private TextView txt_title;
	/**
	 * 消息容器
	 */
	private LinearLayout linear_dialog_position_fiter_container;
	
	/**单机后的回调*/
	private PositionFiterActivity.IflashViewsCallback iflashViewsCallback;

	public CustomFiterSelectionsDialog(Context context) {
		this.context = context;
		init();
	}

	private void init() {
		dialog = new Dialog(context, R.style.custom_dialog);
		dialog.setContentView(R.layout.layout_dalog_position_fiter);
		Window window = dialog.getWindow();
		window.setWindowAnimations(R.style.dialog_in_and_out);
		window.setGravity(Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		// lp.width = Configure.screenWidth; // 设置宽度
		dialog.getWindow().setAttributes(lp);
		linear_dialog_position_fiter_container = (LinearLayout) dialog
				.findViewById(R.id.linear_dialog_position_fiter_container);
		txt_title=(TextView)dialog.findViewById(R.id.txt_title);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "incomplete-switch", "unused" })
	private void initView(final OptionsEnum opt) {
		int n = 0;
		linear_dialog_position_fiter_container.removeAllViews();
		switch (opt) {
		case UPDATE_TIME:
			txt_title.setText("选择更新时间");
			n=FrameConfigures.list_updateTimes.size();
			for (OptionKeyValue entry : FrameConfigures.list_updateTimes) {
				OptionsItemView itemView = new OptionsItemView(context);
				final String key = entry.key;
				final Integer value = entry.value;
				itemView.setKeyValue(key, value);
				if (key.equals(PositionFiterActivity.updateTime_key)) {
					itemView.setChecked(true);
				}else{
					itemView.setChecked(false);
				}
				itemView.setItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						iflashViewsCallback.doFlash(opt, key, value);
						dismiss();
					}
				});
				linear_dialog_position_fiter_container.addView(itemView);
			}
			break;

		case WORKS:
			txt_title.setText("选择工作经验");
			n=FrameConfigures.list_works.size();
			for (OptionKeyValue entry : FrameConfigures.list_works) {
				OptionsItemView itemView = new OptionsItemView(context);
				final String key = entry.key;
				final Integer value = entry.value;
				itemView.setKeyValue(key, value);
				if (key.equals(PositionFiterActivity.works_key)) {
					itemView.setChecked(true);
				}else{
					itemView.setChecked(false);
				}
				itemView.setItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						iflashViewsCallback.doFlash(opt, key, value);
						dismiss();
					}
				});
				linear_dialog_position_fiter_container.addView(itemView);
			}
			
			break;
		case EDUCATIONS:
			txt_title.setText("选择学历要求");
			n=FrameConfigures.list_educations.size();
			for (OptionKeyValue entry : FrameConfigures.list_educations) {
				OptionsItemView itemView = new OptionsItemView(context);
				final String key = entry.key;
				final Integer value = entry.value;
				itemView.setKeyValue(key, value);
				if (key.equals(PositionFiterActivity.educations_key)) {
					itemView.setChecked(true);
				}else{
					itemView.setChecked(false);
				}
				itemView.setItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						iflashViewsCallback.doFlash(opt, key, value);
						dismiss();
					}
				});
				linear_dialog_position_fiter_container.addView(itemView);
			}
			break;
		case SALARY:
			txt_title.setText("选择薪资待遇");
			n=FrameConfigures.list_salary_search.size();
			for (OptionKeyValue entry : FrameConfigures.list_salary_search) {
				OptionsItemView itemView = new OptionsItemView(context);
				final String key = entry.key;
				final Integer value = entry.value;
				itemView.setKeyValue(key, value);
				if (key.equals(PositionFiterActivity.salary_key)) {
					itemView.setChecked(true);
				}else{
					itemView.setChecked(false);
				}
				itemView.setItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						iflashViewsCallback.doFlash(opt, key, value);
						dismiss();
					}
				});
				linear_dialog_position_fiter_container.addView(itemView);
			}
			break;
		case ROOM_BOARD:
			txt_title.setText("选择食宿情况");
			n=FrameConfigures.list_room_board.size();
			for (OptionKeyValue entry : FrameConfigures.list_room_board) {
				OptionsItemView itemView = new OptionsItemView(context);
				final String key = entry.key;
				final Integer value = entry.value;
				itemView.setKeyValue(key, value);
				if (key.equals(PositionFiterActivity.room_board_key)) {
					itemView.setChecked(true);
				}else{
					itemView.setChecked(false);
				}
				itemView.setItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						iflashViewsCallback.doFlash(opt, key, value);
						dismiss();
					}
				});
				linear_dialog_position_fiter_container.addView(itemView);
			}
			break;
		case WORK_MODE:
			txt_title.setText("选择职位性质");
			n=FrameConfigures.list_work_mode.size();
			for (OptionKeyValue entry : FrameConfigures.list_work_mode) {
				OptionsItemView itemView = new OptionsItemView(context);
				final String key = entry.key;
				final Integer value = entry.value;
				itemView.setKeyValue(key, value);
				if (key.equals(PositionFiterActivity.works_mode_key)) {
					itemView.setChecked(true);
				}else{
					itemView.setChecked(false);
				}
				itemView.setItemClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						iflashViewsCallback.doFlash(opt, key, value);
						dismiss();
					}
				});
				linear_dialog_position_fiter_container.addView(itemView);
			}
			break;
		}
	}
	
	/**
	 * 设置数据修改监听
	 *<pre>方法  </pre>
	 * @param callback
	 */
	public void setOnDataFlashListener(PositionFiterActivity.IflashViewsCallback callback){
		iflashViewsCallback=callback;
	}

	public void show(OptionsEnum opt) {
		if (dialog == null) {
			return;
		}
		initView(opt);
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	public void dismiss() {
		if (dialog == null) {
			return;
		}
		if (dialog.isShowing()) {
			dialog.dismiss();
			;
		}
	}

}
