package org.nagarro.disasterhelp.registration.controllers;

import java.util.List;

import org.nagarro.disasterhelp.registration.models.Receiver;
import org.nagarro.disasterhelp.registration.services.ReceiverServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceiverController {
	
	@Autowired
	private ReceiverServices receiverServices;
	
	@GetMapping("/receivers")
	public List<Receiver> getAllReceiver(){
		return this.receiverServices.getAllReceiver();
	}
	
	
	@GetMapping("/receivers/{id}")
	public Receiver getReceiver(@PathVariable("id") int id) {
		
		return this.receiverServices.getReceiverById(id);
	}
	
	
	@GetMapping("/receivers/{email}/{password}")
	public Receiver getReceiver(@PathVariable("email") String email, @PathVariable("password") String password) {	
		return this.receiverServices.getReceiverByEmailAndPassword(email, password);
	}
	
	
	@GetMapping("/receivers/email/{email}")
	public Receiver getReceiverByEmail(@PathVariable String email) {	
		return this.receiverServices.getReceiverByEmail(email);
	}
	
//	@GetMapping("receivers/city/{city}")
//	public List<ReceiverAddress> getAllReceiverAddressByCity(String city){
//		return this.receiverServices.getAllReceiverAddressByCity(city);
//	}
	

	@PostMapping("/receivers")
	public void addReceiver(@RequestBody Receiver receiver) {	
		System.out.print(receiver);
		this.receiverServices.addReceiver(receiver);	
	}
	
	
	@DeleteMapping("/receivers/{id}")
	public void deleteReceiver(@PathVariable("id") int id) {	
		this.receiverServices.deleteReceiver(id);
	}
	
	
	@PutMapping("/receivers/{id}")
	public void updateReceiver(@RequestBody Receiver receiver, @PathVariable("id") int id) {		
		this.receiverServices.updateReceiver(receiver, id);
	}

}
