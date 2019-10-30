package com.mf.mf_new.ui.allcompany;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mf.mf_new.R;
import com.mf.mf_new.repo.MfCompany;

import java.util.List;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class MfCompanyAdapter extends RecyclerView.Adapter<MfCompanyAdapter.MyViewHolder> {


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View mView;
        MfCompany mDetail;
        MyViewHolder(View view) {
            super(view);
            mView = view;
        }

        public void bind(MfCompany detail) {
            mDetail = detail;
            mView.findViewById(R.id.textView).setVisibility(View.GONE);
            mView.findViewById(R.id.textView3).setVisibility(View.GONE);

            TextView t1 = mView.findViewById(R.id.textView2);
            t1.setText(detail.getAmc());
            TextView t4 = mView.findViewById(R.id.textView4);
            t4.setText(detail.getNoOfMf()+"");


            Bundle args = new Bundle();
            args.putString("company_name",mDetail.getAmc() );
            mView.setOnClickListener(Navigation
                    .createNavigateOnClickListener(R.id.action_allMfFundsCompanyFragment_to_allMfFundsFragment, args));

        }
    }
    private List<MfCompany> mList;
    public MfCompanyAdapter(List<MfCompany> list) {
        mList = list;
    }
    public void setItems(List<MfCompany> mfDetailItems) {
        mList = mfDetailItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mf_detail_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
