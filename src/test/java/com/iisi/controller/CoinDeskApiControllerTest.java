package com.iisi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iisi.spring_demo.SpringDemoApplication;
import com.iisi.spring_demo.model.dto.CoinDeskResponse;
import com.iisi.spring_demo.repository.CurrencyRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 測試 3 & 4: 測試呼叫 coindesk API 及其轉換 API * 我們使用 @MockBean 來 Mock RestTemplate，
 * 這樣我們的測試就不會真的打到外部 API，確保測試穩定性
 */
@SpringBootTest(classes = SpringDemoApplication.class)
@AutoConfigureMockMvc
public class CoinDeskApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * 測試 3: 測試呼叫 coindesk API，並顯示其內容
	 */
	@Test
	void testGetOriginalCoinDeskData() throws Exception {
		System.out.println("--- 測試 3: 呼叫原始 Coindesk API (/original) ---");
		mockMvc.perform(get("/api/v1/coindesk/original")).andExpect(status().isOk())
				.andDo(result -> System.out.println(result.getResponse().getContentAsString()));
	}

	/**
	 * 測試 4: 測試呼叫資料轉換的API，並顯示其內容
	 */
	@Test
	void testGetTransformedCoinDeskData() throws Exception {

		System.out.println("--- 測試 4: 呼叫轉換後 API (/transformed) ---");
		mockMvc.perform(get("/api/v1/coindesk/transformed")).andExpect(status().isOk())
				.andExpect(jsonPath("$.updateTime").exists()) // 驗證時間存在
				.andExpect(jsonPath("$.currencies[0].code").exists())
				.andExpect(jsonPath("$.currencies[0].chineseName").exists()) // 來自 data.sql
				.andExpect(jsonPath("$.currencies[0].rate").exists())
				.andDo(result -> System.out.println(result.getResponse().getContentAsString()));
	}
}
