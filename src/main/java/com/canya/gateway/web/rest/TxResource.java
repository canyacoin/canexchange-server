package com.canya.gateway.web.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import com.canya.gateway.domain.Transaction;
import com.canya.gateway.repository.TransactionRepository;
import com.canya.gateway.repository.UserRepository;
import com.canya.gateway.service.MailService;
import com.canya.gateway.service.TransactionService;
import com.canya.gateway.service.UserService;
import com.canya.gateway.service.dto.CanYaCoin;
import com.canya.gateway.service.dto.Etherscan;
import com.canya.gateway.service.dto.Result;
import com.canya.gateway.service.dto.Token;
import com.canya.gateway.service.dto.TransactionDTO;
import com.canya.gateway.web.rest.util.Globals;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.jhipster.config.JHipsterProperties;

/**
 * REST controller for managing the transaction
 */
@RestController
@RequestMapping("/api")
public class TxResource {

	private final Logger log = LoggerFactory.getLogger(TxResource.class);

	private final UserRepository userRepository;

	private final UserService userService;

	private final TransactionRepository transactionRepository;

	private final TransactionService transactionService;

	private final MailService mailService;

	private static final String BASE_URL = "baseUrl";

	private final JHipsterProperties jHipsterProperties;

	private final JavaMailSender javaMailSender;

	private final MessageSource messageSource;

	private final SpringTemplateEngine templateEngine;

	private static final String EMAIL = "email";

	private static String key = "T4RFNMPATQFVFE7NSNYCXF2R89CWM4HGBK";

	public TxResource(UserRepository userRepository, UserService userService, MailService mailService,
			TransactionRepository transactionRepository, JHipsterProperties jHipsterProperties,
			JavaMailSender javaMailSender, MessageSource messageSource, SpringTemplateEngine templateEngine,
			TransactionService transactionService ) {

		this.userRepository = userRepository;
		this.userService = userService;
		this.mailService = mailService;
		this.transactionRepository = transactionRepository;
		this.jHipsterProperties = jHipsterProperties;
		this.javaMailSender = javaMailSender;
		this.messageSource = messageSource;
		this.templateEngine = templateEngine;
		this.transactionService = transactionService;
	}

	public enum Status {
		INITIATED, TRANSFERRED, CONNECTED, DOWN, IDENTIFIED, AUTHENTICATED, AUTHFAIL, COMPLETE, ERROR, READY, TIMEOUT,
		NEEDS_INPUT, CREATED, RESTART, MOREINFO, RESTARTED, UNRESTARTABLE
	}

	

	/**
	 * POST /saveTransaction : register the user.
	 */
	@PostMapping("/saveTransaction")
	@Timed
	@ResponseStatus(HttpStatus.CREATED)
	public void saveTransaction(@Valid @RequestBody TransactionDTO transaction) {
		log.debug("Called saveTransaction {}", transaction);
		transaction.setStatus(Status.INITIATED.name());
		Transaction tx = transactionService.saveTransaction(transaction);

		log.debug("Tx Registered {}", tx );

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		long Seconds = 10;

		Timer timer = new Timer();

		timer.schedule(new TimerTask() {
			int counter = 0;

			@Override
			public void run() {
				try {
					Double comparedValue = null;
					String url = "";
					if (StringUtils.equals(tx.getCurrency(), "ETH")) {
						transaction.setStatus(Status.CONNECTED.name());
						url = "http://api" + Globals.CAN_NETWORK
								+ "etherscan.io/api?module=account&action=txlist&address=" + Globals.ETHER_ADDRESS
								+ "&startblock=0&endblock=999999999&sort=asc&apikey=" + key;
						comparedValue = Double.parseDouble(tx.getEth());
					} else {

						url = "http://api" + Globals.CAN_NETWORK
								+ "etherscan.io/api?module=account&action=tokentx&address=" + Globals.ETHER_ADDRESS
								+ "&startblock=0&endblock=999999999&sort=asc&apikey=" + key;
						comparedValue = Double.parseDouble(tx.getEth());
					}

					System.out.println(url);

					HttpClient client = HttpClientBuilder.create().build();
					if (counter == 360) {
						Transaction txData = transactionRepository.findOneByOrderid(tx.getOrderid());
						txData.setStatus(Status.TIMEOUT.name());
						transactionRepository.save(txData);
						timer.cancel();
					}

					counter++;
					HttpGet request = new HttpGet(url);
					HttpResponse response = client.execute(request);

					System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

					BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

					StringBuffer result = new StringBuffer();
					String line = "";
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
					rd.close();
					request.abort();
					ObjectMapper jsonParserClient = new ObjectMapper();

					Etherscan etherscan = jsonParserClient.readValue(result.toString(), Etherscan.class);
					for (Result ethplorer : etherscan.getResult()) {
						Date longdate = new java.util.Date(Long.parseLong(ethplorer.getTimeStamp()) * 1000);
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String dateString = formatter.format(longdate);
						Double value = Double.parseDouble(ethplorer.getValue()) / 1000000000000000000l;

						if (StringUtils.equals(compareDates(dateString, dateFormat.format(date)), "after")
								&& Double.compare(value, comparedValue) == 0) {
							log.debug("Tx Processed {}", tx.getOrderid());
							System.out.println("Tx Processed {}");
							Transaction txData = transactionRepository.findOneByOrderid(tx.getOrderid());
							txData.setStatus(Status.IDENTIFIED.name());
							txData.setHash(ethplorer.getHash());
							txData.setDate(dateString);
							transactionRepository.save(txData);

							timer.cancel();
							break;
						}
					}
					request.releaseConnection();

				} catch (Exception e) {
					log.error("Tx Error {}", e.getMessage());
					timer.cancel();
					e.printStackTrace();
				}

			}
		}, 0, 1000 * Seconds);
	}

