package com.ethanco.mytests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity implements MyView {

    private MyViewModel mViewModel;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tvInfo = (TextView) findViewById(R.id.tvInfo);


        mViewModel = new MyViewModel();
        mViewModel.attachView(this);

        mViewModel.loadData();
    }

    @Override
    protected void onDestroy() {
        mViewModel.detachView();
        super.onDestroy();
    }

    @Override
    public void show(String message) {
        Toast.makeText(SecondActivity.this, message, Toast.LENGTH_SHORT).show();
        tvInfo.setText(message);
    }
}
