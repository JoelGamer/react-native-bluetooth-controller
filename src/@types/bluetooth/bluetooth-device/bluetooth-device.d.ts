export interface BluetoothDevice {
  address: string;
  name: string;
  bonded: boolean;
  type: BluetoothDeviceType;
}
