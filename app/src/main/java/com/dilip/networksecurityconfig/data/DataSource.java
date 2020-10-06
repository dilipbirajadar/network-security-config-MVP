package com.dilip.networksecurityconfig.data;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.dilip.networksecurityconfig.model.Post;

public interface DataSource {
    interface GetPostsCallback {
        void onPostsLoaded(Post[] posts);

        void onPostsNotAvailable(String error, Throwable cause);
    }

    interface GetImageCallback {
        void onImageLoaded(Bitmap image);

        void onImageNotAvailable(String error);
    }


    void getPosts(@NonNull GetPostsCallback getPostsCallback);

    void getPostImage(@NonNull String url, @NonNull GetImageCallback getImageCallback);
}
