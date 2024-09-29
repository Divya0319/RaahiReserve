package com.fastturtle.raahiReserve.dtos;

import com.fastturtle.raahiReserve.enums.BusType;
import com.fastturtle.raahiReserve.enums.SeatType;

public class SeatCostDetailsDTO {
    private Integer seatNumber;
    private String seatType;
    private String busType;
    private Float seatCost;

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public Float getSeatCost() {
        return seatCost;
    }

    public void setSeatCost(Float seatCost) {
        this.seatCost = seatCost;
    }

}
