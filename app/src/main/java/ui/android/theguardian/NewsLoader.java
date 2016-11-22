package ui.android.theguardian;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Kubra on 22.11.2016.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {
    private static final String LOG_TAG=NewsLoader.class.getName();
    private String mUrl;
/*
*Overriding onStartLoading is a required step to actually
 *  trigger the loadInBackground() method to execute.
* */
    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"onStartLoading çağrıldı");

        forceLoad();
    }

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl=url;
    }
//background thread
    @Override
    public ArrayList<News> loadInBackground() {
        Log.i(LOG_TAG,"loadInBackground çağrıldı");

        if(mUrl==null){
            return null;}
        ArrayList<News> newsList=QueryHelper.fetchData(mUrl);
        return newsList;
    }
}
