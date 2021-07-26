package com.reactnativebluetoothcontroller.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableArray;

import java.util.Map;

public class RNBluetoothController {

  private final BluetoothAdapter bluetoothAdapter;

  public RNBluetoothController() {
    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
  }

  public void startScan(Promise promise, Context context) {
    WritableArray array = Arguments.createArray();

    BroadcastReceiver deviceScan = new BroadcastReceiver() {
      Map<String, RNBluetoothDevice> scannedDevices;

      public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
          BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

          if (device != null) {
            Log.d(this.getClass().getSimpleName(), device.getAddress());

            if (!scannedDevices.containsKey(device.getAddress())) {
              RNBluetoothDevice scannedDevice = new RNBluetoothDevice(device);
              scannedDevices.put(device.getAddress(), scannedDevice);
            }
          }
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
          Log.d(this.getClass().getSimpleName(), "Scan finished with " + scannedDevices.size() + " devices");

          for (RNBluetoothDevice device : scannedDevices.values()) {
            array.pushMap(device.map());
          }

          context.unregisterReceiver(this);
        }
      }
    };

    context.registerReceiver(deviceScan, RNBluetoothDeviceScan.intentFilter());
    bluetoothAdapter.startDiscovery();
    promise.resolve(array);
  }

  public void stopScan() {
    bluetoothAdapter.cancelDiscovery();
  }

  public void getBondedDevices(Promise promise) {
    WritableArray array = Arguments.createArray();

    for (BluetoothDevice device : bluetoothAdapter.getBondedDevices()) {
      array.pushMap(new RNBluetoothDevice(device).map());
    }

    promise.resolve(array);
  }

  public void a(Promise promise) {

  }
}
