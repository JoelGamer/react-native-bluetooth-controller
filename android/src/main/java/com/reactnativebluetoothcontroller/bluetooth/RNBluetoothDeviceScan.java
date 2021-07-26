package com.reactnativebluetoothcontroller.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RNBluetoothDeviceScan extends BroadcastReceiver {

  private RNBluetoothControllerDeviceScanCallback callback;
  private Map<String, RNBluetoothDevice> scannedDevices;

  public RNBluetoothDeviceScan(RNBluetoothControllerDeviceScanCallback callback) {
    this.callback = callback;
    scannedDevices = new HashMap<>();
  }

  public static IntentFilter intentFilter() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
    intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

    return intentFilter;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    String action = intent.getAction();

    if (BluetoothDevice.ACTION_FOUND.equals(action)) {
      BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

      if (device != null) {
        Log.d(this.getClass().getSimpleName(), device.getAddress());

        if (!scannedDevices.containsKey(device.getAddress())) {
          RNBluetoothDevice scannedDevice = new RNBluetoothDevice(device);
          scannedDevices.put(device.getAddress(), scannedDevice);
          callback.onDeviceScanned(scannedDevice);
        }
      }
    } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
      Log.d(this.getClass().getSimpleName(), "Scan finished with " + scannedDevices.size() + " devices");

      callback.onScanFinished(scannedDevices.values());
      context.unregisterReceiver(this);
    }
  }

  public interface RNBluetoothControllerDeviceScanCallback {
    void onDeviceScanned(RNBluetoothDevice device);
    void onScanFinished(Collection<RNBluetoothDevice> devices);
    void onScanFailed(Throwable throwable);
  }
}
