package com.dilip.networksecurityconfig;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dilip.networksecurityconfig.model.Post;
import com.dilip.networksecurityconfig.ui.PostAdapter;
import com.google.android.material.snackbar.Snackbar;

public class FirstFragment extends Fragment implements MainContract.View{

        private static final String TAG = FirstFragment.class.getSimpleName();
        private MainContract.Presenter mPresenter;

        private ProgressBar mProgressBar;
        private CoordinatorLayout mLayout;
        private RecyclerView mPostList;
        private PostAdapter mPostAdapter;
        private View mEmptyView;
        private Snackbar mErrorSnackbar;

        public FirstFragment() {
            // Required empty public constructor
        }

        public static FirstFragment newInstance() {
            return new FirstFragment();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            PostAdapter.PostAdapterCallback mPostLoadingListener = new PostAdapter.PostAdapterCallback() {

                @Override
                public void onPostImageLoadingError(String error, Exception e) {
                    mPresenter.onLoadPostImageError(error, e);
                }
            };
            mPostAdapter = new PostAdapter(getContext(), mPostLoadingListener);

            // Inflate the layout for this fragment
            View layout = inflater.inflate(R.layout.fragment_first, container, false);
            mProgressBar = layout.findViewById(R.id.progressBar);
            mLayout = layout.findViewById(R.id.layout_coordinator);
            mPostList = layout.findViewById(R.id.post_list);
            mEmptyView = layout.findViewById(R.id.empty_view);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mPostList.setLayoutManager(layoutManager);
            mPostList.setAdapter(mPostAdapter);

            layout.findViewById(R.id.load_posts).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLoadPostsClick();
                }
            });

            return layout;
        }

        @Override
        public void onResume() {
            super.onResume();
            mPresenter.start();
        }

        private void onLoadPostsClick() {
            if (mPresenter == null) {
                return;
            }
            mPresenter.loadPosts();
        }

        @Override
        public void setPresenter(MainContract.Presenter presenter) {
            mPresenter = presenter;
        }

        @Override
        public void setLoadingPosts(boolean isLoading) {
            mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        }

        @Override
        public void setPosts(Post[] posts) {
            mPostAdapter.setPosts(posts);
            if (posts == null || posts.length < 1) {
                mPostList.setVisibility(View.GONE);
            } else {
                mPostList.setVisibility(View.VISIBLE);

            }
        }

        @Override
        public void showError(@NonNull String title, String error) {
            Log.e(TAG, error != null ? error : title);
            mErrorSnackbar = Snackbar.make(mLayout, title, Snackbar.LENGTH_INDEFINITE);
            mErrorSnackbar.show();

        }

        @Override
        public void hideError() {
            if (mErrorSnackbar != null) {
                mErrorSnackbar.dismiss();
            }
        }

        @Override
        public void showNoPostsMessage(boolean showMessage) {
            mEmptyView.setVisibility(showMessage ? View.VISIBLE : View.GONE);
        }
    }

