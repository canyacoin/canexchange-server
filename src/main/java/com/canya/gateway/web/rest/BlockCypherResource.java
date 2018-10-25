package com.canya.gateway.web.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canya.gateway.domain.TokenAddress;
import com.canya.gateway.repository.TokenRepository;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for calling ETH.
 */
@RestController
@RequestMapping("/api")
public class BlockCypherResource {

	private final Logger log = LoggerFactory.getLogger(BlockCypherResource.class);

	private final TokenRepository tokenRepository;

	List<TokenAddress> tokens;

	public BlockCypherResource(TokenRepository tokenRepository) {

		this.tokenRepository = tokenRepository;

		tokens = this.tokenRepository.findAll();
	}

	@GetMapping("/getGas")
	@Timed
	public ResponseEntity<String> getGas() throws URISyntaxException, ClientProtocolException, IOException {

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("https://ethgasstation.info/json/ethgasAPI.json");
		HttpResponse response = client.execute(request);

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		String line = "";
		String textView = "";
		while ((line = rd.readLine()) != null) {
			textView += (line);
		}

		return ResponseEntity.created(new URI("/getGas")).body(textView);
	}

	@GetMapping("/bancor")
	@Timed
	public ResponseEntity<List<TokenAddress>> saveToken() throws IOException, URISyntaxException {

		// status 1 = Enabled
		// staus 2 = Metamask Disable
		// statu 0 = disable
		return ResponseEntity.created(new URI("/api/bancor")).body(tokens);
	};

	@GetMapping("/getbyaddress/{address}")
	@Timed
	public ResponseEntity<TokenAddress> getbyaddress(@PathVariable String address)
			throws IOException, URISyntaxException {

		return ResponseEntity.created(new URI("/api/getbyaddress"))
				.body(this.tokenRepository.findOneByAddress(address));
	};

}
