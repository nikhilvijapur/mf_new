package com.mf.mf_new.ui.allfunds;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mf.mf_new.R;
import com.mf.mf_new.repo.MfDetailItem;
import com.mf.mf_new.viewmodel.MfDetailItemViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllMfFundsFragment extends Fragment {

    public static final String ACTION_COMPANY_NAME = "company_name";
    private MfDetailItemViewModel mModel;

    private RecyclerView mRecycleView;
    private View mEmptyView, mLoadingView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = ViewModelProviders.of(getActivity()).get(MfDetailItemViewModel.class);
        Toast.makeText(getActivity(), "Fragment created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_mf_funds_fragment, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecycleView = view.findViewById(R.id.recycle_view);
        mEmptyView = view.findViewById(R.id.empty_view);
        mLoadingView = view.findViewById(R.id.loading);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MfAdapter adapter = new MfAdapter(new ArrayList<>());
        mRecycleView.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                mLoadingView.setVisibility(View.GONE);
                if (adapter.getItemCount() == 0) {
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                }
            }
        });

        String companyName = getArguments() != null ? getArguments().getString(ACTION_COMPANY_NAME) : "";
        Log.d("NIKHIL", companyName+"");
        LiveData<List<MfDetailItem>> data = null;
        if(!TextUtils.isEmpty(companyName)) data = mModel.getAll(companyName);
        else data = mModel.getAll();
        data.observe(this, new Observer<List<MfDetailItem>>() {
            @Override
            public void onChanged(List<MfDetailItem> mfDetailItems) {
                MfAdapter adapter = (MfAdapter) mRecycleView.getAdapter();
                adapter.setItems(mfDetailItems);
                adapter.notifyDataSetChanged();
            }
        });

    }
}
