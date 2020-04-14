package com.debin.photoalbums;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.debin.photoalbums.model.AlbumPhotos;
import com.squareup.picasso.Picasso;

public class AlbumDetailsActivity extends AppCompatActivity {

    private ImageView imgAlbum;
    private TextView tvTitle;
    private TextView tvDes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_details_activity);
        imgAlbum = findViewById(R.id.img_album);
        tvTitle = findViewById(R.id.tv_title);

        String strUrl = getIntent().getStringExtra(AlbumActivity.KEY_URL);
        String strTitle = getIntent().getStringExtra(AlbumActivity.KEY_TITLE);
        String strId = getIntent().getStringExtra(AlbumActivity.KEY_ID);

        Picasso.get().load(strUrl).into(imgAlbum);
        tvTitle.setText(strTitle);

    }
}
