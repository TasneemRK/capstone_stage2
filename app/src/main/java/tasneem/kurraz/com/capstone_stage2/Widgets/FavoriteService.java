package tasneem.kurraz.com.capstone_stage2.Widgets;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.RemoteViewsService;

public class FavoriteService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavoriteWidgetFactory(this.getApplicationContext());
    }
}
