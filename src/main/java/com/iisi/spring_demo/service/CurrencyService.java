package com.iisi.spring_demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iisi.spring_demo.model.entity.Currency;
import com.iisi.spring_demo.repository.CurrencyRepository;

@Service
public class CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepository;

	// 查詢所有幣別
	public List<Currency> findAll() {
		return currencyRepository.findAll();
	}

	// 依照 Code 查詢幣別
	public Optional<Currency> findById(String code) {
		return currencyRepository.findById(code);
	}

	// 新增幣別
	public Currency save(Currency currency) {
		return currencyRepository.save(currency);
	}

	// 修改幣別
	public Optional<Currency> update(String code, Currency currencyDetails) {
		return currencyRepository.findById(code).map(existingCurrency -> {
			existingCurrency.setChineseName(currencyDetails.getChineseName());
			return currencyRepository.save(existingCurrency);
		});
	}

	// 刪除幣別
	public boolean deleteById(String code) {
		if (currencyRepository.existsById(code)) {
			currencyRepository.deleteById(code);
			return true;
		}
		return false;
	}
}