package com.iisi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.iisi.spring_demo.SpringDemoApplication;
import com.iisi.spring_demo.model.entity.Currency;
import com.iisi.spring_demo.repository.CurrencyRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SpringDemoApplication.class)
@AutoConfigureMockMvc
@Transactional
public class CurrencyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testCreateCurrency() throws Exception {
		Currency jpy = new Currency("JPY", "日圓");

		System.out.println("--- 測試新增 (Create) API ---");
		mockMvc.perform(post("/api/v1/currency").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(jpy))).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is("JPY"))).andExpect(jsonPath("$.chineseName", is("日圓")))
				.andDo(result -> System.out.println(result.getResponse().getContentAsString()));
	}

	@Test
	void testGetAllCurrencies() throws Exception {
		System.out.println("--- 測試查詢全部 (Read All) API ---");

		String expectedJson = "[" + "{'code':'USD', 'chineseName':'美金'}," 
		+ "{'code':'EUR', 'chineseName':'歐元'},"
		+ "{'code':'GBP', 'chineseName':'英鎊'}" 
		+ "]";

		MvcResult result = mockMvc.perform(get("/api/v1/currency")).andExpect(status().isOk()).andReturn();

		String actualJson = result.getResponse().getContentAsString();

		// 4. JSONAssert 比對
		JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.NON_EXTENSIBLE);
		System.out.println(actualJson);
	}

	@Test
	void testGetCurrencyById() throws Exception {
		System.out.println("--- 測試查詢單筆 (Read By Id) API ---");
		mockMvc.perform(get("/api/v1/currency/USD")).andExpect(status().isOk()).andExpect(jsonPath("$.code", is("USD")))
				.andExpect(jsonPath("$.chineseName", is("美金")))
				.andDo(result -> System.out.println(result.getResponse().getContentAsString()));

		// 測試找不到
		mockMvc.perform(get("/api/v1/currency/XYZ")).andExpect(status().isNotFound());
	}

	@Test
	void testUpdateCurrency() throws Exception {
		Currency updatedUsd = new Currency("USD", "新美金");

		System.out.println("--- 測試修改 (Update) API ---");
		mockMvc.perform(put("/api/v1/currency/USD").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedUsd))).andExpect(status().isOk())
				.andExpect(jsonPath("$.chineseName", is("新美金")))
				.andDo(result -> System.out.println(result.getResponse().getContentAsString()));
	}

	@Test
	void testDeleteCurrency() throws Exception {
		System.out.println("--- 測試刪除 (Delete) API ---");
		mockMvc.perform(delete("/api/v1/currency/EUR")).andExpect(status().isOk())
				.andDo(result -> System.out.println("刪除 EUR 成功"));

		// 驗證是否真的被刪除
		mockMvc.perform(get("/api/v1/currency/EUR")).andExpect(status().isNotFound());
	}
}
