/**
 * Copyright (C) 2015 JianyingLi <lijy91@foxmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.daza.app.receiver;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.NotificationCompat;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Random;

import io.daza.app.R;
import io.daza.app.event.NotificationEvent;
import io.daza.app.model.Model;
import io.daza.app.model.Notification;
import io.daza.app.ui.HomeActivity;
import io.yunba.android.manager.YunBaManager;

public class NotificationReceiver extends BroadcastReceiver {
    private final String TAG = NotificationReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (YunBaManager.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {

            String topic = intent.getStringExtra(YunBaManager.MQTT_TOPIC);
            String msg = intent.getStringExtra(YunBaManager.MQTT_MSG);

            Notification data = null;
            try {
                data = Model.parseObject(msg, Notification.class);
                if (data == null) {
                    return;
                }
            } catch (Exception ex) {
                return;
            }

            if (isForeground(context)) {
                EventBus.getDefault().post(new NotificationEvent(data));
            } else {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(data.getContent())
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setAutoCancel(true);

                Intent homeIntent = new Intent(context, HomeActivity.class);
                homeIntent.putExtra("extra_notification_id", data.getId());
                homeIntent.putExtra("extra_notification", data.toJSONString());

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(HomeActivity.class);
                stackBuilder.addNextIntent(homeIntent);

                PendingIntent contentIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(new Random().nextInt(), builder.build());
            }
        }
    }

    public static boolean isForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
