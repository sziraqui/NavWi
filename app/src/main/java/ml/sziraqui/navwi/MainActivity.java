package ml.sziraqui.navwi;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;

import ml.sziraqui.navwi.adapters.WiDeviceAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_LOCATION = 0x12345;

    CoordinatorLayout root;
    ArrayList<ScanResult> devices;
    WiDeviceAdapter adapter;
    WifiManager wifiManager;
    WifiReceiver wifiReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        root = findViewById(R.id.root_view);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = saveScanResult(devices);
                String msg;
                if (filename.equals("")){
                    msg = "Error saving file!";
                } else {
                    msg = "Scan result saved!";
                }
                Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                        .setAction("OK", null).show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        devices = new ArrayList<>();

        adapter = new WiDeviceAdapter(this, devices);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        scanWifi();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(wifiReceiver);
    }


    public void scanWifi() {

        startWifiIfDisabled();
        wifiReceiver = new WifiReceiver();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        ((WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE))
                .startScan();
    }

    private void startWifiIfDisabled(){
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled()) {
            Snackbar
                    .make(root,"Turning on Wifi", Snackbar.LENGTH_SHORT)
                    .show();
            wifiManager.setWifiEnabled(true);
            
        }

    }

    public String saveScanResult(ArrayList<ScanResult> scanResults){
        StringBuilder data = new StringBuilder("Sr No,Time,BSSID,SSID,Strength\n");
        for(int i = 0; i < scanResults.size(); i++){
            data.append(i+","+(new Date())+","+scanResults.get(i).BSSID+","+scanResults.get(i).SSID+","+scanResults.get(i).level+"\n");
        }

        File path = getExternalFilesDir(null);
        String filename = "ScanResults_"+(new Date())+".csv";
        File file = new File(path, filename);

        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(new String(data).getBytes());
        } catch (IOException ioe) {
            Log.e("saveScanResult","IO error");
        }
        return filename;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            devices.clear();
            adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE_ACCESS_LOCATION) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            startWifiIfDisabled();
        }
    }
    class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("log","WifiBR.onReceive()");
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_CODE_ACCESS_LOCATION);
            } else {
                ArrayList<ScanResult> scanResults = (ArrayList<ScanResult>) ((WifiManager) context
                        .getApplicationContext()
                        .getSystemService(WIFI_SERVICE))
                        .getScanResults();

                devices.addAll(scanResults);
                if(devices.size() > 0) {
                    adapter.notifyDataSetChanged();
                    Log.d("log", "ScanResult " + devices.get(0).SSID);
                }
            }
        }
    }
}
