package com.mytaxi.controller;

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

import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.CarService;

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
    public void updateSelected(
        @PathVariable long carId, @RequestParam boolean selected)
        throws EntityNotFoundException
    {
		carService.updateSelected(carId, selected);
    }
	
	@GetMapping
	public List<CarDTO> findCars(@RequestParam boolean selected){
		return CarMapper.makeCarDTOList(carService.find(selected));
	}
	 
//  updateCar()
//update rating
}
