package com.notifapi.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (!isNotificationServiceEnabled()) {
            startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
        } else {
            // Permission already on - start service
            Intent service = new Intent(this, NotificationService.class);
            startService(service);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNotificationServiceEnabled()) {
            Intent service = new Intent(this, NotificationService.class);
            startService(service);
            finish();
        }
    }

    private boolean isNotificationServiceEnabled() {
        String flat = Settings.Secure.getString(
            getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            String[] names = flat.split(":");
            for (String name : names) {
                ComponentName cn = ComponentName.unflattenFromString(name);
                if (cn != null && cn.getPackageName().equals(getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
