package com.dilip.networksecurityconfig.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.dilip.networksecurityconfig.R;
import com.dilip.networksecurityconfig.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private static final String TAG = "PostAdapter";
    private final PostAdapterCallback mCallback;
    private Post[] mPosts = new Post[0];
    private final ImageLoader mImageLoader;

    public interface PostAdapterCallback {
        void onPostImageLoadingError(String error, Exception e);
    }

    public PostAdapter(Context context, PostAdapterCallback callback) {
        mCallback = callback;

        // Set up a new ImageLoader that does not cache any requests.
        mImageLoader = new ImageLoader(Volley.newRequestQueue(context), new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                // Do not cache this bitmap.
            }
        }) {
            @Override
            protected void onGetImageError(String cacheKey, VolleyError error) {
                super.onGetImageError(cacheKey, error);
                mCallback.onPostImageLoadingError(error.getMessage(), error);
            }
        };
    }


    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        PostViewHolder holder = new PostViewHolder(layout);
        holder.image.setDefaultImageResId(R.drawable.ic_loading);
        holder.image.setErrorImageResId(R.drawable.ic_error);
        return holder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = mPosts[position];
        holder.name.setText(post.name);
        holder.text.setText(post.message);
        holder.image.setImageUrl(post.profileImage, mImageLoader);

        Log.d(TAG, "loading image: " + post.profileImage);
    }

    public void setPosts(Post[] posts) {
        mPosts = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPosts.length;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView image;
        TextView name;
        TextView text;

        public PostViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.post_image);
            name = itemView.findViewById(R.id.post_name);
            text = itemView.findViewById(R.id.post_text);
        }
    }
}

