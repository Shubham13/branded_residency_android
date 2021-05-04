package com.paragon.sensonic.network;

public interface NetworkResponseCallback {
    public void onSuccess(String response);
    public void onFailure(String error);
}
