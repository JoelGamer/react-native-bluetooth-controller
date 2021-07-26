import { NativeModules } from 'react-native';
import type { BluetoothControllerType } from './@types';

const { BluetoothController } = NativeModules;

export default BluetoothController as BluetoothControllerType;
export * from './@types';
