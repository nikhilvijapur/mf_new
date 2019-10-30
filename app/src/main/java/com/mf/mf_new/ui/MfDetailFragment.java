package com.mf.mf_new.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.mf.mf_new.R;
import com.mf.mf_new.repo.MfDetailItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class MfDetailFragment extends Fragment {

    MfDetailItem mItem;
    public MfDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mf_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mItem = (MfDetailItem) (getArguments() != null ? getArguments().getParcelable("mf_detail") : null);
        ((TextView)view.findViewById(R.id.mf_name)).setText(mItem.getScheme_name());
        ((TextView)view.findViewById(R.id.mf_company)).setText(mItem.getAmc());
        ((TextView)view.findViewById(R.id.mf_description)).setText(mItem.toString());
        ((TextView)view.findViewById(R.id.nav)).setText(mItem.getScheme_min_amt());
        ((TextView)view.findViewById(R.id.start_end_time)).setText(mItem.getScheme_launch_date()+" - " + mItem.getScheme_end_date());


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment newFragment = new GraphFragment(mItem);
        fragmentTransaction.replace(R.id.graph_fragment, newFragment, "fragmentTag");

// fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
