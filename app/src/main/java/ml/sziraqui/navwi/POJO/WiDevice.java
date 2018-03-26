package ml.sziraqui.navwi.POJO;

/**
 * Created by sziraqui on 23/3/18.
 */

public class WiDevice {
    private String name;
    private Double signalStrength;
    private String hwAddress;


    public WiDevice(String name, Double signalStrength, String hwAddress){
        this.name = name;
        this.signalStrength = signalStrength;
        this.hwAddress = hwAddress;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(Double signalStrength) {
        this.signalStrength = signalStrength;
    }

    public String getHwAddress() {
        return hwAddress;
    }

    public void setHwAddress(String hwAddress) {
        this.hwAddress = hwAddress;
    }
}
