package org.nagarro.disasterhelp.registration.dao;

import java.util.List;

import org.nagarro.disasterhelp.registration.models.ReceiverAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiverAddressRepository extends JpaRepository<ReceiverAddress, Integer> {

	public List<ReceiverAddress> findByCity(String city);
}
