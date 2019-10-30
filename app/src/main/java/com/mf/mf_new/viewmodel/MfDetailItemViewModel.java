package com.mf.mf_new.viewmodel;

import android.app.Application;
import android.widget.Toast;

import com.mf.mf_new.repo.MfCategory;
import com.mf.mf_new.repo.MfCompany;
import com.mf.mf_new.repo.MfDetailItem;
import com.mf.mf_new.repo.MfGraphDetailItem;
import com.mf.mf_new.repo.Repo;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class MfDetailItemViewModel extends AndroidViewModel {
    Repo mRepo;
    private Application mApp;
    public MfDetailItemViewModel(Application app) {
        super(app);
        mApp = app;
       refresh();
    }
    public void refresh() {
        Repo.dispose();
        mRepo = Repo.getInstance(mApp);
    }


    public LiveData<List<MfDetailItem>> getAll(){
        return mRepo.getAll();
    }
    public LiveData<List<MfDetailItem>> getAll(String companyName){
        return mRepo.getAll(companyName);
    }
    public LiveData<List<MfCompany>> getCompanies(){
        return mRepo.getCompanies();
    }

    public LiveData<List<MfCategory>> getMfCategories(){
        return mRepo.getMfCategories();
    }

    public void deleteAll() {
        Completable.fromAction(()->mRepo.deleteAll())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(getApplication().getApplicationContext(), "Cleared Cache", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplication().getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void downloadAllContent(Repo.DownloadProgressListener listener) {
        mRepo.downloadAll(listener);
    }

    public LiveData<List<MfGraphDetailItem>> getGraphDetails(int code) {
        return mRepo.getGraphDetail(code);
    }

}
