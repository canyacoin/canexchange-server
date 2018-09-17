package com.canya.gateway.web.rest.util;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.canya.gateway.domain.Tokens;
import com.canya.gateway.repository.TokensRepository;

public class test {

	public static void main(String[] args) throws Exception {
		String imageUrl = "https://s2.coinmarketcap.com/static/img/coins/128x128/";
		String destinationFile = "d:/images/";
		Tokens tokens = new Tokens();
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(new FileReader("C:/Users/ABC/Desktop/bancor.html"));

		RestTemplate rt = new RestTemplate();
		rt.getMessageConverters().add(new StringHttpMessageConverter());
		String uri = new String("https://api.coinmarketcap.com/v2/listings/");
		cpcListing cpc = rt.getForObject(uri, cpcListing.class);

		for (Data dat : cpc.getData()) {
			JSONObject jsonObject = (JSONObject) obj.get("data");
			List<String> data = new ArrayList<>();
			for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if (!StringUtils.contains(key, "BNT") && StringUtils.contains(dat.getSymbol(), key)) {
					tokens.setTokenid(dat.getId());
					tokens.setName(dat.getName());
					tokens.setSymbol(dat.getSymbol());
					// saveImage(imageUrl + dat.getId() + ".png", destinationFile + dat.getId() +
					// ".png");
					data.add(key);
				}
			}
		}

	}

	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

}
