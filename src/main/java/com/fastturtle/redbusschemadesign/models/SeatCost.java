package com.fastturtle.redbusschemadesign.models;

import com.fastturtle.redbusschemadesign.enums.BusType;
import com.fastturtle.redbusschemadesign.enums.SeatType;
import jakarta.persistence.*;

@Entity
@Table(name = "seatCost")
public class SeatCost {

    @Id
    @Column(name = "seatCostId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatCostId;

    @Enumerated(EnumType.STRING)
    @Column(name = "busType")
    private BusType busType;

    @Enumerated(EnumType.STRING)
    @Column(name = "seatType")
    private SeatType seatType;

    @Column(name = "cost")
    private float cost;

    public int getSeatCostId() {
        return seatCostId;
    }

    public void setSeatCostId(int seatCostId) {
        this.seatCostId = seatCostId;
    }

    public BusType getBusType() {
        return busType;
    }

    public void setBusType(BusType busType) {
        this.busType = busType;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
