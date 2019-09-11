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

public class MovieFavAdapter extends RecyclerView.Adapter<MovieFavAdapter.MovieFavViewHolder> {
    private ArrayList<Favorite> listMovieFav = new ArrayList<>();
    private Activity activity;

    public MovieFavAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Favorite> getListMovieFav() {
        return listMovieFav;
    }

    public void setListMovieFav(ArrayList<Favorite> listMovieFav) {

        if (listMovieFav.size() > 0) {
            this.listMovieFav.clear();
        }
        this.listMovieFav.addAll(listMovieFav);

        notifyDataSetChanged();
    }

    public void addItem(Favorite movie) {
        this.listMovieFav.add(movie);
        notifyItemInserted(listMovieFav.size() - 1);
    }

    public void updateItem(int position, Favorite movie) {
        this.listMovieFav.set(position, movie);
        notifyItemChanged(position, movie);
    }

    public void removeItem(int position) {
        this.listMovieFav.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listMovieFav.size());
    }

    @NonNull
    @Override
    public MovieFavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_fav, parent, false);
        return new MovieFavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFavViewHolder holder, int position) {
        holder.tvTitle.setText(listMovieFav.get(position).getTitle());
        holder.tvReleaseData.setText(listMovieFav.get(position).getVoteAverage());
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
        return listMovieFav.size();
    }

    class MovieFavViewHolder extends RecyclerView.ViewHolder {
        final TextView tvReleaseData, tvTitle;
        final CardView cvMovieFav;

        MovieFavViewHolder(View itemView) {
            super(itemView);
            tvReleaseData = itemView.findViewById(R.id.tv_item_movie_fav_release_date);
            tvTitle = itemView.findViewById(R.id.tv_item_movie_fav_title);
            cvMovieFav = itemView.findViewById(R.id.cv_item_movie_fav);
        }
    }

}
