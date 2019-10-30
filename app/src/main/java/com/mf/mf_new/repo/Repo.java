package com.mf.mf_new.repo;

import android.app.Application;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.mf.mf_new.repo.database.MfDetailDOA;
import com.mf.mf_new.repo.database.MfDetailDataBase;
import com.mf.mf_new.repo.network.Api;
import com.mf.mf_new.repo.network.RetroFitUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Repo {

    public static interface DownloadProgressListener {
        public void inProgress(int percent);
        public void onFailed();
        public void onSuccess();
    }

    private static Repo sInstance;

    private MfDetailDOA mfDetailDOA;
    private LiveData<List<MfDetailItem>> mData;
    private Call<List<MfDetailItem>> mCall;
    private Retrofit mRetroFit;

    public static Repo getInstance(Application app) {
        if(sInstance == null)
            sInstance = new Repo(app);
        return sInstance;
    }

    public static void dispose() {
        RetroFitUtil.dispose();
        sInstance = null;
    }

    private Repo(Application app) {
        mfDetailDOA = MfDetailDataBase.getsInstance(app).MfDoa();
        mData = getAllIntenal();
        mRetroFit = RetroFitUtil.getInstance(app.getApplicationContext()).getRetrofit();
    }

    public LiveData<List<MfDetailItem>> getAll() {
        return mData;
    }

    public LiveData<List<MfDetailItem>> getAll(String companyName) {
        MutableLiveData<List<MfDetailItem>> data = new MutableLiveData<>();
        mRetroFit.create(Api.class).getAllByCompany(companyName).enqueue(new Callback<List<MfDetailItem>>() {
            @Override
            public void onResponse(Call<List<MfDetailItem>> call, Response<List<MfDetailItem>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<MfDetailItem>> call, Throwable t) {
                Log.e("NIKHIL", "onFailure", t);
            }
        });
        return data;
    }

    public LiveData<List<MfDetailItem>> getEntries(String code) {
        Api api =  mRetroFit.create(Api.class);
        MutableLiveData<List<MfDetailItem>> data = new MutableLiveData<>();
        api.getEntries(code).enqueue(new Callback<List<MfDetailItem>>() {
            @Override
            public void onResponse(Call<List<MfDetailItem>> call, Response<List<MfDetailItem>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<MfDetailItem>> call, Throwable t) {
            }
        });
        return data;
    }

    public void downloadAll(final DownloadProgressListener listener) {
        LiveData<List<MfDetailItem>> list = mfDetailDOA.getAll();
        list.observeForever(new Observer<List<MfDetailItem>>() {
            @Override
            public void onChanged(List<MfDetailItem> list) {
                Log.d("NIKHIL",list+"");
                if(mCall != null) return;
                if( list != null && list.size() > 0) {
                    listener.onSuccess();
                    return;
                }
                Api api = mRetroFit.create(Api.class);
                mCall = api.getAll();
                mCall.enqueue(new Callback<List<MfDetailItem>>() {
                    @Override
                    public void onResponse(Call<List<MfDetailItem>> call, Response<List<MfDetailItem>> response) {
                        //response.body();
                        Log.d("NIKHIL", response.errorBody() + "");
                        Log.d("NIKHIL", response.message() + "");
                        Log.d("NIKHIL", response.body() + "");
                        // mRecycleView.setAdapter(new MfAdapter(response.body()));
                        Completable.fromAction(new Action() {
                            @Override
                            public void run() throws Exception {
                                List<MfDetailItem> list = response.body();
                                int max = list.size();
                                int count =0;
                                for (MfDetailItem item : list) {
                                    mfDetailDOA.insert(item);
                                    count++;
                                    listener.inProgress((int)(count*100)/max);
                                }
                            }
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        Log.d("NIKHIL", "Loaded to DB from internet");
                                        mCall = null;
                                        listener.onSuccess();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("NIKHIL", "Failed to load to DB from internet", e);
                                        mCall = null;
                                        listener.onFailed();
                                    }
                                });
                    }

                    @Override
                    public void onFailure(Call<List<MfDetailItem>> call, Throwable t) {
                        Log.d("NIKHIL", t.getMessage() + "");
                        Log.e("NIKHIL", "ERROR", t);
                        mCall = null;
                        listener.onFailed();
                    }
                });
            }
        });

    }

    private LiveData<List<MfDetailItem>> getAllIntenal() {
        LiveData<List<MfDetailItem>> list = mfDetailDOA.getAll();
        /*list.observeForever(new Observer<List<MfDetailItem>>() {
            @Override
            public void onChanged(List<MfDetailItem> list) {
                if((list != null && list.size() >0) || mCall != null) return;

                Log.d("NIKHIL", "Starting network call");
                //start network operation.
                Api api = RetroFitUtil.getInstance().getRetrofit().create(Api.class);
                mCall = api.getAll();
                mCall.enqueue(new Callback<List<MfDetailItem>>() {
                    @Override
                    public void onResponse(Call<List<MfDetailItem>> call, Response<List<MfDetailItem>> response) {
                        //response.body();
                        Log.d("NIKHIL", response.errorBody() + "");
                        Log.d("NIKHIL", response.message() + "");
                        Log.d("NIKHIL", response.body() + "");
                        // mRecycleView.setAdapter(new MfAdapter(response.body()));
                        Completable.fromAction(new Action() {
                            @Override
                            public void run() throws Exception {
                                List<MfDetailItem> list = response.body();
                                for (MfDetailItem item : list) {
                                    mfDetailDOA.insert(item);
                                }
                            }
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        Log.d("NIKHIL", "Loaded to DB from internet");
                                        mCall = null;
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("NIKHIL", "Failed to load to DB from internet", e);
                                        mCall = null;

                                    }
                                });
                    }

                    @Override
                    public void onFailure(Call<List<MfDetailItem>> call, Throwable t) {
                        Log.d("NIKHIL", t.getMessage() + "");
                        Log.e("NIKHIL", "ERROR", t);
                        mCall = null;
                    }
                });

            }
        });*/
        return list;
    }

    public LiveData<List<MfCompany>> getCompanies() {
        MutableLiveData<List<MfCompany>> data = new MutableLiveData<>();
        mData.observeForever(new Observer<List<MfDetailItem>>() {
            @Override
            public void onChanged(List<MfDetailItem> mfDetailItems) {
                applyFilter(data, mfDetailItems);
            }
        });
        applyFilter(data, mData.getValue());
        return data;
    }

    private void applyFilter(MutableLiveData<List<MfCompany>> data, List<MfDetailItem> list) {
        List<MfCompany> mList = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        if (list != null) {
            for (MfDetailItem item : list) {
                map.put(item.getAmc(), map.getOrDefault(item.getAmc(), 0)+1);
            }
            for(String val : map.keySet()) {
                mList.add(new MfCompany(val, map.get(val)));
            }
            Log.d("NIKHIL" , mList.size()+"");
            data.postValue(mList);
        }
    }

    public void deleteAll() {
        mfDetailDOA.deleteAll();
    }

    public LiveData<List<MfCategory>> getMfCategories() {
        MutableLiveData<List<MfCategory>> data = new MutableLiveData<>();
        mData.observeForever(new Observer<List<MfDetailItem>>() {
            @Override
            public void onChanged(List<MfDetailItem> mfDetailItems) {
                applyCategoryFilter(data, mfDetailItems);
            }
        });
        applyCategoryFilter(data, mData.getValue());
        return data;
    }
    private void applyCategoryFilter(MutableLiveData<List<MfCategory>> data, List<MfDetailItem> list) {
        List<MfCategory> mList = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        if (list != null) {
            for (MfDetailItem item : list) {
                map.put(item.getScheme_cat(), map.getOrDefault(item.getScheme_cat(), 0)+1);
            }
            for(String val : map.keySet()) {
                mList.add(new MfCategory(val, map.get(val)));
            }
            data.postValue(mList);
        }
    }

    public LiveData<List<MfGraphDetailItem>> getGraphDetail(int code) {

        Api api =  mRetroFit.create(Api.class);
        MutableLiveData<List<MfGraphDetailItem>> data = new MutableLiveData<>();
        api.getGraphEntries(code).enqueue(new Callback<List<MfGraphDetailItem>>() {
            @Override
            public void onResponse(Call<List<MfGraphDetailItem>> call, Response<List<MfGraphDetailItem>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<MfGraphDetailItem>> call, Throwable t) {
            }
        });
        return data;
    }
}
