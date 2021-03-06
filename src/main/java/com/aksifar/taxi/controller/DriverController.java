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
import com.aksifar.taxi.controller.mapper.DriverMapper;
import com.aksifar.taxi.datatransferobject.CarDTO;
import com.aksifar.taxi.datatransferobject.DriverDTO;
import com.aksifar.taxi.domainobject.DriverDO;
import com.aksifar.taxi.domainvalue.OnlineStatus;
import com.aksifar.taxi.exception.CarAlreadyInUseException;
import com.aksifar.taxi.exception.ConstraintsViolationException;
import com.aksifar.taxi.exception.DriverHasNoCarAssignedException;
import com.aksifar.taxi.exception.EntityNotFoundException;
import com.aksifar.taxi.service.driver.DriverService;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    private final DriverService driverService;


    @Autowired
    public DriverController(final DriverService driverService)
    {
        this.driverService = driverService;
    }

    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(
        @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }

    @GetMapping(params="onlineStatus")
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
    {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }
    
    @GetMapping("/{driverId}/car")
    public CarDTO getReservedCar(@PathVariable long driverId) throws EntityNotFoundException, DriverHasNoCarAssignedException{
    	return CarMapper.makeCarDTO(driverService.getReservedCar(driverId));
    }

    @PutMapping("/{driverId}/car/{carId}")
	public CarDTO reserveCar(@PathVariable long driverId, @PathVariable long carId)
			throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException {
		return CarMapper.makeCarDTO(driverService.reserveCar(driverId, carId));
	}
	
    @DeleteMapping("/{driverId}/car")
	public void releaseCar(@PathVariable long driverId)
			throws EntityNotFoundException, DriverHasNoCarAssignedException, ConstraintsViolationException {
		driverService.releaseCar(driverId);
	}
    
    @GetMapping
    public List<DriverDTO> searchDrivers(
    		@RequestParam(value = "username", required = false) String userName,
    		@RequestParam(value = "online", required = true) boolean online,
    		@RequestParam(value = "deleted",required = true) boolean deleted){
    	
    	return DriverMapper.makeDriverDTOList(driverService.search(userName, online, deleted));
    }
}
