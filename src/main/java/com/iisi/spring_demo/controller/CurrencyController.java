package com.iisi.spring_demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iisi.spring_demo.model.entity.Currency;
import com.iisi.spring_demo.service.CurrencyService;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyController {

	@Autowired
	private CurrencyService currencyService;

	// 1. 查詢 (Read All)
	@GetMapping
	public List<Currency> getAllCurrencies() {
		return currencyService.findAll();
	}

	// 2. 查詢 (Read by Code)
	@GetMapping("/{code}")
	public ResponseEntity<Currency> getCurrencyById(@PathVariable String code) {
		return currencyService.findById(code).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// 3. 新增 (Create)
	@PostMapping
	public Currency createCurrency(@RequestBody Currency currency) {
		return currencyService.save(currency);
	}

	// 4. 修改 (Update)
	@PutMapping("/{code}")
	public ResponseEntity<Currency> updateCurrency(@PathVariable String code, @RequestBody Currency currencyDetails) {
		return currencyService.update(code, currencyDetails).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	// 5. 刪除 (Delete)
	@DeleteMapping("/{code}")
	public ResponseEntity<Void> deleteCurrency(@PathVariable String code) {
		if (currencyService.deleteById(code)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
