package com.fastturtle.redbusschemadesign.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "bus")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "busId")
    private int busId;

    @Column(name = "busNo", nullable = false, unique = true)
    private String busNo;

    @Column(name = "companyName")
    private String companyName;

    @Column(name = "totalSeats")
    private int totalSeats;

    @Column(name = "availableSeats")
    private int availableSeats;

    @Enumerated(EnumType.STRING)
    @Column(name = "busType", nullable = false)
    private BusType busType;

    @OneToMany(mappedBy = "bus")
    private Set<BusRoute> busRoutes;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public BusType getBusType() {
        return busType;
    }

    public void setBusType(BusType busType) {
        this.busType = busType;
    }

    public Set<BusRoute> getBusRoutes() {
        return busRoutes;
    }

    public void setBusRoutes(Set<BusRoute> busRoutes) {
        this.busRoutes = busRoutes;
    }
}
