# Library TIC for Android

Library TIC, the simple modifier of IoT API. our library helping developer of generating device API, and developer of using API for 3-rd party application.

this time, you can use TIC-ardunio repository for generating your API

[TIC-arudino]: https://github.com/ArbiterLab/TIC-android

and using there API on android with TIC-android.

# How to use?

## Sorry for complex

We are not upload our library on any repository. this time, you should add our library module
https://github.com/ArbiterLab/TIC-android/tree/master/library
We will improve this soon.

Assume API you want to use is created successfully, you can using there API following this step.

Before we start, these permissions should be writed on Android Manifest

```xml
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
```

on Mashmello (6.0+) , "android.permission.ACCESS_COARSE_LOCATION" permission  should be granted by separate request.

[Permission Request (Reference)]: https://developer.android.com/training/permissions/requesting.html

Maybe, We are throw runtime exception if "ACCESS_COARSE_LOACTION" perision is not granted.



First, you shuld verify if device supports bluetooth connect and it can use with our methods.

```java
if (!TICUtils.isNeedPermissionsGranted(this)){
 	Toast.makeText(this, "ACCESS_COARSE_LOCATION PERMISSION NEED GRANT",Toast.LENGTH_SHORT).show();
}
if (!TICUtils.isDeviceSupportBluetooth()) return;
if (!TICUtils.isBluetoothEnabled()) {
	Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	startActivityForResult(enableBtIntent, 100);
	return;
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
     public void onStateChanged(TIC connection, boolean isConnected, String message) {
      // on state changed (connected, disconnected etc..)
     }      
    
    @Override
     public void onMessage(TIC connection, int bytes, byte[] message) {
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

# TIC Model

We are provide sort of models for IoT device has a similar function. If using same model for similar devices. It is more efficient than each devices API.

Now, because it's in beta. We brought some models as an example. this time, We provide MusicSpeakerModel, ChatModel, DeviceControllModel.

You can make API call like this.

```java
MusicSpeakerModel musicSpeakerModel = new MusicSpeakerModel(tic);
musicSpeakerModel.play(new TicParam("musicPath", "/~/path/xxx.mp3"), new TicParam("timerTime", "30s") );
// or
musicSpeakerModel.stop();
```

```java
DeviceControllModel deviceControllModel = new DeviceControllModel(tic);
deviceControllModel.getDeviceStatus(new TicParam("req", "modelNum, supportMethods"));
deviceControllModel.setDeviceOff();
```

and there request convert to JSON, and send to IoT devices.

```json
{"path":"\/play","params":[{"location":"\/~~\/~"},{"location":"\/~~\/~"},{"location":"\/~~\/~"}]}
```

If you want make your own API call, following this.

```java
public class CustomModel {

    private TIC tic;

    public CustomModel(TIC tic){
        this.tic = tic;
    }

    @TicAPI("/api~/custom1")
    public void custom1(TicParam... params){
        tic.work(params);
    }

    @TicAPI("/api~/custom2")
    public void custom2(TicParam... params){
        tic.work(params);
    }
}
```

In any case, declare the tic.work () on method body that you want request. and should delcare your API path in annotation.
