package com.example.superquickstart;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.example.superquickstart.databinding.ActivityMainBinding;
import com.wenchao.common.BR;
import com.wenchao.common.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding,MainViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.mainModel;
    }
}
