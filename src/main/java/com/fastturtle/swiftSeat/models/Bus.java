package com.fastturtle.swiftSeat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fastturtle.swiftSeat.enums.BusType;
import com.fastturtle.swiftSeat.helpers.BusTypeConverter;
import jakarta.persistence.*;

import java.time.LocalTime;
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
    @Convert(converter = BusTypeConverter.class)
    @Column(name = "busType", nullable = false)
    private BusType busType;

    @OneToMany(mappedBy = "bus")
    @JsonIgnore
    private Set<BusRoute> busRoutes;

    @Column(name = "busTiming")
    private LocalTime busTiming;

    @Transient
    private String formattedBusTiming;

    @Transient
    private String formattedBusNumber;

    @Transient
    private String styleClass;

    public Bus(String busNo, String companyName, int totalSeats,
               int availableSeats, BusType busType, LocalTime busTiming) {
        this.busNo = busNo;
        this.companyName = companyName;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.busType = busType;
        this.busTiming = busTiming;
    }

    public Bus() {

    }

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

    public LocalTime getBusTiming() {
        return busTiming;
    }

    public void setBusTiming(LocalTime busTiming) {
        this.busTiming = busTiming;
    }

    public String getFormattedBusTiming() {
        return formattedBusTiming;
    }

    public void setFormattedBusTiming(String formattedBusTiming) {
        this.formattedBusTiming = formattedBusTiming;
    }

    public String getFormattedBusNumber() {
        return formattedBusNumber;
    }

    public void setFormattedBusNumber(String formattedBusNumber) {
        this.formattedBusNumber = formattedBusNumber;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
}
