import React, { FC, useEffect, useState } from 'react';
import { StyleSheet, View, Text, FlatList } from 'react-native';
import BluetoothController, {
  BluetoothDevice,
  BluetoothDeviceType,
} from 'react-native-bluetooth-controller';

const App: FC = () => {
  const [bondedDevices, setBondedDevices] = useState<
    BluetoothDevice[] | undefined
  >();
  const [scannedDevices, setScannedDevices] = useState<
    BluetoothDevice[] | undefined
  >();

  useEffect(() => {
    setTimeout(() => {
      BluetoothController.stopScan();
      console.log('stopped!');
    }, 10000);

    BluetoothController.startScan().then(setScannedDevices);
    BluetoothController.getBondedDevices().then(setBondedDevices);
  }, []);

  return (
    <View style={styles.container}>
      <View style={styles.listDevices}>
        <Text>Bonded Devices</Text>
        <FlatList
          data={bondedDevices}
          renderItem={({ item, index }) => (
            <View key={index} style={styles.bondedDevice}>
              <Text>Address: {item.address}</Text>
              <Text>Name: {item.name}</Text>
              <Text>Bonded: {Boolean(item.bonded).toString()}</Text>
              <Text>Type: {BluetoothDeviceType[item.type]}</Text>
            </View>
          )}
        />
      </View>
      <View style={styles.listDevices}>
        <Text>Scanned Devices</Text>
        <FlatList
          data={scannedDevices}
          renderItem={({ item, index }) => (
            <View key={index} style={styles.bondedDevice}>
              <Text>Address: {item.address}</Text>
              <Text>Name: {item.name}</Text>
              <Text>Bonded: {Boolean(item.bonded).toString()}</Text>
              <Text>Type: {BluetoothDeviceType[item.type]}</Text>
            </View>
          )}
        />
      </View>
    </View>
  );
};

export default App;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  listDevices: {
    flex: 1,
    marginVertical: 10,
    padding: 10,
    borderColor: 'black',
    borderWidth: 1,
    width: '100%',
  },
  bondedDevice: {
    marginVertical: 5,
  },
});
