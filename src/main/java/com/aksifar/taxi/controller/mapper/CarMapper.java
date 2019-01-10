package com.aksifar.taxi.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.aksifar.taxi.datatransferobject.CarDTO;
import com.aksifar.taxi.domainobject.CarDO;

public class CarMapper
{
    public static CarDO makeCarDO(CarDTO CarDTO)
    {
        return new CarDO(CarDTO.getMake(), CarDTO.getLicensePlate(), CarDTO.getSeatCount(), CarDTO.getEngineType());
    }

    public static CarDTO makeCarDTO(CarDO CarDO)
    {
        CarDTO.CarDTOBuilder CarDTOBuilder = CarDTO.newBuilder()
            .setId(CarDO.getId())
            .setMake(CarDO.getMake())
            .setLicensePlate(CarDO.getLicensePlate())
            .setSeatCount(CarDO.getSeatCount())
            .setEngineType(CarDO.getEngineType());

        return CarDTOBuilder.createCarDTO();
    }

    public static List<CarDTO> makeCarDTOList(Collection<CarDO> Cars)
    {
        return Cars.stream()
            .map(CarMapper::makeCarDTO)
            .collect(Collectors.toList());
    }
}
