package com.ibrahimhmood.torretest.net;

import com.android.volley.VolleyError;

public interface OnResponseReceivedListener
{
    void onResponseReceived(Object response);
    void onErrorReceived(VolleyError error);
}
