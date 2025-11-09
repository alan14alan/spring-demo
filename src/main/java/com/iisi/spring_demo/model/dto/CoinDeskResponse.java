package com.iisi.spring_demo.model.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinDeskResponse {

	private Time time;
	private String disclaimer;
	private String chartName;
	private Map<String, BpiData> bpi;

	// Getters and Setters
	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public Map<String, BpiData> getBpi() {
		return bpi;
	}

	public void setBpi(Map<String, BpiData> bpi) {
		this.bpi = bpi;
	}

	// 巢狀 Class 對應 "time" 物件
	public static class Time {
		private String updated;
		private String updatedISO;
		private String updateduk;

		// Getters and Setters
		public String getUpdated() {
			return updated;
		}

		public void setUpdated(String updated) {
			this.updated = updated;
		}

		public String getUpdatedISO() {
			return updatedISO;
		}

		public void setUpdatedISO(String updatedISO) {
			this.updatedISO = updatedISO;
		}

		public String getUpdateduk() {
			return updateduk;
		}

		public void setUpdateduk(String updateduk) {
			this.updateduk = updateduk;
		}
	}

	// 巢狀 Class 對應 "bpi" 物件 (例如 "USD", "GBP")
	public static class BpiData {
		private String code;
		private String symbol;
		private String rate;
		private String description;
		@JsonProperty("rate_float") // JSON 的 "rate_float" 對應到 "rateFloat"
		private BigDecimal rateFloat;

		// Getters and Setters
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getSymbol() {
			return symbol;
		}

		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}

		public String getRate() {
			return rate;
		}

		public void setRate(String rate) {
			this.rate = rate;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public BigDecimal getRateFloat() {
			return rateFloat;
		}

		public void setRateFloat(BigDecimal rateFloat) {
			this.rateFloat = rateFloat;
		}
	}
}