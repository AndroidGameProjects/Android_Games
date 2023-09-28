// ThirdPartPayAction.aidl
package com.example.alipay;

import com.example.alipay.ThirdPartPayResult;

interface ThirdPartPayAction {
    void request(String orderInfo,float payMoney,ThirdPartPayResult callback);
}