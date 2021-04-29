package com.paragon.utils.base;

import com.paragon.utils.networking.NetworkResponseCallback;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Rupesh Saxena
 */

public abstract class BaseModel<S, T> implements Serializable {
    public abstract void doNetworkRequest(HashMap<S, T> data, NetworkResponseCallback networkResponseCallback);
}


