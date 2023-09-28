package com.example.ex42;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

public class PayService extends Service {

    private static final String TAG = "PayService";
    private ThirdPartPayImpl mThirdPartPay;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onBind: -> action -> " + action);
        if (action != null && "com.example.alipay.THIRD_PART_PAY".equals(action)){
            mThirdPartPay = new ThirdPartPayImpl();
            return mThirdPartPay;
        }
        return new PalAction();
    }


    public class PalAction extends Binder {
        public void Pay(float payMoney){
            if (mThirdPartPay != null){
                mThirdPartPay.paySuccess();
            }
        }

        public void onUserCancel(){
            //用户点击界面上的取消/退出
            if (mThirdPartPay != null){
                mThirdPartPay.payFailed(1,"user cancel pay");
            }
        }
    }


    private class ThirdPartPayImpl extends ThirdPartPayAction.Stub{
        private ThirdPartPayResult mCallback;
        @Override
        public void request(String orderInfo, float payMoney, ThirdPartPayResult callback) throws RemoteException {
            this.mCallback = callback;
            //第三方应用发起请求，打开一个支付界面
            //TODO:
            Intent intent = new Intent();
            intent.setClass(PayService.this,PayActivity.class);
            intent.putExtra(Constants.KEY_BILL_INFO,orderInfo);
            intent.putExtra(Constants.KEY_PAY_MONEY,payMoney);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        public void paySuccess(){
            try {
                if (mCallback != null) {
                    mCallback.onPaySuccess();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void payFailed(int errorCode, String errorMsg){
            if (mCallback != null){
                try {
                    mCallback.onPayFailed(errorCode,errorMsg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
