package org.nagarro.disasterhelp.donor.dao;


import java.util.List;

import org.nagarro.disasterhelp.donor.model.ProviderOfferDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderDonateRepository extends JpaRepository <ProviderOfferDetails, Integer > {

	List<ProviderOfferDetails> findByProviderId(int id);
	List<ProviderOfferDetails> findByRequestId(int id);

}
