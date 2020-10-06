package com.dilip.networksecurityconfig;

import com.dilip.networksecurityconfig.model.Post;

public interface MainContract {
    interface View {
        /**
         * Sets the presenter for interaction from the View.
         *
         * @param presenter
         */
        void setPresenter(Presenter presenter);

        /**
         * Displays or hides a loading indicator.
         *
         * @param isLoading If true, display a loading indicator, hide it otherwise.
         */
        void setLoadingPosts(boolean isLoading);

        /**
         * Displays a list of posts on screen.
         *
         * @param posts The posts to display. If null or empty, the list should not be shown.
         */
        void setPosts(Post[] posts);

        /**
         * Displays an error message on screen and optionally prints out the error to logcat.
         */
        void showError(String title, String error);

        /**
         * Hides the error message.
         *
         * @see #showError(String, String)
         */
        void hideError();

        /**
         * Displays an empty message and icon.
         *
         * @param showMessage If true, the message is show. If false, the message is hidden
         */
        void showNoPostsMessage(boolean showMessage);
    }

    interface Presenter {
        /**
         * Call to start the application. Sets up initial state.
         */
        void start();

        /**
         * Loads post for display.
         */
        void loadPosts();

        /**
         * An error was encountered during the loading of profile images.
         */
        void onLoadPostImageError(String error, Exception e);
    }
}
