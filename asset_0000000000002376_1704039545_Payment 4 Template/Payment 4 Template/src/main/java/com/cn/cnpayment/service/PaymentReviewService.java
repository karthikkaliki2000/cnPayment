package com.cn.cnpayment.service;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.cnpayment.dal.PaymentReviewDAL;
import com.cn.cnpayment.entity.PaymentReview;
import com.cn.cnpayment.exception.ElementAlreadyExistException;
import com.cn.cnpayment.exception.InvalidInputException;
import com.cn.cnpayment.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List; 

@Service
public class PaymentReviewService {

// Autowire the PaymentReviewDAL object.
	@Autowired
	PaymentReviewDAL paymentReviewDAL;

	@Transactional
	public PaymentReview getPaymentReviewById(int id) {
	
		PaymentReview pr=paymentReviewDAL.getById(id);
		if(pr==null) {
			throw new NotFoundException("not found id");
		}
		return pr;
	}

	@Transactional
	public List<PaymentReview> getAllPaymentReviews() {
		/**
		 1. This method fetches the list of all PaymentReviews.
		 2. If no paymentReview is found then it throws NotFoundException.
		 **/
		List<PaymentReview> prList=paymentReviewDAL.getAllPaymentReview();
		if(prList==null || prList.isEmpty()) {
			throw new NotFoundException("not found");
		}
		return prList;
	}

	@Transactional
	public void savePaymentReview(PaymentReview newPaymentReview) {
		
		System.out.println("pr service"+newPaymentReview.getQueryPersonName());
		PaymentReview existpr=paymentReviewDAL.getById(newPaymentReview.getId());;
		
		if(existpr==null) {
			paymentReviewDAL.save(newPaymentReview);
		}
		else {
			throw new ElementAlreadyExistException("already exist");
		}
	}

	@Transactional
	public void delete(int id) {
		/**
		 1. This method deletes PaymentReview for a specific id.
		 2. If no paymentReview is found for the given id, then it throws NotFoundException.
		 **/
		PaymentReview existpr=getPaymentReviewById(id);
		if(existpr==null) {
			throw new NotFoundException("not exist");
		}
		else {
			paymentReviewDAL.delete(id);
		}
		
	}

	@Transactional
	public List<PaymentReview> getPaymentReviewByQueryType(String queryType){
		
		if(queryType.isEmpty()) {
			throw new InvalidInputException("empty query type");
		}

		List<PaymentReview> prList=paymentReviewDAL.getByQueryType(queryType);
		if(prList.isEmpty()) {
			throw new InvalidInputException("invalid input query type");
		}
		return prList;
      
	}

}
