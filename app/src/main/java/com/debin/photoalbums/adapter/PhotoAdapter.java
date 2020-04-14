package com.debin.photoalbums.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.debin.photoalbums.R;
import com.debin.photoalbums.model.AlbumPhotos;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

    private List<AlbumPhotos> albumPhotosList;

    public PhotoAdapter(List<AlbumPhotos> albumPhotosList) {
        this.albumPhotosList = albumPhotosList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(albumPhotosList.get(position).getThumbnailUrl()).into(holder.imgThumbNail);
        holder.tvTitle.setText(albumPhotosList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return albumPhotosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgThumbNail;
        TextView tvTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbNail = itemView.findViewById(R.id.img_album);
            tvTitle = itemView.findViewById(R.id.tv_title);

        }

    }
}
