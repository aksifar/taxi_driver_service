package com.aksifar.taxi.service.car;

import java.util.List;

import com.aksifar.taxi.domainobject.CarDO;
import com.aksifar.taxi.domainvalue.EngineType;
import com.aksifar.taxi.exception.ConstraintsViolationException;
import com.aksifar.taxi.exception.EntityNotFoundException;

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
