package com.cn.cnpayment.service;

import jakarta.transaction.Transactional;

import com.cn.cnpayment.exception.ElementAlreadyExistException;
import com.cn.cnpayment.exception.InvalidInputException;
import com.cn.cnpayment.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.cnpayment.dal.PaymentDetailsRepository;
import com.cn.cnpayment.dal.PaymentReviewDAL;
import com.cn.cnpayment.dal.PaymentReviewRepository;
import com.cn.cnpayment.entity.PaymentReview;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentReviewService {

//	@Autowired
//	PaymentReviewDAL paymentReviewDAL;
	
	@Autowired
	PaymentReviewRepository paymentReviewRepository;

	@Transactional
	public PaymentReview getPaymentReviewById(int id) {
		//PaymentReview paymentReview=paymentReviewDAL.getById(id);
		PaymentReview paymentReview=paymentReviewRepository.findById(id).get();
		
		return paymentReview;
	}

	@Transactional
	public List<PaymentReview> getAllPaymentReviews() {
		//List<PaymentReview> paymentReview = paymentReviewDAL.getAllPaymentReview();
		List<PaymentReview> paymentReview =(List<PaymentReview>) paymentReviewRepository.findAll();
		
		return paymentReview;
	}

	@Transactional
	public void savePaymentReview(PaymentReview newPaymentReview) {

		if (paymentReviewRepository.findById(newPaymentReview.getId()).get()==null) {
			paymentReviewRepository.save(newPaymentReview);

		}
		else {
			throw new ElementAlreadyExistException("Payment Review with given already exists");
		}

	}

	@Transactional
	public void delete(int id) {
		PaymentReview paymentReview = paymentReviewRepository.findById(id).get();
		if (paymentReview != null) {
			paymentReviewRepository.deleteById(id);
		} else {
			throw new NotFoundException("No paymentReview found with id: " + id);
		}

	}

	@Transactional
	public List<PaymentReview> getPaymentReviewByQueryType(String queryType){
		
		List<PaymentReview> allPaymentReviews=this.getAllPaymentReviews();
		List<PaymentReview> paymentReviewsByQueryType = new ArrayList<>();
		for(PaymentReview paymentReview : allPaymentReviews)
		{
			if(paymentReview.getQueryType().equalsIgnoreCase(queryType))
			{
			paymentReviewsByQueryType.add(paymentReview);
			}
		}
		return paymentReviewsByQueryType;
	}

}
