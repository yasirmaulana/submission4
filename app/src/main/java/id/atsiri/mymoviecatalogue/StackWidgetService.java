package id.atsiri.mymoviecatalogue;

import android.content.Intent;
import android.widget.RemoteViewsService;

import id.atsiri.mymoviecatalogue.StackRemoteViewsFactory;

public class StackWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}
