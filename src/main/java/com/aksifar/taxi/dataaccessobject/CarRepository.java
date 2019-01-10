package com.aksifar.taxi.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aksifar.taxi.domainobject.CarDO;
import com.aksifar.taxi.domainvalue.EngineType;

/**
 * Database Access Object for car table.
 */
@Repository
public interface CarRepository extends CrudRepository<CarDO, Long>
{

    List<CarDO> findByEngineType(EngineType engineType);
    
    /**
     * To get the list of cars which based on availability.
     */
    List<CarDO> findBySelected(Boolean selected);
    
    @Query(value = "SELECT * FROM car WHERE license_plate LIKE CONCAT('%',:licensePlate,'%') "
    		+ "AND engine_type = :engineType "
    		+ "AND make LIKE CONCAT('%',:make,'%')",
    		nativeQuery = true)
	public List<CarDO> search(
			@Param("licensePlate") String licensePlate, 
			@Param("engineType") String engineType, 
			@Param("make") String make);

    @Query(value = "SELECT * FROM car WHERE license_plate LIKE CONCAT('%',:licensePlate,'%') "
    		+ "AND engine_type = :engineType "
    		+ "AND make LIKE CONCAT('%',:make,'%') "
    		+ "AND selected = :selected ",
    		nativeQuery = true)
	public List<CarDO> search(
			@Param("licensePlate") String licensePlate,
			@Param("engineType") String engineType,
			@Param("make") String make,
			@Param("selected") boolean selected);

    @Query(value = "SELECT * FROM car WHERE license_plate LIKE CONCAT('%',:licensePlate,'%') "
    		+ "AND make LIKE CONCAT('%',:make,'%')", nativeQuery = true)
	public List<CarDO> search(
			@Param("licensePlate") String licensePlate,
			@Param("make") String make);
    
    @Query(value = "SELECT * FROM car WHERE license_plate LIKE CONCAT('%',:licensePlate,'%') "
    		+ "AND make LIKE CONCAT('%',:make,'%') "
    		+ "AND selected = :selected",
    		nativeQuery = true)
	public List<CarDO> search(
			@Param("licensePlate") String licensePlate,
			@Param("make") String make,
			@Param("selected") boolean selected);
}
