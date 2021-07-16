package org.nagarro.disasterhelp.registration.controllers;

import java.util.List;
import org.nagarro.disasterhelp.registration.models.Provider;
import org.nagarro.disasterhelp.registration.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {
	
	@Autowired
	private ProviderService providerService;
	
	
	@GetMapping("/providers")
	public List<Provider> getAllProvider(){
		return this.providerService.getAllProvider();
	}
	
	
	@GetMapping("/providers/{id}")
	public Provider getProvider(@PathVariable("id") int id) {
		
		return this.providerService.getProviderById(id);
	}
	
	@GetMapping("/providers/{email}/{password}")
	public Provider getProviderByEmailAndPassword(@PathVariable String email,
			@PathVariable String password) {	
		return this.providerService.getProviderByEmailAndPassword(email, password);
	}
	
	@GetMapping("/providers/email/{email}")
	public Provider getProviderByEmail(@PathVariable String email) {	
		return this.providerService.getProviderByEmail(email);
	}
	
	
	@PostMapping("/providers")
	public Provider addProvider(@RequestBody Provider provider) {	
		return this.providerService.addProvider(provider);	
	}
	
	
	@DeleteMapping("/provider/{id}")
	public void deleteProvider(@PathVariable("id") int id) {	
		this.providerService.deleteProvider(id);
	}
	
	
	@PutMapping("/provider/{id}")
	public void updateProvider(@RequestBody Provider provider, @PathVariable("id") int id) {		
		this.providerService.updateProvider(provider, id);
	}
}
