package id.atsiri.mymoviecatalogue;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieDetailViewModel extends ViewModel {
    private MutableLiveData<MovieDetail> movieDetailData = new MutableLiveData<>();

    void setMovieDetail(final int movieId) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/"+ movieId +"?api_key="+ BuildConfig.TMDB_API_KEY +"&language=en-US";
        final MovieDetail mMovieDetail = new MovieDetail();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    MovieDetail modelMovieDetail = new MovieDetail(responseObject);

                    mMovieDetail.setBackdropPath(modelMovieDetail.getBackdropPath());
                    mMovieDetail.setPosterPath(modelMovieDetail.getPosterPath());
                    mMovieDetail.setTitle(modelMovieDetail.getTitle());
                    mMovieDetail.setVoteAverage(modelMovieDetail.getVoteAverage());
                    mMovieDetail.setOverView(modelMovieDetail.getOverView());

                    movieDetailData.postValue(mMovieDetail);
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

    LiveData<MovieDetail> getMovieDetail() {
        return movieDetailData;
    }

}
