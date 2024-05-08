package com.cn.cnpayment.service;

import jakarta.transaction.Transactional;

import com.cn.cnpayment.exception.ElementAlreadyExistException;
import com.cn.cnpayment.exception.InvalidInputException;
import com.cn.cnpayment.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cn.cnpayment.dal.PaymentDetailsDAL;
import com.cn.cnpayment.dal.PaymentDetailsRepository;
import com.cn.cnpayment.entity.PaymentDetails;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentDetailsService {

//	@Autowired
//	PaymentDetailsDAL paymentDetailsDAL;
	
	@Autowired
	PaymentDetailsRepository paymentDetailsRepository;

	@Transactional
	public PaymentDetails getPaymentDetailsById(int id) {
	//	PaymentDetails paymentDetails=paymentDetailsDAL.getById(id);
		PaymentDetails paymentDetails=	paymentDetailsRepository.findById(id).get();
	
		return paymentDetails;
	}

	@Transactional
	public List<PaymentDetails> getAllPaymentDetails() {

	//	List<PaymentDetails> paymentDetails = paymentDetailsDAL.getAllPaymentDetails();
		List<PaymentDetails> paymentDetails=(List<PaymentDetails>) paymentDetailsRepository.findAll();
		
		return paymentDetails;
	}

	@Transactional
	public void savePaymentDetails(PaymentDetails newPaymentDetails) {
		List<PaymentDetails> allPaymentDetails = getAllPaymentDetails();
		for(PaymentDetails paymentDetails : allPaymentDetails)
		{
			if(paymentDetails.getId()==newPaymentDetails.getId())
			{
				throw new ElementAlreadyExistException("This paymentDetails already exist.");
			}
		}
		//paymentDetailsDAL.save(newPaymentDetails);
		paymentDetailsRepository.save(newPaymentDetails);
	}

	@Transactional
	public void delete(int id) {
		//paymentDetailsDAL.delete(id);
		paymentDetailsRepository.deleteById(id);
	}

	@Transactional
	public List<PaymentDetails> getByCurrency(String currency){
		if (currency.equals("INR") || currency.equals("Dollar") || currency.equals("Yen") || currency.equals("Pound") ||currency.equals("USD")) {
			
			List<PaymentDetails> allPaymentsDetails=getAllPaymentDetails();
			List<PaymentDetails> paymentsByCurrency = new ArrayList<>();
			for(PaymentDetails paymentDetails : allPaymentsDetails)
			{
				if(paymentDetails.getCurrency().equalsIgnoreCase(currency))
				{
					paymentsByCurrency.add(paymentDetails);
				}
			}
			return paymentsByCurrency;
		}

		else {
			throw new InvalidInputException("Invalid Currency");
		}
	}


	@Transactional
	public void update(PaymentDetails paymentDetails) {
		if (getPaymentDetailsById(paymentDetails.getId())!=null) {
			PaymentDetails paymentDetails1=this.getPaymentDetailsById(paymentDetails.getId());
		paymentDetails1.setAmount(paymentDetails.getAmount());
		paymentDetails1.setCurrency(paymentDetails.getCurrency());
		paymentDetails1.setCreditAccount(paymentDetails.getCreditAccount());
		paymentDetails1.setDebitAccount(paymentDetails.getDebitAccount());
		paymentDetailsRepository.save(paymentDetails);
		}
		else {
			throw new NotFoundException("PaymentDetails with given id not found");
		}
	

}
	
}
