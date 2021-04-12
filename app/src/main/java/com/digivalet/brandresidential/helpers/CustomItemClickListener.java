package com.digivalet.brandresidential.helpers;

public interface CustomItemClickListener {
    default void onItemClickListener(int position){}
    default void onItemClickListener(int position, int view){}
    default void onItemClickListener(int position, String value){}
    default void onItemClickListener(int position, Object value){}

}
