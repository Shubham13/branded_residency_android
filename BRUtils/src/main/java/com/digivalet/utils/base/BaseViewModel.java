package com.digivalet.utils.base;


import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

/**
 * Created by Rupesh Saxena
 */

public abstract class BaseViewModel<N> extends ViewModel {

    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);

    private N mNavigator;

    public BaseViewModel() {

    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    public N getNavigator() {
        return mNavigator;
    }

    public void setNavigator(N navigator) {
        this.mNavigator = navigator;
    }
}
