package com.iisi.spring_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iisi.spring_demo.model.dto.CoinDeskResponse;
import com.iisi.spring_demo.model.dto.TransformedResponse;
import com.iisi.spring_demo.service.CoinDeskApiService;

@RestController
@RequestMapping("/api/v1/coindesk")
public class CoinDeskApiController {

	@Autowired
	private CoinDeskApiService coinDeskService;

	/**
	 * 實作內容 2: 呼叫 coindesk 的 API
	 */
	@GetMapping("/original")
	public CoinDeskResponse getCoinDeskData() {
		return coinDeskService.callCoinDeskApi();
	}

	/**
	 * 實作內容 3: 呼叫 coindesk API 並進行資料轉換
	 */
	@GetMapping("/transformed")
	public TransformedResponse getTransformedCoinDeskData() {
		return coinDeskService.getTransformedData();
	}
}
