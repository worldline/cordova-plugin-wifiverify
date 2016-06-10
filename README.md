Cordova Plugin Wi-Fi Verify
==========================

_A Cordova plugin to check if a specific Wi-Fi is on 

**Service :** SDCO - Software Engineering - Web & Mobile Framework

## Installation

"**In the Tech Forum project the dependency exists in package.json, so you have nothing to do.**"

If you want to add the plugin in another project follow this instruction:

Clone the project from Github in the same folder with you project
In you project run:

```bash
cordova plugin add ../cordova-plugin-wifiverify
```

In the constructor of **app/app.js** and after **platform.ready().then(() => {** add this code:

```bash
if(ionic.Platform.isAndroid())
window.cordova.plugins.Wifi.wifiverify();
```

To test this plugin you need to run it on real device.