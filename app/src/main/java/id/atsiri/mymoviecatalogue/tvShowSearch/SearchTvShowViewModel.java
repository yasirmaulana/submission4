package id.atsiri.mymoviecatalogue.tvShowSearch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import id.atsiri.mymoviecatalogue.BuildConfig;


public class SearchTvShowViewModel extends ViewModel {
    private MutableLiveData<ArrayList<SearchTvShows>> listTvShows = new MutableLiveData<>();

    void setTvShowSearch(final String tvShowSearch) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<SearchTvShows> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key="+ BuildConfig.TMDB_API_KEY +"&language=en-US&query=" + tvShowSearch;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvshow = list.getJSONObject(i);
                        SearchTvShows tvShowItems = new SearchTvShows(tvshow);
                        listItems.add(tvShowItems);
                    }
                    listTvShows.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    LiveData<ArrayList<SearchTvShows>> getTvShows () {
        return listTvShows;
    }
}
