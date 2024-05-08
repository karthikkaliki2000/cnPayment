package com.cn.cnpayment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cn.cnpayment.entity.Payment;
import com.cn.cnpayment.service.PaymentService;
import java.util.List;

 
@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	PaymentService paymentService;


	@GetMapping("/id/{id}")
	public Payment getPaymentById(@PathVariable int id)
	{
	
		return paymentService.getPaymentById(id);
	}


	@GetMapping("/allPayments")
	public List<Payment> getAllPayments()
	{
		
		return paymentService.getAllPayments();
	}


	
	@GetMapping("/paymentType/{paymentType}")
	public List<Payment> getPaymentByPaymentType(@PathVariable String paymentType)
	{
	
		return paymentService.getPaymentByPaymentType(paymentType);
	}


	
	@GetMapping("/description/{keyword}")
	public List<Payment> getPaymentByDescriptionKeyword(@PathVariable String keyword)
	{
	
		return paymentService.getPaymentByDescriptionKeyword(keyword);
	}



	@PostMapping("/save")
	public void addPayment(@RequestBody Payment payment){
		paymentService.addPayment(payment);
	}



}
