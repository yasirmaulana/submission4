package id.atsiri.mymoviecatalogue.movieSearch;

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

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.SearchMovieViewHolder> {
    private ArrayList<SearchMovies> mData = new ArrayList<>();

    public void setData(ArrayList<SearchMovies> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_search, viewGroup, false);
        return new SearchMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieViewHolder searchMovieViewHolder, int position) {
        searchMovieViewHolder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class SearchMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBackdropPath;
        TextView textViewVoteAverage;
        TextView textViewTitle;
        TextView textViewReleaseDate;

        SearchMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBackdropPath = itemView.findViewById(R.id.img_photo_msearch);
            textViewVoteAverage = itemView.findViewById(R.id.tv_user_score_msearch);
            textViewTitle = itemView.findViewById(R.id.tv_title_msearch);
            textViewReleaseDate = itemView.findViewById(R.id.tv_movie_date_msearch);
        }

        public void bind(SearchMovies searchMovies) {
            textViewVoteAverage.setText(searchMovies.getVoteAvarage());
            textViewTitle.setText(searchMovies.getTitle());
            textViewReleaseDate.setText(searchMovies.getReleaseDate());

            Glide.with(itemView.getContext())
                    .load(searchMovies.getBackdropPath())
                    .into(ivBackdropPath);


        }
    }
}
