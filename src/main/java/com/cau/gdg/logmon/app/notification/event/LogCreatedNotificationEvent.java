package com.cau.gdg.logmon.app.notification.event;

import com.cau.gdg.logmon.app.logs.Log;

public record LogCreatedNotificationEvent(Log log) { }
