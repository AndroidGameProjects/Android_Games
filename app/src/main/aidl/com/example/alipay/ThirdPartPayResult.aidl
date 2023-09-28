// ThirdPartPayResult.aidl
package com.example.alipay;

interface ThirdPartPayResult {
    void onPaySuccess();
    void onPayFailed(in int errorCode,in String msg);
}