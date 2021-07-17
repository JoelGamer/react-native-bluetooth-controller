import { NativeModules } from 'react-native';

type BluetoothControllerType = {
  multiply(a: number, b: number): Promise<number>;
};

const { BluetoothController } = NativeModules;

export default BluetoothController as BluetoothControllerType;
