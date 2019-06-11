package com.sumukha.doordashlite.ui.views;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.sumukha.doordashlite.R;
import com.sumukha.doordashlite.ui.presenters.BasePresenter;

public abstract class BaseView extends AppCompatActivity implements View {

    protected BasePresenter presenter;

    public BaseView() {
        super();
    }

    @Override
    protected void onDestroy() {

        // Decouple presenter and view for garbage collection
        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
        }

        super.onDestroy();
    }

    public void onFetchDataError(@NonNull Throwable e) {
        Log.e("[BaseView]", e.toString());
        Toast.makeText(this, R.string.toast_error, Toast.LENGTH_SHORT);
    }
}