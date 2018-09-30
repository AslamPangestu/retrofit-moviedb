package com.mvryan.katalogfilm;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by mvryan on 30/09/18.
 */

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this);
    }
}
