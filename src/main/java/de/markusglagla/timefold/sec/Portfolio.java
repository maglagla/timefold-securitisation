package de.markusglagla.timefold.sec;

import java.math.BigDecimal;

public class Portfolio {
    private String name;
    private BigDecimal maxVolume;

    public Portfolio(String name, BigDecimal maxVolume) {
        this.name = name;
        this.maxVolume = maxVolume;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getMaxVolume() {
        return maxVolume;
    }

    @Override
    public String toString() {
        return "Portfolio{name='" + name + "', maxVolume=" + maxVolume + '}';
    }
}
