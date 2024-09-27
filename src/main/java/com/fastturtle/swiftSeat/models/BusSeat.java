package com.fastturtle.swiftSeat.models;

import com.fastturtle.swiftSeat.enums.SeatType;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "busSeat"
//        , uniqueConstraints =
//@UniqueConstraint(columnNames = {"bus_id", "seat_number", "createdAt"})
)
public class BusSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "busSeatId")
    private int busSeatId;

    @ManyToOne
    @JoinColumn(name = "busId", nullable = false)
    private Bus bus;

    @Column(name = "seatNumber", nullable = false)
    private int seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "seatType")   // allow null for "No Preference"
    private SeatType seatType;

    @Column(name = "occupied")
    private boolean occupied = false;

    @Column(name = "createdAt")
    private ZonedDateTime createdAt;

    public int getBusSeatId() {
        return busSeatId;
    }

    public void setBusSeatId(int busSeatId) {
        this.busSeatId = busSeatId;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
