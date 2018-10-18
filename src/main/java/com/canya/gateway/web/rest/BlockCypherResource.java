package com.canya.gateway.web.rest;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canya.gateway.domain.TokenAddress;
import com.canya.gateway.repository.TokenRepository;
import com.canya.gateway.repository.UserRepository;
import com.canya.gateway.service.MailService;
import com.canya.gateway.service.UserService;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * REST controller for calling ETH.
 */
@RestController
@RequestMapping("/api")
public class BlockCypherResource {

	private final Logger log = LoggerFactory.getLogger(BlockCypherResource.class);

	private final UserService userService;

	private final UserRepository userRepository;

	private final MailService mailService;

	private final TokenRepository tokenRepository;

	List<TokenAddress> tokens;

	public BlockCypherResource(UserService userService, UserRepository userRepository, MailService mailService,
			TokenRepository tokenRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
		this.mailService = mailService;

		this.tokenRepository = tokenRepository;

		tokens = tokenRepository.findAll();
	}

//	@GetMapping("/getTokens")
//	@Timed
//	public ResponseEntity<List<Coinmarketcap>> getTokens()
//			throws ClientProtocolException, IOException, URISyntaxException {
//
//		List<Coinmarketcap> cmc = cMCRepository.findAllByStatus(false);
//		System.out.println(cmc);
//		return ResponseEntity.created(new URI("/api/getTokens")).body(cmc);
//	}
//
//	@GetMapping("/saveToken")
//	@Timed
//	public void saveToken() throws ClientProtocolException, IOException {
//		HttpClient client = HttpClientBuilder.create().build();
//		HttpGet request = new HttpGet("https://api.coinmarketcap.com/v2/listings/");
//		HttpResponse response = client.execute(request);
//
//		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//
//		StringBuffer result = new StringBuffer();
//		String line = "";
//		while ((line = rd.readLine()) != null) {
//			result.append(line);
//		}
//
//		JSONObject obj = new JSONObject(result.toString());
//		JSONArray arr = obj.getJSONArray("data");
//
//		for (int i = 0; i < arr.length(); i++) {
//			ObjectMapper jsonParserClient = new ObjectMapper();
//			Coinmarketcap data = jsonParserClient.readValue(arr.getJSONObject(i).toString(), Coinmarketcap.class);
//			cMCRepository.save(data);
//		}
//
//		request.releaseConnection();
//	}
//	
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

//	@GetMapping("/bancor")
//	@Timed
//	public ResponseEntity<List<Tokens>> saveToken() throws IOException, ParseException, URISyntaxException {
//
//		File file = new File(Globals.ERC20_PATH);
//
//		BufferedReader br = new BufferedReader(new FileReader(file));
//
//		String st;
//		String data = "";
//		while ((st = br.readLine()) != null)
//			data += st;
//
//		br.close();
//
//		Gson gson = new Gson();
//		Type type = new TypeToken<List<TokenAddress>>() {
//		}.getType();
//		List<TokenAddress> tokenAddress = gson.fromJson(data, type);
//
//		List<Tokens> tokens = tokensRepository.findAll();
//		List<Tokens> tokensFin = new ArrayList<>();
//
//		for (Tokens token : tokens) {
//			for (TokenAddress tA : tokenAddress) {
//				if (StringUtils.equals(token.getSymbol(), StringUtils.replace(tA.getSymbol(), "$", ""))
//						&& StringUtils.isNotEmpty(tA.getAddress())) {
//					token.setAddress(tA.getAddress());
//					token.setDecimal(tA.getDecimals());
//					tokensFin.add(token);
//					break;
//				}
//
//			}
//		}
//
//		return ResponseEntity.created(new URI("/api/bancor")).body(tokensFin);
//	};

	@GetMapping("/bancor")
	@Timed
	public ResponseEntity<List<TokenAddress>> saveToken() throws IOException, URISyntaxException {

		return ResponseEntity.created(new URI("/api/bancor")).body(tokens);
	};

//	@GetMapping("/bancortest")
//	@Timed
//	public ResponseEntity<List<TokenAddress>> bancortest()
//			throws IOException, ParseException, URISyntaxException, InterruptedException {
//
//		File file = new File(Globals.ERC20_PATH_SAMPLE);
//
//		BufferedReader br = new BufferedReader(new FileReader(file));
//
//		String st;
//		String data = "";
//		while ((st = br.readLine()) != null)
//			data += st;
//
//		br.close();
//
//		Gson gson = new Gson();
//		Type type = new TypeToken<List<TokenAddress>>() {
//		}.getType();
//		List<TokenAddress> tokenAddress = gson.fromJson(data, type);
//
//		List<TokenAddress> tokenAddressF = new ArrayList<>();
//
//		for (TokenAddress tA : tokenAddress) {
//			RestTemplate rt = new RestTemplate();
//			rt.getMessageConverters().add(new StringHttpMessageConverter());
//			System.out.println("http://api.ethplorer.io/getTokenInfo/" + tA.getAddress() + "?apiKey=freekey");
//			String uri = new String("http://api.ethplorer.io/getTokenInfo/" + tA.getAddress() + "?apiKey=freekey");
//			ETHP cpc = rt.getForObject(uri, ETHP.class);
//
//			Thread.sleep(2500);
//			if (StringUtils.equals(tA.getAddress(), cpc.getAddress())) {
//				tA.setDecimals(cpc.getDecimals());
//				tokenAddressF.add(tA);
//				tokenRepository.save(tA);
//			}
//		}
//
//		return ResponseEntity.created(new URI("/api/bancor")).body(tokenAddressF);
//	};

//	@GetMapping("/getdata")
//	@Timed
//	public void getdata() throws IOException, ParseException, URISyntaxException {
//
//		String imageUrl = "https://s2.coinmarketcap.com/static/img/coins/128x128/";
//		String destinationFile = "d:/images/";
//
//		JSONParser parser = new JSONParser();
//		JSONObject obj = (JSONObject) parser.parse(new FileReader("C:/Users/ABC/Desktop/bancor.html"));
//
//		RestTemplate rt = new RestTemplate();
//		rt.getMessageConverters().add(new StringHttpMessageConverter());
//		String uri = new String("https://api.coinmarketcap.com/v2/listings/");
//		cpcListing cpc = rt.getForObject(uri, cpcListing.class);
//
//		JSONObject jsonObject = (JSONObject) obj.get("data");
//
//		for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
//			String key = (String) iterator.next();
//
//			for (Data dat : cpc.getData()) {
//				if (!StringUtils.contains(key, "BNT") && StringUtils.contains(dat.getSymbol(), key)) {
//					Tokens tokens = new Tokens();
//					tokens.setTokenid(dat.getId());
//					tokens.setName(dat.getName());
//					tokens.setSymbol(key);
//
//					System.out.println(tokens);
//					// tokensRepository.save(tokens);
//				}
//			}
//		}
//
//	}

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
