package id.atsiri.mymoviecatalogue.movieSearch;

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

public class SearchMovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<SearchMovies>> listMovies = new MutableLiveData<>();

    void setMovieSearch(final String movieSearch) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<SearchMovies> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+ BuildConfig.TMDB_API_KEY +"&language=en-US&query=" + movieSearch;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        SearchMovies movieItems = new SearchMovies(movie);
                        listItems.add(movieItems);
                    }
                    listMovies.postValue(listItems);
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

    LiveData<ArrayList<SearchMovies>> getMovies() {
        return listMovies;
    }

}
