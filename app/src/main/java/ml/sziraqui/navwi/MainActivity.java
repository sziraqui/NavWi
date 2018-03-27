package ml.sziraqui.navwi;

import android.content.Context;
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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ml.sziraqui.navwi.adapters.WiDeviceAdapter;

public class MainActivity extends AppCompatActivity {

    CoordinatorLayout root;
    ArrayList<ScanResult> devices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        root = findViewById(R.id.root_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        devices = new ArrayList<>();

        WiDeviceAdapter adapter = new WiDeviceAdapter(this, devices);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void scanWifi() {

        WifiManager wifiManager = startWifiIfDisabled();

    }

    private WifiManager startWifiIfDisabled(){
        WifiManager wifiManager = (WifiManager) getApplication()
                .getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled()) {
            Snackbar
                    .make(root,"Turning on Wifi", Snackbar.LENGTH_INDEFINITE)
                    .show();
            wifiManager.setWifiEnabled(true);
            
        }
        return wifiManager;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
