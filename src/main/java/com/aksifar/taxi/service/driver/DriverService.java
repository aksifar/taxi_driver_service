package com.aksifar.taxi.service.driver;

import java.util.List;

import com.aksifar.taxi.domainobject.CarDO;
import com.aksifar.taxi.domainobject.DriverDO;
import com.aksifar.taxi.domainvalue.OnlineStatus;
import com.aksifar.taxi.exception.CarAlreadyInUseException;
import com.aksifar.taxi.exception.ConstraintsViolationException;
import com.aksifar.taxi.exception.DriverHasNoCarAssignedException;
import com.aksifar.taxi.exception.EntityNotFoundException;

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

	List<DriverDO> search(String userName, boolean onlineStatus, boolean deleted);

}
