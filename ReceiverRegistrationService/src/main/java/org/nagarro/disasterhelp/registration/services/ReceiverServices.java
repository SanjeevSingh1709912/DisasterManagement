package org.nagarro.disasterhelp.registration.services;

import java.util.List;

import org.nagarro.disasterhelp.registration.dao.ReceiverRepository;
import org.nagarro.disasterhelp.registration.models.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiverServices {
	
	@Autowired
	private ReceiverRepository receiverRepo;
	
//	@Autowired
//	private ReceiverAddressRepository receiverAddressRepo;
	
	public List<Receiver> getAllReceiver() {
		List<Receiver> receiverList = (List<Receiver>) this.receiverRepo.findAll();
		return receiverList;
	}
	
//	public List<ReceiverAddress> getAllReceiverAddressByCity(String city) {
//		List<ReceiverAddress> receiverAddressList = (List<ReceiverAddress>) this.receiverAddressRepo.findByCity(city);
//		return receiverAddressList;
//	}

	
	public Receiver getReceiverById(int id) {
		Receiver receiver = null;
		receiver = this.receiverRepo.findById(id).get();
		return receiver;
	}
	
	
	public Receiver getReceiverByEmailAndPassword(String email, String password) {
		Receiver receiver = null;
		receiver = this.receiverRepo.findByEmailAndPassword(email, password);
		return receiver;
	}
	
	public Receiver getReceiverByEmail(String email) {
		Receiver receiver = null;
		receiver = this.receiverRepo.findByEmail(email);
		return receiver;
	}
	

	public void addReceiver(Receiver receiver) {
		this.receiverRepo.save(receiver);
	}

	
	public void deleteReceiver(int id) {
		this.receiverRepo.deleteById(id);
	}

	
	public void updateReceiver(Receiver receiver, int id) {
		receiver.setId(id);
		this.receiverRepo.save(receiver);
	}	

}
