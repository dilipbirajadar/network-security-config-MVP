package com.dilip.networksecurityconfig;

import android.content.Context;

import androidx.annotation.NonNull;

import com.dilip.networksecurityconfig.data.DataSource;
import com.dilip.networksecurityconfig.data.remote.RemoteDataSource;

public class Injection {
    public static DataSource provideDataSource(@NonNull Context context) {
        return new RemoteDataSource("https://storage.googleapis.com/network-security-conf-codelab.appspot.com/v1/posts.json");
    }
}
