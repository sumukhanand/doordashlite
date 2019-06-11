package com.sumukha.doordashlite;

import android.app.Application;

import com.sumukha.doordashlite.data.data_sources.DaggerRemoteDataSource_Component;
import com.sumukha.doordashlite.data.data_sources.RemoteDataSource;

public class DoorDashLiteApplication extends Application {

    private static RemoteDataSource.Component component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerRemoteDataSource_Component
                .builder()
                .build();
    }

    public static RemoteDataSource.Component getComponent() {
        return component;
    }

}
