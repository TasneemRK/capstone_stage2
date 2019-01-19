package tasneem.kurraz.com.capstone_stage2.Widgets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;
import java.util.Set;

import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.Helper.ConvertToDataSet;
import tasneem.kurraz.com.capstone_stage2.Models.Product;
import tasneem.kurraz.com.capstone_stage2.R;

class FavoriteWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private List<Product> products;

    public FavoriteWidgetFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.SHARED_PREF), Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet(Constants.SHAERS_KEY, null);
        if (set != null) {
            products = ConvertToDataSet.convertToList(set);
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (products == null) return 0;
        return products.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        remoteViews.setTextViewText(R.id.widget_name, products.get(i).getProduct_name());
        remoteViews.setTextViewText(R.id.widget_price, products.get(i).getProduct_price());
        remoteViews.setImageViewUri(R.id.widget_image, Uri.parse(products.get(i).getProduct_image()));
        Intent intent = new Intent();
        remoteViews.setOnClickFillInIntent(R.id.widget_item, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
