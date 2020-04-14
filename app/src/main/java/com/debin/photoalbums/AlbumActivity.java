package com.debin.photoalbums;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.debin.photoalbums.Utilities.AlbumClickListner;
import com.debin.photoalbums.Utilities.PaginationScrollListener;
import com.debin.photoalbums.Utilities.Util;
import com.debin.photoalbums.adapter.PagenationAdapter;
import com.debin.photoalbums.model.AlbumPhotos;
import com.debin.photoalbums.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivity extends AppCompatActivity implements AlbumClickListner {

    private RecyclerView recyclerView;
    private PagenationAdapter pagenationAdapter;
    private ProgressBar progressBar;
    private static final String TAG = "AlbumActivity";
    public static final String KEY_URL = "AlbumDetailsUrl";
    public static final String KEY_TITLE = "AlbumDetailsTitle";
    public static final String KEY_ID = "id";
    private RetrofitInstance retrofitInstance = RetrofitInstance.getInstance();

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        loadFirstPage();
    }

    private Call<List<AlbumPhotos>> getAlbumList() {
        return retrofitInstance.getApi().getAlbumPhotos(currentPage);
    }

    private void loadFirstPage() {
        currentPage = PAGE_START;
        getAlbumList().enqueue(new Callback<List<AlbumPhotos>>() {
            @Override
            public void onResponse(Call<List<AlbumPhotos>> call, Response<List<AlbumPhotos>> response) {
                progressBar.setVisibility(View.GONE);
                if(response.raw().cacheResponse()!=null) {
                    // true: response was served from cache
                    Log.i(TAG,"Response from cache");
                } else if (response.raw().networkResponse() != null
                        && response.raw().networkResponse() == null) {
                    // true: response was served from network/server
                    Log.i(TAG,"Response from server");
                }
                generateDataList(response.body());
                if (currentPage <= TOTAL_PAGES) {
                   // adapter.addLoadingFooter();
                } else{ isLastPage = true;}
            }

            @Override
            public void onFailure(Call<List<AlbumPhotos>> call, Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
                Util.ToastMessage(getApplicationContext(), "Something went wrong...Please try later!");
            }
        });

    }

    private void loadNextPage() {
        getAlbumList().enqueue(new Callback<List<AlbumPhotos>>() {
            @Override
            public void onResponse(Call<List<AlbumPhotos>> call, Response<List<AlbumPhotos>> response) {
                //pagenationAdapter.removeLoadingFooter();
                isLoading = false;
                generateDataList(response.body());
                if (currentPage != TOTAL_PAGES) {
                    //pagenationAdapter.addLoadingFooter();
                } else { isLastPage = true; }
            }
            @Override
            public void onFailure(Call<List<AlbumPhotos>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void generateDataList(List<AlbumPhotos> albumPhotos) {
        Log.i(TAG,"Getting the list");
        pagenationAdapter = new PagenationAdapter(albumPhotos,this);
        layoutManager =  new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pagenationAdapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                new Handler().postDelayed(() -> {loadNextPage();},1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public void onAlbumClick(AlbumPhotos albumPhotos) {
        Intent toDetailsActivity = new Intent(this, AlbumDetailsActivity.class);
        toDetailsActivity.putExtra(KEY_URL,  albumPhotos.getUrl());
        toDetailsActivity.putExtra(KEY_TITLE, albumPhotos.getTitle());
        toDetailsActivity.putExtra(KEY_ID,albumPhotos.getId());
        startActivity(toDetailsActivity);

    }
}
