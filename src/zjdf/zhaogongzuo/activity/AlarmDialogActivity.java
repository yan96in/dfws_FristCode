/**
 * Copyright © 2014年6月12日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.activity;

import zjdf.zhaogongzuo.databases.sharedpreferences.SetsKeeper;
import zjdf.zhaogongzuo.service.AlarmReceiver;
import zjdf.zhaogongzuo.ui.CustomMessage;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

/**
 * @author Eilin.Yang VearyEast
 * @since 2014年6月12日
 * @version v1.0.0
 * @modify
 */
public class AlarmDialogActivity extends Activity {

	private int meetid = 0;
	private Context mContext;
	private AlertDialog alert;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this;
		meetid = getIntent().getIntExtra("meetid", 0);
		// setContentView(R.layout.layout_alarm_dialog);
		alert =new  AlertDialog
				.Builder(mContext)
				.setMessage("是否取消闹钟")
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (meetid!=0) {
							cancelClock(mContext);
						}
						finish();
					}
				})
				.setNegativeButton("不取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();
							}
						}).create();
		alert.show();
	}

	// 取消闹钟
	private void cancelClock(Context context) {
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		// 建立Intent和PendingIntent来调用闹钟管理器
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
				meetid, intent, 0);
		// 获取闹钟管理器
		alarmManager.cancel(pendingIntent);
		CustomMessage.showToast(context, "闹钟已经取消！", Gravity.CENTER, 0);
		SetsKeeper.keepJobfairAlarmState(context, 0, meetid+"");
	}

	// public void onclicks(View view) {
	// if (view.getId() == R.id.btn_alarm_cancel) {
	// cancelClock(mContext);
	// }
	// if (view.getId() == R.id.btn_alarm_nullcancel) {
	//
	// }
	// finish();
	// }
}
