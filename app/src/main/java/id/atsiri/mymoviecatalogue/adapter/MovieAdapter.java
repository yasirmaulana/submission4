package id.atsiri.mymoviecatalogue.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.atsiri.mymoviecatalogue.MovieItems;
import id.atsiri.mymoviecatalogue.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<MovieItems> mData = new ArrayList<>();

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<MovieItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_movie, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        movieViewHolder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBackdropPath;
        TextView textViewVoteAverage;
        TextView textViewTitle;
        TextView textViewReleaseDate;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBackdropPath = itemView.findViewById(R.id.img_photo);
            textViewVoteAverage = itemView.findViewById(R.id.tv_user_score);
            textViewTitle = itemView.findViewById(R.id.tv_title);
            textViewReleaseDate = itemView.findViewById(R.id.tv_movie_date);
        }

        void bind(final MovieItems movieItems) {
            textViewVoteAverage.setText(movieItems.getVoteAvarage());
            textViewTitle.setText(movieItems.getTitle());
            textViewReleaseDate.setText(movieItems.getReleaseDate());

            Glide.with(itemView.getContext())
                    .load(movieItems.getBackdropPath())
                    .into(ivBackdropPath);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClicked(movieItems);
                }
            });
        }

    }

    public interface OnItemClickCallback {
        void onItemClicked(MovieItems data);
    }

}