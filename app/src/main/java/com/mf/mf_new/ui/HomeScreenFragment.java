package com.mf.mf_new.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mf.mf_new.R;
import com.mf.mf_new.viewmodel.MfDetailItemViewModel;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class HomeScreenFragment extends androidx.fragment.app.Fragment {

    private MfDetailItemViewModel mModel;
    private NavController mNavController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = ViewModelProviders.of(getActivity()).get(MfDetailItemViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_screen_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
        view.findViewById(R.id.view_all_funds_by_company).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeScreenFragment_to_allMfFundsCompanyFragment));
        view.findViewById(R.id.view_all_funds).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeScreenFragment_to_allMfFundsFragment));
        view.findViewById(R.id.mf_categoties).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeScreenFragment_to_allMfCategotyFragment));
        view.findViewById(R.id.clear_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewModelProviders.of(getActivity()).get(MfDetailItemViewModel.class).deleteAll();
            }
        });
    }

}
