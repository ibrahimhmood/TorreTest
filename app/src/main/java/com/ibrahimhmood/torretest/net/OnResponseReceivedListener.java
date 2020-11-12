package com.ibrahimhmood.torretest.net;

public interface OnResponseReceivedListener
{
    void onResponseReceived(Object response);
    void onErrorReceived(String error);
}
