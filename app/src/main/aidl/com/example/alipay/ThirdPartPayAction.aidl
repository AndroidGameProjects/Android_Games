// ThirdPartPayAction.aidl
package com.example.alipay;

import com.example.alipay.ThirdPartPayResult;

interface ThirdPartPayAction {
    //发起支付请求
    void request(String orderInfo,float payMoney,ThirdPartPayResult callback);
}