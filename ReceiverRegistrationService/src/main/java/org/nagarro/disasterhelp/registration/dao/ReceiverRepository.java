package org.nagarro.disasterhelp.registration.dao;

import org.nagarro.disasterhelp.registration.models.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, Integer> {

	public Receiver findByEmailAndPassword(String email, String password);
	public Receiver findByEmail(String email);
}
