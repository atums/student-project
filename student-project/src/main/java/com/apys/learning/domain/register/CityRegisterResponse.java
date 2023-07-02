package com.apys.learning.domain.register;

public class CityRegisterResponse {
    boolean registered;
    boolean temporal;

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public boolean isTemporal() {
        return temporal;
    }

    public void setTemporal(boolean temporal) {
        this.temporal = temporal;
    }

    @Override
    public String toString() {
        return "CityRegisterCheckerResponse{" +
                "existing=" + registered +
                ", temporal=" + temporal +
                '}';
    }
}
