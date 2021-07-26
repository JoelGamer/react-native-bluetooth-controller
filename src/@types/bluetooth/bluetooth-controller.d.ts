export interface BluetoothControllerType {
  startScan(): void;
  getBondedDevices(): Promise<BluetoothDevice[]>;
}
