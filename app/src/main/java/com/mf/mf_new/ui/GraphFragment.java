package com.mf.mf_new.ui;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.mf.mf_new.R;
import com.mf.mf_new.repo.MfDetailItem;
import com.mf.mf_new.repo.MfGraphDetailItem;
import com.mf.mf_new.ui.allfunds.MfAdapter;
import com.mf.mf_new.viewmodel.MfDetailItemViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class GraphFragment extends Fragment {
    MfDetailItemViewModel mModel;
    private MfDetailItem mItem;

    public GraphFragment(MfDetailItem item) {
        mItem = item;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = ViewModelProviders.of(getActivity()).get(MfDetailItemViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.graph_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startProcess(Integer.valueOf(mItem.getCode()));
    }

    public void startProcess(int code) {
        LiveData<List<MfGraphDetailItem>> data = mModel.getGraphDetails(code);
        data.observe(this, new Observer<List<MfGraphDetailItem>>() {
            @Override
            public void onChanged(List<MfGraphDetailItem> list) {
                Log.d("NIKHIL", list+"");
                getView().findViewById(R.id.progressBar_download).setVisibility(View.GONE);
                setChartData(list);
            }
        });
    }

    private void setChartData(List<MfGraphDetailItem> list) {
        LineChart chart = (LineChart)getView().findViewById(R.id.chart);
        chart.setVisibility(View.VISIBLE);
        if(list == null || list.size() == 0) return;
        chart.setTouchEnabled(true);
        chart.setDrawGridBackground(false);
        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);

        float max = 0f;
        for(MfGraphDetailItem item: list) {
            if(max < item.getNav())
                max = item.getNav();
        }

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            chart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(max+10f);
            yAxis.setAxisMinimum(0f);
        }

        chart.animateX(500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            float val = list.get(i).getNav();
            values.add(new Entry(i, val, list.get(i)));
        }

        //----------------------------------------------------------
        //----------------------------------------------------------
        //----------------------------------------------------------



        LineDataSet set1;
        // create a dataset and give it a type
        set1 = new LineDataSet(values, "NAV");

        set1.setDrawIcons(false);

        // draw dashed line
        set1.enableDashedLine(1f, 2f, 0f);
        set1.setDrawCircles(false);

        // black lines and points
        set1.setColor(Color.BLUE);
        set1.setCircleColor(Color.RED);

        // line thickness and point size
        set1.setLineWidth(0.5f);
        set1.setCircleRadius(0.003f);

        // draw points as solid circles
        set1.setDrawCircleHole(false);

        // customize legend entry
        set1.setFormLineWidth(0.1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{2f, 1f}, 0f));
        set1.setFormSize(15.f);

        // text size of values
        set1.setValueTextSize(9f);

        // draw selection line as dashed
        set1.enableDashedHighlightLine(1f, 2f, 0f);

        // set the filled area
        set1.setDrawFilled(true);
        set1.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return chart.getAxisLeft().getAxisMinimum();
            }
        });

        // set color of filled area
        /*if (Utils.getSDKInt() >= 18) {
            // drawables only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
            set1.setFillDrawable(drawable);
        } else */{
            set1.setFillColor(Color.BLUE);
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets

        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // set data
        chart.setData(data);

    }
}
