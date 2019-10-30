package com.mf.mf_new.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mf.mf_new.R;
import com.mf.mf_new.repo.Repo;
import com.mf.mf_new.viewmodel.MfDetailItemViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class InitialLoadingFragment extends Fragment {
    public static interface LoadingInterface {
        public void onSuccess();
        public void onFailure();
    }
    private MfDetailItemViewModel mModel;

    private ProgressBar mProgressBar= null;
    private NavController mController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = ViewModelProviders.of(getActivity()).get(MfDetailItemViewModel.class);
        mModel.refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.initial_loading_data, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        mProgressBar.setMax(100);
        Log.d("NIKHIL", "onViewCreated");
        mController = Navigation.findNavController(getView());
        startProcess();
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);

    }

    private void startProcess() {
        mModel.downloadAllContent(new Repo.DownloadProgressListener() {

            @Override
            public void inProgress(int percent) {
                mProgressBar.setProgress(percent);
            }

            @Override
            public void onFailed() {
                Log.d("NIKHIL", "failed");
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                NavOptions navOptions = navBuilder.setPopUpTo(R.id.initialLoadingFragment, true).build();
                mController.navigate(R.id.action_initialLoadingFragment_to_loadingError, null, navOptions);
                //getNavController().navigate(R.id.action_initialLoadingFragment_to_loadingError);
            }

            @Override
            public void onSuccess() {
                Log.d("NIKHIL", "onsuccess");
                if(isVisible()) {
                    NavOptions.Builder navBuilder = new NavOptions.Builder();
                    NavOptions navOptions = navBuilder.setPopUpTo(R.id.initialLoadingFragment, true).build();
                    mController.navigate(R.id.action_initialLoadingFragment_to_homeScreenFragment, null, navOptions);
                }

            }
        });
    }
}
