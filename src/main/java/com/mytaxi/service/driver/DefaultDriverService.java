package com.mytaxi.service.driver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.DriverHasNoCarAssignedException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.CarService;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService
{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    @Autowired
    CarService carService;
    private final DriverRepository driverRepository;


    public DefaultDriverService(final DriverRepository driverRepository)
    {
        this.driverRepository = driverRepository;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }

	@Override
	@Transactional
	public CarDO reserveCar(long driverId, long carId)
			throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException {

		// checking if driver & car exists
		DriverDO driverDO = findDriverChecked(driverId);
		// checking if car exists
		CarDO newCarDO = carService.find(carId);

		if (newCarDO.getSelected()) {
			throw new CarAlreadyInUseException("The Car with id: " + carId + " is alreay in use by some other driver.");
		}
		
		// if the driver already has a car selected, then release the car from driver
		CarDO oldCarDO = driverDO.getCar();
		if (null != oldCarDO) {
			carService.releaseCar(oldCarDO);
		}
		// update the new car's availability status & assign it to the driver
		newCarDO.setSelected(true);
		carService.updateCar(newCarDO);
		driverDO.setCar(newCarDO);
		driverRepository.save(driverDO);
		return newCarDO;
	}

	@Override
	public CarDO getReservedCar(long driverId) throws EntityNotFoundException, DriverHasNoCarAssignedException {
		DriverDO driverDO = findDriverChecked(driverId);
		CarDO carDO = driverDO.getCar();
		if(null == carDO) 
		{
			throw new DriverHasNoCarAssignedException("Driver with id: " + driverId + " does not have any car assigned.");
		}
		return carDO;
	}

	@Override
	public void releaseCar(long driverId) throws EntityNotFoundException, DriverHasNoCarAssignedException, ConstraintsViolationException {
		DriverDO driverDO = findDriverChecked(driverId);
		CarDO carDO = driverDO.getCar();
		if(null == carDO) 
		{
			throw new DriverHasNoCarAssignedException("Driver with id: " + driverId + " does not have any car assigned.");
		}
		carService.releaseCar(carDO);
		driverDO.setCar(null);
		driverRepository.save(driverDO);
	}
}
