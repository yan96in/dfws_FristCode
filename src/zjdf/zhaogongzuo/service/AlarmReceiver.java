/**
 * 
 */
package zjdf.zhaogongzuo.service;

import java.io.IOException;

import zjdf.zhaogongzuo.activity.AlarmDialogActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;

public class AlarmReceiver extends BroadcastReceiver {

	private int meetid;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	
	@Override
	public void onReceive(Context context, Intent data) {
		if (data!=null) {
			meetid = data.getIntExtra("meetid", 0);
			MediaPlayer mp = new MediaPlayer();
			try {
				mp.setDataSource(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
				mp.prepare();
				mp.start();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent=new Intent(context,AlarmDialogActivity.class); 
			intent.putExtra("meetid", meetid);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}
}
