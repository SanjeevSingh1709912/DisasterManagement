package org.nagarro.disasterhelp.consumer.dao;


import java.util.List;

import org.nagarro.disasterhelp.consumer.models.ReceiverRequestDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiverRequestRepository extends JpaRepository <ReceiverRequestDetails, Integer > {

	List<ReceiverRequestDetails> findByReceiverId(int id);
	List<ReceiverRequestDetails> findByOfferId(int id);

}