	public static String compareDates(String d1, String d2) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = sdf.parse(d1);
			Date date2 = sdf.parse(d2);

			if (date1.after(date2)) {
				return "after";
			}
			if (date1.before(date2)) {
				return "before";
			}

			if (date1.equals(date2)) {
				return "equal";
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * POST /saveTransaction : register the user.
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	@PostMapping("/sendMail")
	@Timed
	@ResponseStatus(HttpStatus.CREATED)
	public void sentMail(@RequestBody Transaction tx) {
		System.out.println(tx.toString());
		Transaction transaction = transactionRepository.findOneByOrderid(tx.getOrderid());
		Context context = new Context();
		context.setVariable("order", transaction.getOrderid());
		context.setVariable("email", transaction.getEmail());
		context.setVariable("hash", "https://" + Globals.CAN_NETWORK + "etherscan.io/tx/" + transaction.getHash());
		context.setVariable("can",
				"https://" + Globals.CAN_NETWORK + "etherscan.io/tx/" + transaction.getHashethertoaccount());
		context.setVariable("amount", transaction.getAmount() + " CAN");
		context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
		String content = templateEngine.process("mail/transactionConfirm", context);
		sendEmail(transaction.getEmail(), "Transaction Successful", content, false, true);
		log.debug("Called sendMail");
	}

	/**
	 * POST /saveTransaction : register the user.
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	@PostMapping("/sendStagingMail")
	@Timed
	@ResponseStatus(HttpStatus.CREATED)
	public void sendStagingMail(@RequestBody Transaction tx) {

		Transaction transaction = transactionRepository.findOneByOrderid(tx.getOrderid());

		Context context = new Context();
		context.setVariable("order", transaction.getOrderid());
		context.setVariable("email", transaction.getEmail());
		context.setVariable("hash", transaction.getHash());
		context.setVariable("can", "PENDING");
		context.setVariable("amount", transaction.getAmount() + " CAN");
		context.setVariable("url", "https://" + Globals.CAN_NETWORK + "io/tx/" + transaction.getAddress());
		context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
		String content = templateEngine.process("mail/transactionConfirm", context);
		sendEmail(transaction.getEmail(), "Transaction Successful", content, false, true);
		log.debug("Called sendStagingMail");
	}

	@Async
	public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {

		// Prepare message using a Spring helper
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
			message.setTo(to);
			message.setFrom(jHipsterProperties.getMail().getFrom());
			message.setSubject(subject);
			message.setText(content, isHtml);
			javaMailSender.send(mimeMessage);
			log.debug("Sent email to User '{}'", to);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.warn("Email could not be sent to user '{}'", to, e);
			} else {
				log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
			}
		}
	}

	@GetMapping("/getunique")
	@Timed
	public ResponseEntity<Token> getunique() throws URISyntaxException, ClientProtocolException, IOException {

		String key = RandomStringUtils.random(8, 0, 20, true, true, "bj81G5RDED3DC6142kasok".toCharArray()).toString();
		log.debug("Called getunique {}", key);
		Token token = new Token();

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet("https://rinkeby.etherscan.io/");
		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		if (StringUtils.contains(result.toString(), "Notice: The indexer is temporarily down for maintenance")) {
			token.setStatus(false);
		} else {
			token.setStatus(true);
		}

		token.setToken(key);
		request.releaseConnection();
		return ResponseEntity.created(new URI("/api/getunique")).body(token);
	}

	@GetMapping("/staging/{id}")
	@Timed
	public ResponseEntity<Transaction> getAllUsers(@PathVariable String id) {
		System.out.println(id);
		try {
			Transaction transaction = transactionRepository.findOneByOrderid(id);
			System.out.println(transaction.getStatus());
			return new ResponseEntity<Transaction>(transaction, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			return null;
		}

	}

	@GetMapping("/order/{orderid}")
	@Timed
	public Transaction order(@PathVariable String orderid) {

		try {
			Transaction transaction = transactionRepository.findOneByOrderid(orderid);
			return transaction;

		} catch (Exception e) {
			return null;
		}

	}

	@PostMapping("/staging")
	@Async
	public void sendStagingActivity(@RequestBody TransactionDTO txData) {

		log.debug("Called staging {}", txData);
		System.out.println("Transferring");

		txData.setStatus(Status.AUTHENTICATED.name());

		Web3j web3 = Web3j.build(new HttpService(Globals.CAN_INFURA));
		Transaction txDatac = transactionRepository.findOneByOrderid(txData.getKey());

		Credentials credentials;
		try {
			credentials = WalletUtils.loadCredentials(Globals.CAN_PASSWORD, Globals.CAN_KEY);
			BigInteger gasprice = web3.ethGasPrice().send().getGasPrice();
			System.out.println("Gas Price " + gasprice);
			log.debug("Gas Price {}", gasprice);
			BigInteger gaslimit = BigInteger.valueOf(90000);
			BigDecimal value = new BigDecimal(txData.getAmount());
			CanYaCoin contract = CanYaCoin.load(Globals.CAN_CONTRACT, web3, credentials, gasprice, gaslimit);

			BigDecimal valueToMultiply = new BigDecimal("1000000");

			BigDecimal s = value.multiply(valueToMultiply);

			System.out.println("address is " + txDatac.getAddress());
			TransactionReceipt transactionReceipt = contract.transfer(txDatac.getAddress(), s.toBigInteger()).send();
			System.out.println("hash " + transactionReceipt.getTransactionHash());
			System.out.println("Success");
			txDatac.setStatus(Status.TRANSFERRED.name());
			txDatac.setHashethertoaccount(transactionReceipt.getTransactionHash());
			log.debug("CAN Sent {}", txDatac);
			transactionRepository.save(txDatac);

		} catch (Exception e) {
			txDatac.setStatus(Status.ERROR.name());
			transactionRepository.save(txDatac);
			e.printStackTrace();
		}

		if (StringUtils.equals(txDatac.getStatus(), Status.TRANSFERRED.name())) {

			String url = "http://api" + Globals.CAN_NETWORK + "etherscan.io/api?module=account&action=tokentx&address="
					+ txData.getAddress() + "&startblock=0&endblock=999999999&sort=asc&apikey=" + key;

			Timer timer = new Timer();
			long Seconds = 10;
			timer.schedule(new TimerTask() {

				int counter = 0;

				@Override
				public void run() {
					try {

						if (counter == 360) {
							Transaction txData = transactionRepository.findOneByOrderid(txDatac.getOrderid());
							txData.setStatus(Status.TIMEOUT.name());
							transactionRepository.save(txData);
							timer.cancel();
						}
						counter++;
						HttpClient client = HttpClientBuilder.create().build();
						HttpGet request = new HttpGet(url);
						HttpResponse response = client.execute(request);
						System.out.println(url);
						System.out.println("Response Code 1 : " + response.getStatusLine().getStatusCode());

						BufferedReader rd = new BufferedReader(
								new InputStreamReader(response.getEntity().getContent()));

						StringBuffer result = new StringBuffer();
						String line = "";
						while ((line = rd.readLine()) != null) {
							result.append(line);
						}
						rd.close();
						request.abort();

						ObjectMapper jsonParserClient = new ObjectMapper();

						Etherscan etherscan = jsonParserClient.readValue(result.toString(), Etherscan.class);

						for (Result ethplorer : etherscan.getResult()) {
							if (StringUtils.equals(txDatac.getHashethertoaccount(), ethplorer.getHash())) {
								System.out.println("DONE " + txDatac.getHashethertoaccount());
								log.debug("CAN Confirmed {}", txDatac);
								txDatac.setStatus(Status.COMPLETE.name());
								txDatac.setHash(ethplorer.getHash());
								transactionRepository.save(txDatac);

								timer.cancel();
								break;
							}

						}
						request.releaseConnection();
					} catch (Exception e) {
						txDatac.setStatus(Status.ERROR.name());
						transactionRepository.save(txDatac);
						e.printStackTrace();
					}

				}
			}, 0, 1000 * Seconds);
		}

	}

	/**
	 * POST /saveTransaction : register the user.
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	@GetMapping("test")
	@Timed
	@ResponseStatus(HttpStatus.CREATED)
	public void saveTransaction() throws ClientProtocolException, IOException {

		String test = "xx001";
		// mailService.sendEmailFromTemplate("my3d3d@gmail.com", test,
		// "mail/transactionConfirm");

	}

}
