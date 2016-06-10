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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;

import java.util.Calendar;
public class AlertPlugin extends CordovaPlugin {
  AlarmManagerBroadcastReceiver alarm;
  WifiManager wifiManager;
  WifiInfo info;
  Context context;
  List<ScanResult> results;
  String ssid;
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if("alert".equals(action)){
      Context context=this.cordova.getActivity().getApplicationContext(); 
	  alarm = new AlarmManagerBroadcastReceiver();
      alarm.SetAlarm(context);
      
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

 class AlarmManagerBroadcastReceiver extends BroadcastReceiver{

    final public static String ONE_TIME = "onetime";
    Calendar cal;

    public AlarmManagerBroadcastReceiver(){
        cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();


        cal.set(2016, 04, 03, 13, 51);
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        if(System.currentTimeMillis()>cal.getTimeInMillis()){
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);
            System.out.print("hello");
            CancelAlarm(context);
        }
    }
    public void SetAlarm(Context context)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 30 seconds.
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), cal.getTimeInMillis() - System.currentTimeMillis(), pi);

    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }


}
}