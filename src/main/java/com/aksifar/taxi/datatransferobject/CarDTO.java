package com.aksifar.taxi.datatransferobject;

import javax.validation.constraints.NotNull;

import com.aksifar.taxi.domainvalue.EngineType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "Lisence plate can not be null!")
    private String licensePlate;
    
    @NotNull(message = "Manufacturer can not be null!")
    private String make;

    @NotNull(message = "Seat count can not be null!")
    private int seatCount;
    
    @NotNull(message = "Engine type can not be null!")
    private EngineType engineType;

    private CarDTO()
    {
    }

    private CarDTO(Long id, String licensePlate, int seatCount, String make, EngineType engineType)
    {
        this.id = id;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.make = make;
        this.engineType = engineType;
    }

    public static CarDTOBuilder newBuilder()
    {
        return new CarDTOBuilder();
    }

    @JsonProperty
    public Long getId()
    {
        return id;
    }

    public String getLicensePlate()
    {
        return licensePlate;
    }

    public String getMake()
    {
        return make;
    }

    public int getSeatCount()
    {
        return seatCount;
    }
    
    public EngineType getEngineType()
    {
    	return engineType;
    }

    public static class CarDTOBuilder
    {
        private Long id;
        private String licensePlate;
        private String make;
        private int seatCount;
        private EngineType engineType;

        public CarDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }

        public CarDTOBuilder setLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }

        public CarDTOBuilder setMake(String make)
        {
            this.make = make;
            return this;
        }

        public CarDTOBuilder setSeatCount(int seatCount)
        {
            this.seatCount = seatCount;
            return this;
        }
        
        public CarDTOBuilder setEngineType(EngineType engineType)
        {
            this.engineType = engineType;
            return this;
        }

        public CarDTO createCarDTO()
        {
            return new CarDTO(id, licensePlate, seatCount, make , engineType);
        }
    }
}
