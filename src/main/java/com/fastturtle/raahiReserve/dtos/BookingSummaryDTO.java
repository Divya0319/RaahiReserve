package com.fastturtle.raahiReserve.dtos;

import java.util.ArrayList;
import java.util.List;

public class BookingSummaryDTO {

    private List<SeatCostDetailsDTO> seatCostDetails;
    private Float totalCost;

    public BookingSummaryDTO() {
        this.seatCostDetails = new ArrayList<>();
        this.totalCost = 0.0f;
    }

    public List<SeatCostDetailsDTO> getSeatCostDetails() {
        return seatCostDetails;
    }

    public void setSeatCostDetails(List<SeatCostDetailsDTO> seatCostDetails) {
        this.seatCostDetails = seatCostDetails;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public void addSeatCostDetail(SeatCostDetailsDTO seatDetail) {
        this.seatCostDetails.add(seatDetail);
        this.totalCost = this.totalCost + seatDetail.getSeatCost();


    }
}
