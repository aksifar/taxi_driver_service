package com.mytaxi.service.driver;

import java.util.List;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.DriverHasNoCarAssignedException;
import com.mytaxi.exception.EntityNotFoundException;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);

	CarDO reserveCar(long driverId, long carId) throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException;

	void releaseCar(long driverId) throws EntityNotFoundException, DriverHasNoCarAssignedException, ConstraintsViolationException;

	CarDO getReservedCar(long driverId) throws EntityNotFoundException, DriverHasNoCarAssignedException;
	

}
