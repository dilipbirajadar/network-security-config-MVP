package com.dilip.networksecurityconfig;

import com.dilip.networksecurityconfig.data.DataSource;
import com.dilip.networksecurityconfig.model.Post;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

public class MainPresenterTest {
    @Mock
    private MainContract.View mView;

    @Mock
    private DataSource mData;

    @Captor
    private ArgumentCaptor<DataSource.GetPostsCallback> mGetPostsCallbackbackCaptor;

    private MainPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mPresenter = new MainPresenter(mData, mView);
    }

    @Test
    public void loadPostsSuccess() throws Exception {
        Post[] posts = new Post[]{
                new Post("Annie", "Hello", null),
                new Post("Ann", "World", null),
                new Post("Droid", "Test", null)
        };
        mPresenter.loadPosts();

        // Data should be loaded from data source and loading indicator should be displayed
        verify(mData).getPosts(mGetPostsCallbackbackCaptor.capture());
        verify(mView).setLoadingPosts(true);

        // Trigger success
        mGetPostsCallbackbackCaptor.getValue().onPostsLoaded(posts);

        // Loading indicator should be hidden and posts should be displayed.
        verify(mView).setLoadingPosts(false);
        verify(mView).setPosts(posts);
        verify(mView).showNoPostsMessage(false);
        verify(mView).hideError();

    }

    @Test
    public void loadPostsError() throws Exception {
        String errorText = "Error!";

        mPresenter.loadPosts();

        // Data should be loaded from data source and loading indicator should be displayed
        verify(mData).getPosts(mGetPostsCallbackbackCaptor.capture());
        verify(mView).setLoadingPosts(true);

        // Trigger error
        mGetPostsCallbackbackCaptor.getValue().onPostsNotAvailable(errorText, null);

        // Loading indicator should be hidden and error should be displayed.
        verify(mView).setLoadingPosts(false);
        verify(mView).showError(anyString(), anyString());
        verify(mView).showNoPostsMessage(true);
        verify(mView).setPosts(null);

    }
}
