package com.digivalet.utils.base;

import com.digivalet.utils.networking.NetworkResponseCallback;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Rupesh Saxena
 */

public abstract class BaseModel<S, T> implements Serializable {
    public abstract void doNetworkRequest(HashMap<S, T> data, NetworkResponseCallback networkResponseCallback);
}


