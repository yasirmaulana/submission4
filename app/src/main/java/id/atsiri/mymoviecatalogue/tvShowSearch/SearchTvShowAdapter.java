package id.atsiri.mymoviecatalogue.tvShowSearch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.atsiri.mymoviecatalogue.R;

public class SearchTvShowAdapter extends RecyclerView.Adapter<SearchTvShowAdapter.SearchTvShowViewHolder> {
    private ArrayList<SearchTvShows> mData = new ArrayList<>();

    public void setData(ArrayList<SearchTvShows> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchTvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tvshow_search, viewGroup, false);
        return new SearchTvShowViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTvShowViewHolder searchTvShowViewHolder, int position) {
        searchTvShowViewHolder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() { return mData.size(); }

    class SearchTvShowViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBackdropPath;
        TextView tvVoteAverage;
        TextView tvTitle;
        TextView tvFirstAirDate;

        SearchTvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBackdropPath = itemView.findViewById(R.id.img_photo_tssearch);
            tvVoteAverage = itemView.findViewById(R.id.tv_user_score_tssearch);
            tvTitle = itemView.findViewById(R.id.tv_title_tssearch);
            tvFirstAirDate = itemView.findViewById(R.id.tv_tvshow_date_tssearch);
        }

        void bind(final SearchTvShows searchTvShows) {
            tvVoteAverage.setText(searchTvShows.getVoteAverage());
            tvTitle.setText(searchTvShows.getTitle());
            tvFirstAirDate.setText(searchTvShows.getFirstAirDate());

            Glide.with(itemView.getContext())
                    .load(searchTvShows.getBackdropPath())
                    .into(ivBackdropPath);

        }
    }
}
