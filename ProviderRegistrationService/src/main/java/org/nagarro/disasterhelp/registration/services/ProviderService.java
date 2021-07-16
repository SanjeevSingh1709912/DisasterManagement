package org.nagarro.disasterhelp.registration.services;

import java.util.List;
import org.nagarro.disasterhelp.registration.dao.ProviderRepository;
import org.nagarro.disasterhelp.registration.models.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {
	
	@Autowired
	private ProviderRepository providerRepo;
	
	
	public List<Provider> getAllProvider() {
		List<Provider> providerList = (List<Provider>) this.providerRepo.findAll();
		return providerList;
	}

	
	public Provider getProviderById(int id) {
		Provider provider = null;
		provider = this.providerRepo.findById(id).get();
		return provider;
	}
	
	public Provider getProviderByEmailAndPassword(String email, String password) {
		return this.providerRepo.findByEmailAndPassword(email, password);
	}
	
	public Provider getProviderByEmail(String email) {
		return this.providerRepo.findByEmail(email);
	}

	public Provider addProvider(Provider provider) {
		return this.providerRepo.save(provider);
	}

	
	public void deleteProvider(int id) {
		this.providerRepo.deleteById(id);
	}

	
	public void updateProvider(Provider provider, int id) {
		provider.setId(id);
		this.providerRepo.save(provider);
	}


}
