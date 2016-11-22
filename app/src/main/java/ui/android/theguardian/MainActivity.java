package ui.android.theguardian;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<News>> {
    /**
     * Constant value for the news loader ID. We can choose any integer.
     * This really only comes into play if we're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;
    //response url for json parsing
    private static final String JSON_REQUEST_URL= "http://content.guardianapis.com/search?page-size=20&api-key=test";
    private static final String LOG_TAG=MainActivity.class.getName();
    private TextView emptyView;
    private NewsAdapter newsAdapter;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //set adapter
        emptyView=(TextView)findViewById(R.id.empty_view);
        ListView listView=(ListView)findViewById(R.id.listViewId);
        newsAdapter=new NewsAdapter(this,R.layout.activity_main,new ArrayList<News>());
        listView.setAdapter(newsAdapter);
        listView.setEmptyView(emptyView);
        connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
        LoaderManager loaderManager=getLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID,null,this);}


    }

/* onCreateLoader is for when the LoaderManager has determined that
the loader with our specified ID isn't running, so we should create a new one.*/
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG,"onCreateLoader çağrıldı");
        return new NewsLoader(this,JSON_REQUEST_URL);
    }
/*We need onLoadFinished(), where we'll do exactly what
we did in onPostExecute(), and use the earthquake data to update our UI -
 by updating the dataset in the adapter.*/
    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> newsList) {
        Log.i(LOG_TAG,"onLoadFinished çağrıldı");
        View loadingIndicator=findViewById(R.id.loadingIndicator);
        loadingIndicator.setVisibility(View.GONE);
        emptyView.setText(R.string.no_news);

// Clear the adapter of previous news data
        newsAdapter.clear();
        // If there is a valid list of {@link News}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if(newsList!=null && !newsList.isEmpty()){
            newsAdapter.addAll(newsList);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        Log.i(LOG_TAG,"onLoaderReset çağrıldı");

        // Loader reset, so we can clear out our existing data.
        newsAdapter.clear();
    }


//    //AsyncTask<doInBackGround parametre tipi,onPostExecute return tipi,doInBackground return tipi>
//    private class NewsJsonAsyncTask extends AsyncTask<String,Void,ArrayList<News>>{
//
//        @Override
//        protected ArrayList<News> doInBackground(String... urls) {
//            if (urls.length < 1 || urls[0] == null) {
//                return null;
//            }
//
//            ArrayList<News> newsList=QueryHelper.fetchData(urls[0]);
//
//            return newsList;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<News> newsList) {
//            // Clear the adapter of previous news data
//            newsAdapter.clear();
//            // If there is a valid list of {@link News}s, then add them to the adapter's
//            // data set. This will trigger the ListView to update.
//            if(newsList!=null && !newsList.isEmpty()){
//                newsAdapter.addAll(newsList);
//            }
//        }
//
//
//    }

}
