package id.atsiri.mymoviecatalogue;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        holder.tvTitle.setText(listFavorites.get(position).getTitle());
        holder.tvReleaseData.setText(listFavorites.get(position).getVoteAverage());
        holder.cvMovieFav.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
//                Intent intent = new Intent(activity, MovieAddUpdateActivity.class);
//                intent.putExtra(MovieAddUpdateActivity.EXTRA_POSITION, position);
//                intent.putExtra(MovieAddUpdateActivity.EXTRA_MOVIE, listMovieFav.get(position));
//                activity.startActivityForResult(intent, MovieAddUpdateActivity.REQUEST_UPDATE);
            }
        }));

    }

    @Override
    public int getItemCount() {
        return listFavorites.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvReleaseData, tvTitle;
        final CardView cvMovieFav;

        FavoriteViewHolder(View itemView) {
            super(itemView);
            tvReleaseData = itemView.findViewById(R.id.tv_item_fav_release_date);
            tvTitle = itemView.findViewById(R.id.tv_item_fav_title);
            cvMovieFav = itemView.findViewById(R.id.cv_item_fav);
        }
    }

}
