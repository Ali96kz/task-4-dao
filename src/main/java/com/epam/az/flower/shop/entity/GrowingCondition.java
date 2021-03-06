package com.epam.az.flower.shop.entity;

public class GrowingCondition extends BaseEntity {
    public String name;
    private boolean lovelight;
    private Temperature temperature;
    private WaterInWeek waterInWeek;


    public GrowingCondition() {
    }

    public boolean isLovelight() {
        return lovelight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WaterInWeek getWaterInWeek() {
        return waterInWeek;
    }

    public void setWaterInWeek(WaterInWeek waterInWeek) {
        this.waterInWeek = waterInWeek;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public boolean getLovelight() {
        return lovelight;
    }

    public void setLovelight(boolean lovelight) {
        this.lovelight = lovelight;
    }

    @Override
    public String toString() {
        return temperature + " " + waterInWeek + " " + lovelight;
    }
}
