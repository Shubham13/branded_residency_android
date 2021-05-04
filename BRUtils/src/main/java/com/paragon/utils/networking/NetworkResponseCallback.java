package com.paragon.utils.networking;


public interface NetworkResponseCallback<T> {


    default void onShowProgress() {
    }

    void onResponse(T object);

    void onFailure(String error);

    default void onInternetDisable() {
    }

    default void onSessionExpire() {
    }

    default void onHideProgress() {
    }
}
