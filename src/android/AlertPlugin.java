package com.example.cordova.alert;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
 
import android.net.wifi.WifiManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import java.util.List;
import android.net.wifi.ScanResult; 

public class AlertPlugin extends CordovaPlugin {
  WifiManager wifiManager;
  WifiInfo info;
  Context context;
  List<ScanResult> results;
  String ssid;
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if("alert".equals(action)){
      Context context=this.cordova.getActivity().getApplicationContext(); 
      final String content = args.getString(0);
      wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
      info = wifiManager.getConnectionInfo();
      results = wifiManager.getScanResults();
      if (results != null) {
            StringBuffer buf = new StringBuffer();

            for (int i = 0; i < results.size(); i++) {
                 ssid = results.get(i).SSID;
                if (ssid.equals("TechForum")) {
                    buf.append(ssid + "\n");
                    showAlert("Welecome to the TechForum");
                }
            }
        }
      callbackContext.success();
      return true;
    } else {
      callbackContext.error("AlertPlugin."+action+" not found !");
      return false;
    }
  }
 
  private void showAlert(String content){
    // see http://developer.android.com/guide/topics/ui/dialogs.html
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.cordova.getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
    alertDialog.setTitle("Alert");
    alertDialog.setMessage(content);
    alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
      public void onClick(DialogInterface dialog, int id){
        // User clicked OK button
      }
    });
    alertDialog.show();
  }
}