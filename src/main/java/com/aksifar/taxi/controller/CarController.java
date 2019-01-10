package com.aksifar.taxi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aksifar.taxi.controller.mapper.CarMapper;
import com.aksifar.taxi.datatransferobject.CarDTO;
import com.aksifar.taxi.domainobject.CarDO;
import com.aksifar.taxi.domainvalue.EngineType;
import com.aksifar.taxi.exception.ConstraintsViolationException;
import com.aksifar.taxi.exception.EntityNotFoundException;
import com.aksifar.taxi.service.car.CarService;

/**
 * All operations for Cars will be routed by this controller.
 */
@RestController
@RequestMapping("v1/cars")
public class CarController {
	
	@Autowired
	CarService carService;
	
	@GetMapping("/{carId}")
	public CarDTO getCar(@PathVariable long carId) throws EntityNotFoundException
	{
		return CarMapper.makeCarDTO(carService.find(carId));
	}
	
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException
    {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.create(carDO));
    }
	
	@DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable long carId) throws EntityNotFoundException
    {
		 carService.delete(carId);
    }
	
	@PutMapping("/{carId}")
    public void updateCarAvailability(
        @PathVariable long carId, @RequestParam boolean selected)
        throws EntityNotFoundException
    {
		carService.updateSelected(carId, selected);
    }
	
	@GetMapping
	public List<CarDTO> searchCars(
			@RequestParam(value = "licensePlate", required = false) String licensePlate,
			@RequestParam(value = "engineType", required = false) EngineType engineType, 
			@RequestParam(value = "make", required = false) String make,
			@RequestParam(value = "selected", required = false) String selectedString)
	{
		if(isValid(selectedString))
		{
			boolean selected =  Boolean.parseBoolean(selectedString);
			return CarMapper.makeCarDTOList(carService.search(licensePlate, engineType, make, selected));
		}
		else
		{
			return CarMapper.makeCarDTOList(carService.search(licensePlate, engineType, make));
		}
	}
	 
	private boolean isValid(String param)
	{
		if(null == param || param.isEmpty())
			return false;
		
		return true;
	}
}
