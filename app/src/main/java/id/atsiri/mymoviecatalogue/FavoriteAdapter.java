package id.atsiri.mymoviecatalogue;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.atsiri.mymoviecatalogue.entity.Favorite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private ArrayList<Favorite> listFavorites = new ArrayList<>();
    private Activity activity;

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Favorite> getListFavorites() {
        return listFavorites;
    }

    public void setListFavorites(ArrayList<Favorite> listFavorites) {

        if (listFavorites.size() > 0) {
            this.listFavorites.clear();
        }
        this.listFavorites.addAll(listFavorites);

        notifyDataSetChanged();
    }

    public void addItem(Favorite favorite) {
        this.listFavorites.add(favorite);
        notifyItemInserted(listFavorites.size() - 1);
    }

    public void updateItem(int position, Favorite favorite) {
        this.listFavorites.set(position, favorite);
        notifyItemChanged(position, favorite);
    }

    public void removeItem(int position) {
        this.listFavorites.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listFavorites.size());
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.tvStatus.setText(listFavorites.get(position).getStatus());
        holder.tvTitle.setText(listFavorites.get(position).getTitle());
        holder.tvVoteAverage.setText(listFavorites.get(position).getVoteAverage());
        holder.cvFavorite.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(activity, "test click " + listFavorites.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, FavoriteDetailActivity.class);
                intent.putExtra(FavoriteDetailActivity.EXTRA_POSITION, position);
                intent.putExtra(FavoriteDetailActivity.EXTRA_FAVORITE, listFavorites.get(position));
                activity.startActivityForResult(intent, FavoriteDetailActivity.REQUEST_UPDATE);
            }
        }));

    }

    @Override
    public int getItemCount() {
        return listFavorites.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvStatus, tvTitle, tvVoteAverage;
        final CardView cvFavorite;

        FavoriteViewHolder(View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tv_item_fav_status);
            tvTitle = itemView.findViewById(R.id.tv_item_fav_title);
            tvVoteAverage = itemView.findViewById(R.id.tv_item_fav_voteaverage);
            cvFavorite = itemView.findViewById(R.id.cv_item_fav);
        }
    }

}
