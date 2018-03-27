package ml.sziraqui.navwi.sensors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

import ml.sziraqui.navwi.adapters.WiDeviceAdapter;

/**
 * Created by sziraqui on 27/3/18.
 */

public class WifiBR extends BroadcastReceiver {

    WifiManager wifiManager;
    List<ScanResult> devices;
    WiDeviceAdapter adapter;
    public WifiBR(WifiManager wifiManager, List<ScanResult> devices, WiDeviceAdapter wiDeviceAdapter ){
        this.wifiManager = wifiManager;
        this.devices = devices;
        this.adapter = wiDeviceAdapter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        devices = wifiManager.getScanResults();
        adapter.notifyDataSetChanged();
    }
}
