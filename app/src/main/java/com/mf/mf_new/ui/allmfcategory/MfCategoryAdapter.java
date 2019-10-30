package com.mf.mf_new.ui.allmfcategory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mf.mf_new.R;
import com.mf.mf_new.repo.MfCategory;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MfCategoryAdapter extends RecyclerView.Adapter<MfCategoryAdapter.MyViewHolder> {


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View mView;
        MfCategory mDetail;
        MyViewHolder(View view) {
            super(view);
            mView = view;
        }

        public void bind(MfCategory detail) {
            mDetail = detail;
            mView.findViewById(R.id.textView).setVisibility(View.GONE);
            mView.findViewById(R.id.textView3).setVisibility(View.GONE);

            TextView t1 = mView.findViewById(R.id.textView2);
            t1.setText(detail.getCategory());
            TextView t4 = mView.findViewById(R.id.textView4);
            t4.setText(detail.getNoOfMf()+"");

        }
    }
    private List<MfCategory> mList;
    public MfCategoryAdapter(List<MfCategory> list) {
        mList = list;
    }
    public void setItems(List<MfCategory> mfDetailItems) {
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
