package com.jdawidowska.myapplication3.models;

import java.time.ZonedDateTime;

public class ZonedDateTimeRequest {
    private ZonedDateTime dateFrom;
    private ZonedDateTime dateTo;

    public ZonedDateTimeRequest() {
    }

    public ZonedDateTimeRequest(ZonedDateTime dateFrom, ZonedDateTime dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public ZonedDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(ZonedDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public ZonedDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(ZonedDateTime dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "ZonedDateTimeRequest{" +
                "dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
