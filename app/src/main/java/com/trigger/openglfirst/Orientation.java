package com.trigger.openglfirst;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orientation {

    @SerializedName("PITCH")
    @Expose
    private Double pITCH;
    @SerializedName("ROLL")
    @Expose
    private Double rOLL;
    @SerializedName("YAW")
    @Expose
    private Double yAW;

    public Double getPITCH() {
        return pITCH;
    }

    public void setPITCH(Double pITCH) {
        this.pITCH = pITCH;
    }

    public Double getROLL() {
        return rOLL;
    }

    public void setROLL(Double rOLL) {
        this.rOLL = rOLL;
    }

    public Double getYAW() {
        return yAW;
    }

    public void setYAW(Double yAW) {
        this.yAW = yAW;
    }

}