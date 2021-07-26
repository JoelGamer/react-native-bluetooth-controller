package com.reactnativebluetoothcontroller.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.Build;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

public class RNBluetoothDevice {

  private final BluetoothDevice nativeBluetoothDevice;

  public RNBluetoothDevice(BluetoothDevice nativeBluetoothDevice) {
    this.nativeBluetoothDevice = nativeBluetoothDevice;
  }

  public WritableMap map() {
    WritableMap writableMap = Arguments.createMap();

    writableMap.putString("address", nativeBluetoothDevice.getAddress());
    writableMap.putString("name", nativeBluetoothDevice.getName());
    writableMap.putBoolean("bonded", nativeBluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED);
    writableMap.putInt("type", nativeBluetoothDevice.getType());

    return writableMap;
  }
}
