package com.example.superquickstart;

import android.app.Application;

import androidx.annotation.NonNull;

import com.wenchao.common.base.BaseViewModel;

/**
 * @author wenchao
 * Date 2019/8/13 16:38
 * Description
 */
public class MainViewModel extends BaseViewModel<DemoRepository> {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MainViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);
    }
}
