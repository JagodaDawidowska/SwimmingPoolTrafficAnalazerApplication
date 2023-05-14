package com.jdawidowska.myapplication3.models;

import java.time.ZonedDateTime;

public class DataModel {
    private Integer amountPeopleAtPool;
    private Integer amountOfFreeSpotsAtPool;
    private Integer amountPeopleAtSpa;
    private Integer amountOfFreeSpotsAtSpa;
    private String downloadDate;

    public DataModel() {
    }

    public DataModel(Integer amountPeopleAtPool, Integer amountOfFreeSpotsAtPool, Integer amountPeopleAtSpa, Integer amountOfFreeSpotsAtSpa, String downloadDate) {
        this.amountPeopleAtPool = amountPeopleAtPool;
        this.amountOfFreeSpotsAtPool = amountOfFreeSpotsAtPool;
        this.amountPeopleAtSpa = amountPeopleAtSpa;
        this.amountOfFreeSpotsAtSpa = amountOfFreeSpotsAtSpa;
        this.downloadDate = downloadDate;
    }

    public DataModel(Integer amountPeopleAtPool, Integer amountOfFreeSpotsAtPool, Integer amountPeopleAtSpa, Integer amountOfFreeSpotsAtSpa) {
        this.amountPeopleAtPool = amountPeopleAtPool;
        this.amountOfFreeSpotsAtPool = amountOfFreeSpotsAtPool;
        this.amountPeopleAtSpa = amountPeopleAtSpa;
        this.amountOfFreeSpotsAtSpa = amountOfFreeSpotsAtSpa;
    }

    public Integer getAmountPeopleAtPool() {
        return amountPeopleAtPool;
    }

    public void setAmountPeopleAtPool(Integer amountPeopleAtPool) {
        this.amountPeopleAtPool = amountPeopleAtPool;
    }

    public Integer getAmountOfFreeSpotsAtPool() {
        return amountOfFreeSpotsAtPool;
    }

    public void setAmountOfFreeSpotsAtPool(Integer amountOfFreeSpotsAtPool) {
        this.amountOfFreeSpotsAtPool = amountOfFreeSpotsAtPool;
    }

    public Integer getAmountPeopleAtSpa() {
        return amountPeopleAtSpa;
    }

    public void setAmountPeopleAtSpa(Integer amountPeopleAtSpa) {
        this.amountPeopleAtSpa = amountPeopleAtSpa;
    }

    public Integer getAmountOfFreeSpotsAtSpa() {
        return amountOfFreeSpotsAtSpa;
    }

    public void setAmountOfFreeSpotsAtSpa(Integer amountOfFreeSpotsAtSpa) {
        this.amountOfFreeSpotsAtSpa = amountOfFreeSpotsAtSpa;
    }

    public String getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(String downloadDate) {
        this.downloadDate = downloadDate;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "amountPeopleAtPool=" + amountPeopleAtPool +
                ", amountOfFreeSpotsAtPool=" + amountOfFreeSpotsAtPool +
                ", amountPeopleAtSpa=" + amountPeopleAtSpa +
                ", amountOfFreeSpotsAtSpa=" + amountOfFreeSpotsAtSpa +
                ", downloadDate=" + downloadDate +
                '}';
    }
}
