package com.iisi.spring_demo.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// 這個 DTO 是我們新 API 的回傳格式
public class TransformedResponse {

	private String updateTime;
	private List<CurrencyDetail> currencies = new ArrayList<>();

	// Getters and Setters
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public List<CurrencyDetail> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<CurrencyDetail> currencies) {
		this.currencies = currencies;
	}

	// 巢狀 Class 對應幣別資訊
	public static class CurrencyDetail {
		private String code;
		private String chineseName;
		private BigDecimal rate;

		public CurrencyDetail(String code, String chineseName, BigDecimal rate) {
			this.code = code;
			this.chineseName = chineseName;
			this.rate = rate;
		}

		// Getters and Setters
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getChineseName() {
			return chineseName;
		}

		public void setChineseName(String chineseName) {
			this.chineseName = chineseName;
		}

		public BigDecimal getRate() {
			return rate;
		}

		public void setRate(BigDecimal rate) {
			this.rate = rate;
		}
	}
}