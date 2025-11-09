package com.iisi.spring_demo.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.iisi.spring_demo.model.dto.CoinDeskResponse;
import com.iisi.spring_demo.model.dto.TransformedResponse;
import com.iisi.spring_demo.model.entity.Currency;
import com.iisi.spring_demo.repository.CurrencyRepository;

@Service
public class CoinDeskApiService {

	private static final String COINDESK_API_URL = "https://kengp3.github.io/blog/coindesk.json";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CurrencyRepository currencyRepository;

	// 呼叫 coindesk API
	public CoinDeskResponse callCoinDeskApi() {
		return restTemplate.getForObject(COINDESK_API_URL, CoinDeskResponse.class);
	}

	// 呼叫並轉換資料
	public TransformedResponse getTransformedData() {
		// 1.呼叫原始 API
		CoinDeskResponse originalData = callCoinDeskApi();

		TransformedResponse response = new TransformedResponse();

		// 2.轉換時間格式(1990/01/01 00:00:00)
		String isoTime = originalData.getTime().getUpdatedISO();
		OffsetDateTime odt = OffsetDateTime.parse(isoTime);

		// 將時間轉換為系統預設時區 (例如 Asia/Taipei)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String formattedTime = odt.atZoneSameInstant(ZoneId.systemDefault()).format(formatter);
		response.setUpdateTime(formattedTime);

		// 步驟 3: 組合幣別資訊

		List<Currency> currencyList = currencyRepository.findAll();

		List<TransformedResponse.CurrencyDetail> currencyDetails = originalData.getBpi().entrySet().stream()
				.map(entry -> {
					String code = entry.getKey();
					CoinDeskResponse.BpiData bpiData = entry.getValue();

					
					Optional<Currency> filterCurrency = currencyList.stream().filter(c -> c.getCode().equals(code))
							.findFirst();
					String chineseName = filterCurrency.isPresent() ? filterCurrency.get().getChineseName() : "N/A"; // 如果資料庫沒設定，顯示
																														// N/A

					return new TransformedResponse.CurrencyDetail(code, chineseName, bpiData.getRateFloat());
				}).collect(Collectors.toList());

		response.setCurrencies(currencyDetails);
		return response;
	}
}
