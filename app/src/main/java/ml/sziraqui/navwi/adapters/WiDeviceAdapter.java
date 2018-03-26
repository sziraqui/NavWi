package ml.sziraqui.navwi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ml.sziraqui.navwi.POJO.WiDevice;
import ml.sziraqui.navwi.R;

/**
 * Created by sziraqui on 23/3/18.
 */

public class WiDeviceAdapter extends RecyclerView.Adapter<WiDeviceAdapter.WiDeviceHolder> {

    Context context;
    List<WiDevice> devices;
    public WiDeviceAdapter(Context context, List<WiDevice> devices){
        this.context = context;
        this.devices = devices;
    }
    public class WiDeviceHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView value;
        public WiDeviceHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.widevice_iv);
            name = itemView.findViewById(R.id.widevice_name_tv);
            value = itemView.findViewById(R.id.widevice_signal_value_tv);
        }

        public void bindHolder(WiDevice device) {
            name.setText(device.getName());
            value.setText(""+device.getSignalStrength());
        }
    }

    @Override
    public WiDeviceAdapter.WiDeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wi_device_item, parent, false);
        return new WiDeviceHolder(view);
    }

    @Override
    public void onBindViewHolder(WiDeviceAdapter.WiDeviceHolder holder, int position) {
        holder.bindHolder(devices.get(position));
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }
}
