package com.paragon.utils.anim;

/**
 * Created by Rupesh Saxena
 */

public interface SpringListener {

    /*
     * hits when Spring is Active
     * */
    void onSpringStart();

    /*
     * hits when Spring is inActive
     * */

    void onSpringStop();
}
