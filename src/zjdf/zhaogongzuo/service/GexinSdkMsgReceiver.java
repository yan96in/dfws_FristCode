package zjdf.zhaogongzuo.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.igexin.sdk.Consts;
import com.tencent.mm.sdk.platformtools.Log;

import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.activity.SplashActivity;

public class GexinSdkMsgReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Context mContext = context.getApplicationContext();
		switch (bundle.getInt(Consts.CMD_ACTION)) {
		case Consts.GET_MSG_DATA:
			// 获取透传数据
			// String appid = bundle.getString("appid");
			byte[] payload = bundle.getByteArray("payload");
			if (!ApplicationConfig.isResume) {
				Intent intents = new Intent(Intent.ACTION_MAIN);
			     intents.addCategory(Intent.CATEGORY_LAUNCHER);
			     intents.setClass(mContext, SplashActivity.class);
			     intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			     mContext.startActivity(intents);
			}
			break;
		case Consts.GET_CLIENTID:
			// 获取ClientID(CID)
			// 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
			String cid = bundle.getString("clientid");
			// 将该clientid和userid进行绑定。
			ApplicationConfig.client_id = cid;
			Log.i("clientid", bundle.toString());
			break;

		case Consts.BIND_CELL_STATUS:
			String cell = bundle.getString("cell");
			break;
		default:
			break;
		}
	}
}
