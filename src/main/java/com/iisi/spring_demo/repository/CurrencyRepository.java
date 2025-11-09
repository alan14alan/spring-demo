package com.iisi.spring_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iisi.spring_demo.model.entity.Currency;



@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {

	// Spring Data JPA 會自動提供
	// save(), findById(), findAll(), deleteById()... 等方法
}
