package com.reactnativebluetoothcontroller;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.reactnativebluetoothcontroller.bluetooth.RNBluetoothController;

@ReactModule(name = BluetoothControllerModule.NAME)
public class BluetoothControllerModule extends ReactContextBaseJavaModule implements ActivityEventListener, LifecycleEventListener {
    private final String TAG = this.getClass().getSimpleName();
    public static final String NAME = "BluetoothController";

    private final RNBluetoothController bluetoothController = new RNBluetoothController();

    public BluetoothControllerModule(ReactApplicationContext reactContext) {
        super(reactContext);

        getReactApplicationContext().addActivityEventListener(this);
        getReactApplicationContext().addLifecycleEventListener(this);
    }

    //region ActivityEventListener

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void onHostResume() {

    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostDestroy() {
      Log.d(TAG, "Activity Lifecycle destroyed");
      stopScan();
    }

    //endregion

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void getBondedDevices(Promise promise) {
      bluetoothController.getBondedDevices(promise);
    }

    @ReactMethod
    public void startScan(Promise promise) {
      bluetoothController.startScan(promise, getReactApplicationContext());
    }

    @ReactMethod
    public void stopScan() {
      bluetoothController.stopScan();
    }
}
