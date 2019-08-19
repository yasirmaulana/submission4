package id.atsiri.mymoviecatalogue;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TvShowDetailViewModel extends ViewModel {
    private MutableLiveData<TvShowDetail> tvShowDetailData = new MutableLiveData<>();

    void setTvShowDetail(final int tvShowId) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/tv/"+ tvShowId +"?api_key="+ BuildConfig.TMDB_API_KEY +"&language=en-US";
        final TvShowDetail mTvShowDetail = new TvShowDetail();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    TvShowDetail modelTvShowDetail = new TvShowDetail(responseObject);

                    mTvShowDetail.setBackdropPath(modelTvShowDetail.getBackdropPath());
                    mTvShowDetail.setPosterPath(modelTvShowDetail.getPosterPath());
                    mTvShowDetail.setTitle(modelTvShowDetail.getTitle());
                    mTvShowDetail.setVoteAverage(modelTvShowDetail.getVoteAverage());
                    mTvShowDetail.setOverView(modelTvShowDetail.getOverView());

                    tvShowDetailData.postValue(mTvShowDetail);
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

    LiveData<TvShowDetail> getTvShowDetail () { return tvShowDetailData; }

}
