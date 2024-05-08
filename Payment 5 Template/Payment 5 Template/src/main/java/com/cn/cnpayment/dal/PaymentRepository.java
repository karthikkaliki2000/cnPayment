package com.cn.cnpayment.dal;

import org.springframework.data.repository.CrudRepository;

import com.cn.cnpayment.entity.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer>{

}
