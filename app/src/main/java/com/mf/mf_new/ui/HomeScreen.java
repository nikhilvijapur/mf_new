package com.mf.mf_new.ui;

import android.os.Bundle;

import com.mf.mf_new.R;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity {
    InitialLoadingFragment mLoadingFragment;
    HomeScreenFragment mHomeScreenFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        /*mLoadingFragment = (InitialLoadingFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host);
        mLoadingFragment.startProcess(new InitialLoadingFragment.LoadingInterface() {
            @Override
            public void onSuccess() {
                if(HomeScreen.this.isFinishing()) return;
                //mLoadingFragment.getNavController().navigate(R.id.action_initialLoadingFragment_to_homeScreenFragment);
                Toast.makeText(HomeScreen.this, "Success to get content" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(HomeScreen.this, "Failed to get content" , Toast.LENGTH_SHORT).show();
                //mLoadingFragment.getNavController().navigate(R.id.action_initialLoadingFragment_to_loadingError);
            }
        });*/
    }
}
