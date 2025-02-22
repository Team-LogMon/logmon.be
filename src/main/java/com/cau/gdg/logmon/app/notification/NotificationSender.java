package com.cau.gdg.logmon.app.notification;

import com.cau.gdg.logmon.app.logAlertSubscription.LogAlertSubscription;
import com.cau.gdg.logmon.app.logs.Log;

public interface NotificationSender {
    void send(Log log, LogAlertSubscription subscription);

}
