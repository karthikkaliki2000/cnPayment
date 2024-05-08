package com.cn.cnpayment.service;

import com.cn.cnpayment.dal.OrderDal;
import com.cn.cnpayment.dal.OrderRepository;
import com.cn.cnpayment.dal.PaymentDAL;
import com.cn.cnpayment.dal.PaymentRepository;
import com.cn.cnpayment.entity.Orders;
import com.cn.cnpayment.entity.Payment;
import com.cn.cnpayment.exception.NotFoundException;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class OrderService {

	
//	@Autowired
//	OrderDal orderDal;
//	
//	@Autowired
//	PaymentDAL paymentDAL;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Transactional
    public Orders getOrderById(int id) {
//		Orders orders=orderDal.getById(id);

		
		Orders orders=(Orders) orderRepository.findById(id).get();
		
    	return orders;
		
    }


   
	@Transactional
    public List<Orders> getAllOrders() {
		//List<Orders> orders=orderDal.getAllOrders();
//		List<Orders> orders=new ArrayList<Orders>();
//		orderRepository.findAll().forEach(o->orders.add((Orders) o));
		List<Orders> orders=(List<Orders>) orderRepository.findAll();
		if(orders.isEmpty()) {
			throw new NotFoundException("not found");
		}
    	return orders;
    }


   
	@Transactional
    public void saveOrder(Orders orders) {
		Orders newOrders=new Orders();
		newOrders.setAmount(orders.getAmount());
		newOrders.setCategory(orders.getCategory());
		newOrders.setName(orders.getName());
		newOrders.setQuantity(orders.getQuantity());
		List<Payment> paymentsList=new ArrayList<Payment>();
		for(Payment p:orders.getPayments()) {
			Payment currentPayment=paymentRepository.findById(p.getId()).get();
			paymentsList.add(currentPayment);
		}
		
		newOrders.setPayments(paymentsList);
		
		orderRepository.save(newOrders);
    }


	@Transactional
    public void delete(int id) {
		orderRepository.deleteById(id);
    }


}
