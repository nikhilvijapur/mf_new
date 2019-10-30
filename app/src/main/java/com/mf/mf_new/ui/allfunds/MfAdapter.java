package com.mf.mf_new.ui.allfunds;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mf.mf_new.repo.MfDetailItem;
import com.mf.mf_new.R;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MfAdapter extends RecyclerView.Adapter<MfAdapter.MyViewHolder> implements Filterable {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View mView;

        MyViewHolder(View view) {
            super(view);
            mView = view;
        }

        public void bind(MfDetailItem detail) {
            TextView t1 = mView.findViewById(R.id.textView2);
            t1.setText(detail.getScheme_name());

            TextView t2 = mView.findViewById(R.id.textView);
            t2.setText(detail.getAmc());
            TextView t3 = mView.findViewById(R.id.textView3);
            t3.setText(detail.getScheme_type());
            TextView t4 = mView.findViewById(R.id.textView4);
            t4.setText(detail.getScheme_min_amt());


            Bundle args = new Bundle();
            args.putParcelable("mf_detail",detail );
            mView.setOnClickListener(Navigation
                    .createNavigateOnClickListener(R.id.action_allMfFundsFragment_to_mfDetailFragment, args));
        }
    }

    private List<MfDetailItem> mList;
    private List<MfDetailItem> mMainList;

    public MfAdapter(List<MfDetailItem> list) {

        mList = list;
        mMainList = list;
    }

    public void setItems(List<MfDetailItem> mfDetailItems) {
        mList = mfDetailItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mf_detail_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                List<MfDetailItem> filteredList = new ArrayList<>();

                if (charString.isEmpty()) {

                    filteredList = mMainList;
                } else {



                    for (MfDetailItem item : mMainList) {
                        if (item.toString().contains(charSequence))
                            filteredList.add(item);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mList = (List<MfDetailItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
