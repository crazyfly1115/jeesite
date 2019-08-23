package com.thinkgem.jeesite.modules.ips.position;
public class Gps {
    public Gps(double mgLon, double mgLat) {
        this.mgLon = mgLon;
        this.mgLat = mgLat;
    }

    private double mgLon;
    private double mgLat;

    public double getMgLon() {
        return mgLon;
    }

    public void setMgLon(double mgLon) {
        this.mgLon = mgLon;
    }

    public double getMgLat() {
        return mgLat;
    }

    public void setMgLat(long mgLat) {
        this.mgLat = mgLat;
    }
}
