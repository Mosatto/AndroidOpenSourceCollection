package com.glumes.opensource.ui.presenter;

import com.glumes.opensource.base.BasePresenter;
import com.glumes.opensource.net.LoadSubscriber;
import com.glumes.opensource.net.entity.BaseResult;
import com.glumes.opensource.ui.contract.InfoContract;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by zhaoying on 16/11/10.
 */

public class InfoPresenter extends BasePresenter<InfoContract.InfoView,InfoContract.Model> implements InfoContract
        .Presenter{


    private boolean isLoading;

    @Inject
    public InfoPresenter(InfoContract.InfoView view, InfoContract.Model model) {
        super(view, model);
    }

    @Override
    public void attachView(InfoContract.InfoView view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }


    @Override
    public void LoadData(String type, int num, int page,boolean showLoding) {
        Timber.d("type is %s , page is %d ,num is %d",type,page,num);
        mSubscription.add(mModel.getData(type, num, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> {
                    isLoading = true;
                    if (showLoding) mView.showLoading();
                })
                .doOnTerminate(() -> {
                    isLoading = false;
                    if (showLoding) mView.dismissLoading();
                })
                .subscribe(new LoadSubscriber<List<BaseResult>>() {
                    @Override
                    protected void onSuccess(List<BaseResult> baseResults) {
                        mView.showContent(baseResults);
                    }

                    @Override
                    protected void onFailed(Throwable e) {
                        mView.showFailed(e);
                    }
                }));
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
