package com.debin.photoalbums.network;

import com.debin.photoalbums.model.AlbumPhotos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    @GET("albums/{album_id}/photos")
    Call<List<AlbumPhotos>> getAlbumPhotos(
            @Path("album_id") int albumId
    );
}
