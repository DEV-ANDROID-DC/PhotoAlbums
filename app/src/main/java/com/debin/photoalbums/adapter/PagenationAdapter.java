//package com.debin.photoalbums.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.debin.photoalbums.R;
//import com.debin.photoalbums.model.AlbumPhotos;
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
//public class PagenationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    private static final int ITEM = 0;
//    private static final int LOADING = 1;
//
//    private boolean isLoadingAdded = false;
//    private boolean retryPageLoad = false;
//
//    List<AlbumPhotos> albumPhotos;
//
//    public PagenationAdapter(List<AlbumPhotos> albumPhotos) {
//        this.albumPhotos = albumPhotos;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        RecyclerView.ViewHolder viewHolder = null;
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        switch (viewType) {
//            case ITEM:
//                View viewItem = inflater.inflate(R.layout.album_item, parent, false);
//                viewHolder = new AlbumViewHolder(viewItem);
//                break;
//            case LOADING:
//                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
//                viewHolder = new LoadingViewHolder(viewLoading);
//                break;
//        }
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        AlbumPhotos photos = albumPhotos.get(position);
//        switch (getItemViewType(position)) {
//            case ITEM:
//                Picasso.get().load(albumPhotos.get(position).getThumbnailUrl()).into(holder.imgThumbNail);
//                holder.tvTitle.setText(albumPhotos.get(position).getTitle());
//                break;
//            case LOADING:
//                //LoadingViewHolder loadingVH = (LoadingViewHolder) holder;
//                LoadingViewHolder.mProgressBar.setVisibility(View.VISIBLE);
//                break;
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return albumPhotos == null ? 0 : albumPhotos.size();;
//    }
//
//    protected class AlbumViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView imgThumbNail;
//        TextView tvTitle;
//
//        public AlbumViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imgThumbNail = itemView.findViewById(R.id.img_album);
//            tvTitle = itemView.findViewById(R.id.tv_title);
//        }
//    }
//
//    protected class LoadingViewHolder extends RecyclerView.ViewHolder {
//        private ProgressBar mProgressBar;
//        public LoadingViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
//        }
//    }
//
// }
