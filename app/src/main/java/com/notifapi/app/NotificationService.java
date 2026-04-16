package com.notifapi.app;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import org.json.JSONArray;
import org.json.JSONObject;

public class NotificationService extends NotificationListenerService {
    
    public static JSONArray notifications = new JSONArray();

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        try {
            JSONObject n = new JSONObject();
            n.put("package", sbn.getPackageName());
            n.put("title", sbn.getNotification().extras.getString("android.title", ""));
            n.put("content", sbn.getNotification().extras.getString("android.text", ""));
            n.put("time", sbn.getPostTime());
            
            // FamPay filter
            if (sbn.getPackageName().contains("fampay")) {
                notifications.put(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {}
}
