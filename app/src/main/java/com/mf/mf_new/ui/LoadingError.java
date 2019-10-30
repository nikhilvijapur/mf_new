package com.mf.mf_new.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mf.mf_new.R;
import com.mf.mf_new.SharedPrefUtils;

public class LoadingError extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading_error, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText editText = (EditText) view.findViewById(R.id.url_field);
        editText.setText(SharedPrefUtils.getBaseUrl(getContext()));
        view.findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefUtils.setBaseUrl(getContext(), editText.getText()+"");
                Navigation.findNavController(view).navigate(R.id.action_loadingError_to_initialLoadingFragment);
            }
        });

    }

}
