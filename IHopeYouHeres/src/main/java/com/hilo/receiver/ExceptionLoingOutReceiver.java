package com.hilo.receiver;

import com.hilo.util.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 数据库异常情况下接收广播直接退出登录，重新登录
 * @author Dick
 */
public class ExceptionLoingOutReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Utils.appLogout(context);
	}

}
