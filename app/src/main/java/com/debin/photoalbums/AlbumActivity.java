package com.debin.photoalbums;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.debin.photoalbums.Utilities.Util;
import com.debin.photoalbums.adapter.PhotoAdapter;
import com.debin.photoalbums.model.AlbumPhotos;
import com.debin.photoalbums.network.Api;
import com.debin.photoalbums.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    //private PagenationAdapter pagenationAdapter;
    private ProgressBar progressBar;
    private static final String TAG = "AlbumActivity";
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

        //loadFirstPage();

        retrofitInstance.getApi().getAlbumPhotos(1)
                .enqueue(new Callback<List<AlbumPhotos>>() {
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
            }

            @Override
            public void onFailure(Call<List<AlbumPhotos>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Util.ToastMessage(getApplicationContext(), "Something went wrong...Please try later!");
            }
        });
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
                //adapter.removeLoadingFooter();
                isLoading = false;
                generateDataList(response.body());
                if (currentPage != TOTAL_PAGES) {
                    //adapter.addLoadingFooter();
                } else { isLastPage = true; }
            }
            @Override
            public void onFailure(Call<List<AlbumPhotos>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void generateDataList(List<AlbumPhotos> albumPhotos) {
        photoAdapter = new PhotoAdapter(albumPhotos);
        //pagenationAdapter = new PagenationAdapter(albumPhotos);
        layoutManager =  new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(photoAdapter);

//        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
//            @Override
//            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage += 1;
//                new Handler().postDelayed(() -> {loadNextPage();},1000);
//            }
//
//            @Override
//            public int getTotalPageCount() {
//                return TOTAL_PAGES;
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//        });
    }

}
