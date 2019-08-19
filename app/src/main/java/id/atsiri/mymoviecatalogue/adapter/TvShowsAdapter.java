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

import id.atsiri.mymoviecatalogue.R;
import id.atsiri.mymoviecatalogue.TvShowItems;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.TvShowViewHolder> {
    private ArrayList<TvShowItems> mData = new ArrayList<>();

    private OnItemClickCallback  onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<TvShowItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_tvshow, viewGroup, false);
        return new TvShowViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder tvShowViewHolder, int position) {
        tvShowViewHolder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBackdropPath;
        TextView tvVoteAverage;
        TextView tvTitle;
        TextView tvFirstAirDate;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBackdropPath = itemView.findViewById(R.id.backdrop_tvshow);
            tvVoteAverage = itemView.findViewById(R.id.vote_average_tvshow);
            tvTitle = itemView.findViewById(R.id.title_tvshow);
            tvFirstAirDate = itemView.findViewById(R.id.first_air_date_tvshow);
        }

        void bind(final TvShowItems tvShowItems) {
            tvVoteAverage.setText(tvShowItems.getVoteAverage());
            tvTitle.setText(tvShowItems.getTitle());
            tvFirstAirDate.setText(tvShowItems.getFirstAirDate());

            Glide.with(itemView.getContext())
                    .load(tvShowItems.getBackdropPath())
                    .into(ivBackdropPath);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClicked(tvShowItems);
                }
            });
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TvShowItems data);
    }
}
