package com.aksifar.taxi.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.aksifar.taxi.domainobject.DriverDO;
import com.aksifar.taxi.domainvalue.OnlineStatus;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long>
{
    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);

    @Query(value = "SELECT * FROM driver WHERE username LIKE CONCAT('%',:username,'%') AND online_status = :online AND deleted = :deleted",
    		nativeQuery = true)
	public List<DriverDO> searchDrivers(
			@Param("username") String userName, 
			@Param("online") String onlineStatus, 
			@Param("deleted") boolean deleted);
}
