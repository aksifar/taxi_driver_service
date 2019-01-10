package com.aksifar.taxi.service.car;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.aksifar.taxi.dataaccessobject.CarRepository;
import com.aksifar.taxi.domainobject.CarDO;
import com.aksifar.taxi.domainvalue.EngineType;
import com.aksifar.taxi.exception.ConstraintsViolationException;
import com.aksifar.taxi.exception.EntityNotFoundException;

@Service
public class DefaultCarService implements CarService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

	@Autowired
    CarRepository carRepository;

	@Override
	public CarDO find(Long carId) throws EntityNotFoundException {
		return findCarChecked(carId);
	}

	@Override
	public CarDO create(CarDO carDO) throws ConstraintsViolationException {

		CarDO car;
        try
        {
            car = carRepository.save(carDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a car: {}", carDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return car;
	}

	@Override
	@Transactional
	public void delete(Long carId) throws EntityNotFoundException {
		CarDO carDO = findCarChecked(carId);
		carRepository.delete(carDO);
	}

	@Override
	public List<CarDO> find(Boolean selected) {
		return carRepository.findBySelected(selected);
	}
	
	@Override
	public void updateSelected(Long carId, Boolean selected) throws EntityNotFoundException {
		CarDO carDO = findCarChecked(carId);
		carDO.setSelected(selected);
		carRepository.save(carDO);
	}
	
	
	private CarDO findCarChecked(Long carId) throws EntityNotFoundException {
		return carRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("Could not find car entity with id: " + carId));
	}

	@Override
	public void updateCar(CarDO carDO) throws ConstraintsViolationException {
		try
        {
            carRepository.save(carDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while updating a car: {}", carDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
	}

	@Override
	public void releaseCar(CarDO carDO) throws EntityNotFoundException, ConstraintsViolationException {
		carDO.setSelected(false);
		updateCar(carDO);
		
	}

	@Override
	public List<CarDO> search(String licensePlate, EngineType engineType, String make) {
		if(null ==  licensePlate)
		{
			licensePlate ="";
		}
		if(null ==  make)
		{
			make ="";
		}
		if(null ==  engineType)
		{
			return  carRepository.search(licensePlate, make);
		}
		else
		{
			return  carRepository.search(licensePlate, engineType.toString(), make);
		}
	}

	@Override
	public List<CarDO> search(String licensePlate, EngineType engineType, String make, boolean selected) {
		if(null ==  licensePlate)
		{
			licensePlate ="";
		}
		if(null ==  make)
		{
			make ="";
		}
		if(null !=  engineType)
		{
			return carRepository.search(licensePlate, engineType.toString(), make, selected);
		}
		else
		{
			return carRepository.search(licensePlate, make, selected);
		}
	}
}
