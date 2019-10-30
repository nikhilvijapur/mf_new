package com.mf.mf_new.ui.allmfcategory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mf.mf_new.R;
import com.mf.mf_new.repo.MfCategory;
import com.mf.mf_new.repo.MfCompany;
import com.mf.mf_new.viewmodel.MfDetailItemViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllMfCategoryFragment extends Fragment {
    private MfDetailItemViewModel mModel;

    private RecyclerView mRecycleView;
    private View mEmptyView, mLoadingView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = ViewModelProviders.of(getActivity()).get(MfDetailItemViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_mf_funds_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mRecycleView = view.findViewById(R.id.recycle_view);
        mEmptyView = view.findViewById(R.id.empty_view);
        mLoadingView = view.findViewById(R.id.loading);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MfCategoryAdapter adapter = new MfCategoryAdapter(new ArrayList<>());
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

        mModel.getMfCategories().observe(this, new Observer<List<MfCategory>>() {
            @Override
            public void onChanged(List<MfCategory> mfDetailItems) {
                MfCategoryAdapter adapter = (MfCategoryAdapter) mRecycleView.getAdapter();
                adapter.setItems(mfDetailItems);
                adapter.notifyDataSetChanged();
            }
        });

    }
}
