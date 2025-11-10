package com.iisi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.iisi.spring_demo.model.dto.CoinDeskResponse;
import com.iisi.spring_demo.model.dto.TransformedResponse;
import com.iisi.spring_demo.model.entity.Currency;
import com.iisi.spring_demo.repository.CurrencyRepository;
import com.iisi.spring_demo.service.CoinDeskApiService;

@ExtendWith(MockitoExtension.class)
public class CoinDeskServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private CurrencyRepository currencyRepository;

	@InjectMocks
	private CoinDeskApiService coinDeskApiService;

	private CoinDeskResponse createMockResponseWith(String currencyCode, String rate) {
		CoinDeskResponse mockResponse = new CoinDeskResponse();

		CoinDeskResponse.Time time = new CoinDeskResponse.Time();
		time.setUpdatedISO("2023-10-01T12:00:00+00:00");
		mockResponse.setTime(time);

		// 建立 BpiData
		CoinDeskResponse.BpiData bpiData = new CoinDeskResponse.BpiData();
		bpiData.setCode(currencyCode);
		bpiData.setRateFloat(new BigDecimal(rate)); // 將字串匯率轉為 BigDecimal

		Map<String, CoinDeskResponse.BpiData> bpiMap = new HashMap<>();
		bpiMap.put(currencyCode, bpiData);
		mockResponse.setBpi(bpiMap);

		return mockResponse;
	}

	/**
	 * 測試 1: 驗證當 DB 有資料時，組合是否正確
	 */
	@Test
	void testTransform_WhenCurrencyExists() {
		CoinDeskResponse mockApiData = createMockResponseWith("USD", "50000.0");
		when(restTemplate.getForObject(anyString(), eq(CoinDeskResponse.class))).thenReturn(mockApiData);

		List<Currency> currencies = new ArrayList();
		currencies.add(new Currency("USD", "美金"));
		currencies.add(new Currency("EUR", "歐元"));
		when(currencyRepository.findAll()).thenReturn(currencies);

		TransformedResponse result = coinDeskApiService.getTransformedData();

		assertEquals("美金", result.getCurrencies().get(0).getChineseName());
		assertEquals(new BigDecimal("50000.0"), result.getCurrencies().get(0).getRate());

		OffsetDateTime odt = OffsetDateTime.parse(mockApiData.getTime().getUpdatedISO());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String expectedFormattedTime = odt.atZoneSameInstant(ZoneId.systemDefault()).format(formatter);

		assertEquals(expectedFormattedTime, result.getUpdateTime());
	}

	/**
	 * 測試 2: 驗證當 DB 無資料時 (新幣別)，是否正確回傳 "N/A"
	 */
	@Test
	void testTransform_WhenCurrencyIsNew() {
		CoinDeskResponse mockApiData = createMockResponseWith("JPY", "6000000.0");
		when(restTemplate.getForObject(anyString(), eq(CoinDeskResponse.class))).thenReturn(mockApiData);

		List<Currency> currencies = new ArrayList();
		currencies.add(new Currency("USD", "美金"));
		currencies.add(new Currency("EUR", "歐元"));
		when(currencyRepository.findAll()).thenReturn(currencies);

		TransformedResponse result = coinDeskApiService.getTransformedData();

		assertEquals("N/A", result.getCurrencies().get(0).getChineseName());
		assertEquals(new BigDecimal("6000000.0"), result.getCurrencies().get(0).getRate());
	}

}