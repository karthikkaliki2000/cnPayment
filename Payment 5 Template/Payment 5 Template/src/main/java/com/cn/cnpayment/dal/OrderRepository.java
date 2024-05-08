package com.cn.cnpayment.dal;

import org.springframework.data.repository.CrudRepository;

import com.cn.cnpayment.entity.Orders;


public interface OrderRepository extends CrudRepository<Orders, Integer>{

	

}
