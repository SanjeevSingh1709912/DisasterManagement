package org.nagarro.disasterhelp.registration.dao;

import org.springframework.stereotype.Repository;
import org.nagarro.disasterhelp.registration.models.Provider;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {	
	
	public Provider findByEmailAndPassword(String email, String password);
	public Provider findByEmail(String email);

}
