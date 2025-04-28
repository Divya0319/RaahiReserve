package com.fastturtle.raahiReserve.dtos;

public class CityPassengerCountDTO {

    private String cityName;
    private Long passengerCount;

    public CityPassengerCountDTO(String cityName, Long passengerCount) {
        this.cityName = cityName;
        this.passengerCount = passengerCount;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(Long passengerCount) {
        this.passengerCount = passengerCount;
    }
}
