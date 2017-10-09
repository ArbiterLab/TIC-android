# Library TIC for Android

Library TIC, the simple modifier of IoT API. our library helping developer of generating device API, and developer of using API for 3-rd party application.

this time, you can use TIC-ardunio repository for generating your API

[TIC-arudino]: https://github.com/ArbiterLab/TIC-android

and using there API on android with TIC-android.

# How to use?

Assume API you want to use is created successfully, you can using there API following this step.

First, you shuld verify if device supports bluetooth connect and it can use with our methods.

```java
if (!TICUtils.isDeviceSupportBluetooth()) return;
if (!TICUtils.isBluetoothEnabled()) {
	Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	startActivityForResult(enableBtIntent, 100);
}
```

Whichever method you use, you can use the library with Bluetooth permissions.



Construct TICPair for searching bluetooth devices, make connection with devices.

```java
TICPair ticPair = new TICPair(this);
```

and you can search nearby devices with TICPair.

```java
mTICPair.searchDevices(new OnDeviceDetectedListener() {
	@Override
	public void onDetect(BluetoothDevice bluetoothDevice) {
	// you can check your bluetooth device, and connect on this block
});
```

​	

and you can generating Trust IoT Connection with searched bluetoothDevice.

```java
final TIC tic = mTICPair.connect(bluetoothDevice, new ConnectionStateListener() {
    @Override
    public void onStateChanged(TIC connection, boolean isConnected, String message) {
    }

    @Override
    public void onMessage(TIC connection, int bytes, byte[] message) {
    }
});
```



As a result, this looks like code to create a connection after a search.

```java
mTICPair.searchDevices(new OnDeviceDetectedListener() {
	@Override
	public void onDetect(BluetoothDevice bluetoothDevice) {
		if (bluetoothDevice.getAddress().equals("YOUR DEVICE MAC ADDRESS")) {
			final TIC tic = mTICPair.connect(bluetoothDevice, new ConnectionStateListener() {
				@Override
				public void onStateChanged(TIC connection, boolean isConnected, String message)					{
                  // on state changed (connected, disconnected etc..)
				}
           
                @Override
				public void onMessage(TIC connection, int bytes, byte[] message) 
                {
					String output = new String(message);
					Log.d("output", output);
					mainText.setText("Message : " + output);
				}
			});
		}
	}
});
```



This is exemplary detach TICPair on application finish. (like onDestroy())

```java
mTICPair.detach();
```
