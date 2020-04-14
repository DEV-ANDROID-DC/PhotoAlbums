package com.debin.photoalbums.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.debin.photoalbums.R;
import com.debin.photoalbums.Utilities.AlbumClickListner;
import com.debin.photoalbums.model.AlbumPhotos;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PagenationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private List<AlbumPhotos> albumPhotos;
    private AlbumClickListner albumClickListner;

    public PagenationAdapter(List<AlbumPhotos> albumPhotos, AlbumClickListner albumClickListner) {
        this.albumPhotos = albumPhotos;
        this.albumClickListner = albumClickListner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.album_item, parent, false);
                viewHolder = new AlbumViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingViewHolder(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                AlbumViewHolder albumViewHolder = (AlbumViewHolder) holder;
                albumViewHolder.bind(albumPhotos.get(position), albumClickListner);
                break;
            case LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.mProgressBar.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return albumPhotos == null ? 0 : albumPhotos.size();
    }

    protected class AlbumViewHolder extends RecyclerView.ViewHolder {

        ImageView imgThumbNail;
        TextView tvTitle;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbNail = itemView.findViewById(R.id.img_album);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }

        public void bind(AlbumPhotos albumPhotos, AlbumClickListner albumClickListner) {
            Picasso.get().load(albumPhotos.getThumbnailUrl()).into(imgThumbNail);
            tvTitle.setText(albumPhotos.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    albumClickListner.onAlbumClick(albumPhotos);
                }
            });
        }
    }

    protected class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar mProgressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
    }

 }
