package com.cn.cnpayment.service;

import jakarta.transaction.Transactional;
import com.cn.cnpayment.dal.PaymentDAL;
import com.cn.cnpayment.dal.PaymentRepository;
import com.cn.cnpayment.entity.PaymentReview;
import com.cn.cnpayment.exception.ElementAlreadyExistException;
import com.cn.cnpayment.exception.InvalidInputException;
import com.cn.cnpayment.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cn.cnpayment.entity.Payment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PaymentService {

//	@Autowired
//	PaymentDAL paymentDAL;
	
	@Autowired
	PaymentRepository paymentRepository;

	@Transactional
	public Payment getPaymentById(int id) {

		Payment payment=paymentRepository.findById(id).get();

		return payment;
	}

	@Transactional
	public List<Payment> getPaymentByPaymentType(String paymentType) {

		ArrayList<String> validPayments = new ArrayList<String>() {{
			add("Cash");
			add("Debit");
			add("Credit");
		}};
		Boolean isValidPayment=false;
		for(String validPayment : validPayments)
		{
			if(validPayment.equalsIgnoreCase(paymentType))
			{
				isValidPayment=true;
				break;
			}
		}
		if(isValidPayment==false)
		{
			throw new InvalidInputException("Payment type "+ paymentType + "is incorrect");
		}
		
		List<Payment> allPayments=getAllPayments();
		List<Payment> paymentsByPaymentType = new ArrayList<>();
		for(Payment payment : allPayments)
		{
			if(payment.getPaymentType().equalsIgnoreCase(paymentType))
			{
				paymentsByPaymentType.add(payment);
			}
		}
		

		return paymentsByPaymentType;
	}

	@Transactional
	public List<Payment> getPaymentByDescriptionKeyword(String keyword) {

		List<Payment> allPayments=getAllPayments();
		List<Payment> paymentsByDescription = new ArrayList<>();
		for(Payment payment : allPayments)
		{
			if(payment.getDescription().contains(keyword))
			{
				paymentsByDescription.add(payment);
			}
		}
	
		
		return paymentsByDescription;
	}

	@Transactional
	public List<Payment> getAllPaymentsByCurrency(String currency) {
		List<String> validCurrencies = new ArrayList<>();
		Collections.addAll(validCurrencies,"INR","Rupee","Dollar","Yen","Pound","USD");
		boolean isValidCurrency=false;
		for(String validCurrency : validCurrencies)
		{
			if(validCurrency.equalsIgnoreCase(currency))
			{
				isValidCurrency=true;
				break;
			}
		}
		if(!isValidCurrency)
		{
			throw new InvalidInputException("Currency "+ currency + "is invalid.");
		}
		List<Payment> allPayments=getAllPayments();
		List<Payment> paymentsByCurrency = new ArrayList<>();
		for(Payment payment : allPayments)
		{
			if(payment.getPaymentDetails().getCurrency().equalsIgnoreCase(currency))
			{
				paymentsByCurrency.add(payment);
			}
		}
		return paymentsByCurrency;

		
	}

	@Transactional
	public List<Payment> getAllPayments() {

		List<Payment> payment = (List<Payment>) paymentRepository.findAll();
		System.out.println(payment);
		
		return payment;
	}

	@Transactional
	public void addPayment(Payment payment)  {
		
		
		paymentRepository.save(payment);
	}

	@Transactional
	public void update(Payment updatePayment) {
		paymentRepository.save(updatePayment);
	}

	@Transactional
	public void updateDescription(int id, String description) {
		Payment currentPayment = this.getPaymentById(id);
		currentPayment.setDescription(description);
		paymentRepository.save(currentPayment);
	}

	@Transactional
	public void delete(int id) {
		paymentRepository.deleteById(id);
	}



	@Transactional
	public List<PaymentReview> getPaymentReviews(int paymentId) {
		
		Payment payment = this.getPaymentById(paymentId);
		List<PaymentReview> paymentReviews=payment.getPaymentReviews();
		
		
		return paymentReviews;
	}

	@Transactional
	public List<Payment> getAllPaymentsByQueryType(String queryType) {

		ArrayList<String> validQueries = new ArrayList<String>() {{
			add("Payment Issue");
			add("Bank Issue");
			add("Merchant Issue");
		}};
		Boolean isValidQueryType=false;
		for(String validQuery : validQueries)
		{
			if(validQuery.equalsIgnoreCase(queryType))
			{
				isValidQueryType=true;
				break;
			}
		}
		if(isValidQueryType==false)
		{
			throw new InvalidInputException("QueryType "+ queryType + "is invalid.");
		}
		
		List<Payment> allPayments=getAllPayments();
		List<Payment> paymentsByReviews = new ArrayList<Payment>();
		for(Payment payment : allPayments)
		{
			for(PaymentReview paymentReview : payment.getPaymentReviews())
			{
				if(paymentReview.getQueryType().equalsIgnoreCase(queryType))
				{
					paymentsByReviews.add(payment);
					break;
				}
			}
		}
		return paymentsByReviews;
		
	}

}

