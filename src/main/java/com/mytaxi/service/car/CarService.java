package com.mytaxi.service.car;

import java.util.List;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

public interface CarService {
	
	 	CarDO find(Long carId) throws EntityNotFoundException;

	    CarDO create(CarDO carDO) throws ConstraintsViolationException;

	    void delete(Long carId) throws EntityNotFoundException;
	    
	    void updateSelected(Long carId, Boolean selected) throws EntityNotFoundException;

	    List<CarDO> find(Boolean selected);
	    
	    void updateCar(CarDO carDo) throws ConstraintsViolationException;
	    
	    void releaseCar(CarDO carDO) throws EntityNotFoundException, ConstraintsViolationException;

	    List<CarDO> search(String licensePlate, EngineType engineType, String make);

	    List<CarDO> search(String licensePlate, EngineType engineType, String make, boolean selected);
}
