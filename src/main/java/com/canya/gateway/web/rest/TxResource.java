package com.canya.gateway.web.rest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
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
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
import com.canya.gateway.repository.ConfigRepository;
import com.canya.gateway.repository.TransactionRepository;
import com.canya.gateway.service.MailService;
import com.canya.gateway.service.TransactionService;
import com.canya.gateway.service.dto.CanYaCoin;
import com.canya.gateway.service.dto.Etherscan;
import com.canya.gateway.service.dto.Result;
import com.canya.gateway.service.dto.Token;
import com.canya.gateway.service.dto.TransactionDTO;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import io.github.jhipster.config.JHipsterProperties;

/**
 * REST controller for managing the transaction
 */
@RestController
@RequestMapping("/api")
public class TxResource {

	private final Logger log = LoggerFactory.getLogger(TxResource.class);

	private final TransactionRepository transactionRepository;

	private final TransactionService transactionService;

	private final MailService mailService;

	private static final String BASE_URL = "baseUrl";

	private final JHipsterProperties jHipsterProperties;

	private final JavaMailSender javaMailSender;

	private final MessageSource messageSource;

	private final SpringTemplateEngine templateEngine;

	private final ConfigRepository configRepository;

	private static String key = "T4RFNMPATQFVFE7NSNYCXF2R89CWM4HGBK";

	private static String CAN_NETWORK = null;

	private static String ETHER_ADDRESS = null;

	private static String CAN_INFURA = null;

	private static String CAN_PASSWORD = null;

	private static String CAN_CONTRACT = null;
	private static String PROJECT_ID = null;
	private static String BUCKET_NAME = null;
	private static String OBJECT_NAME = null;
	private static String GCLOUD_KEY = null;
	private static String SENDGRID_USERNAME = null;
	private static String SENDGRID_PASSWORD = null;
	Credentials credentials = null;
	private static InputStream inputStream = null;

	public TxResource(MailService mailService, TransactionRepository transactionRepository,
			JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender, MessageSource messageSource,
			SpringTemplateEngine templateEngine, TransactionService transactionService,
			ConfigRepository configRepository) {

		this.mailService = mailService;
		this.transactionRepository = transactionRepository;
		this.jHipsterProperties = jHipsterProperties;
		this.javaMailSender = javaMailSender;
		this.messageSource = messageSource;
		this.templateEngine = templateEngine;
		this.transactionService = transactionService;
		this.configRepository = configRepository;

		PROJECT_ID = configRepository.findOneByKey("PROJECT_ID").getValue();
		CAN_NETWORK = configRepository.findOneByKey("CAN_NETWORK").getValue();
		ETHER_ADDRESS = configRepository.findOneByKey("ETHER_ADDRESS").getValue();
		CAN_INFURA = configRepository.findOneByKey("CAN_INFURA").getValue();
		CAN_CONTRACT = configRepository.findOneByKey("CAN_CONTRACT").getValue();
		BUCKET_NAME = configRepository.findOneByKey("BUCKET_NAME").getValue();
		OBJECT_NAME = configRepository.findOneByKey("OBJECT_NAME").getValue();
		CAN_PASSWORD = configRepository.findOneByKey("CAN_PASSWORD").getValue();
		GCLOUD_KEY = configRepository.findOneByKey("GCLOUD_KEY").getValue();
		SENDGRID_USERNAME = configRepository.findOneByKey("SENDGRID_USERNAME").getValue();
		SENDGRID_PASSWORD = configRepository.findOneByKey("SENDGRID_PASSWORD").getValue();

		try {
			InputStream stream = new ByteArrayInputStream(GCLOUD_KEY.getBytes(StandardCharsets.UTF_8));

			StorageOptions options = StorageOptions.newBuilder().setProjectId(PROJECT_ID)
					.setCredentials(GoogleCredentials.fromStream(stream)).build();

			Storage storage = options.getService();
			Blob blob = storage.get(BUCKET_NAME, OBJECT_NAME);
			ReadChannel r = blob.reader();
			inputStream = Channels.newInputStream(r);
			credentials = WalletUtils.loadCredentials(CAN_PASSWORD, stream2file(inputStream));
		} catch (Exception e) {
		}
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

		log.debug("Tx Registered {}", tx);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		long Seconds = 10;

		Timer timer = new Timer();

		timer.schedule(new TimerTask() {
			int counter = 0;

			@Override
			public void run() {
				try {
					String comparedValue = null;
					String url = "";
					if (StringUtils.equals(tx.getCurrency(), "ETH")) {
						transaction.setStatus(Status.CONNECTED.name());
						url = "http://api" + CAN_NETWORK + "etherscan.io/api?module=account&action=txlist&address="
								+ ETHER_ADDRESS + "&startblock=0&endblock=999999999&sort=asc&apikey=" + key;
						comparedValue = tx.getEth();
					} else {

						url = "http://api" + CAN_NETWORK + "etherscan.io/api?module=account&action=tokentx&address="
								+ ETHER_ADDRESS + "&startblock=0&endblock=999999999&sort=asc&apikey=" + key;
						comparedValue = tx.getEth();
					}

					System.out.println(url);
					Transaction txDatac = transactionRepository.findOneByOrderid(transaction.getKey());

					if (StringUtils.equals(txDatac.getStatus(), Status.INITIATED.name())) {
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
						Transaction txData = transactionRepository.findOneByOrderid(tx.getOrderid());
						for (Result ethplorer : etherscan.getResult()) {
							Date longdate = new java.util.Date(Long.parseLong(ethplorer.getTimeStamp()) * 1000);
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String dateString = formatter.format(longdate);
							String value = String.format("%.6f",
									Double.parseDouble(ethplorer.getValue()) / 1000000000000000000l);

							System.out.println("Double ---- " + comparedValue + " equals to " + value);
							if (StringUtils.equals(compareDates(dateString, dateFormat.format(date)), "after")
									&& StringUtils.contains(value, comparedValue)) {
								log.debug("Tx Processed {}", tx.getOrderid());
								System.out.println("Tx Processed {}");
								txData.setStatus(Status.IDENTIFIED.name());
								txData.setHash(ethplorer.getHash());
								txData.setDate(dateString);
								transactionRepository.save(txData);

								break;
							}
						}

						request.releaseConnection();
					}

					txDatac = transactionRepository.findOneByOrderid(transaction.getKey());

					if (StringUtils.equals(txDatac.getStatus(), Status.IDENTIFIED.name())) {
						log.debug("Called staging {}", txDatac);
						System.out.println("Transferring");

						txDatac.setStatus(Status.AUTHENTICATED.name());

						Web3j web3 = Web3j.build(new HttpService(CAN_INFURA));

						try {

							BigInteger gasprice = web3.ethGasPrice().send().getGasPrice();
							System.out.println("Gas Price " + gasprice);
							log.debug("Gas Price {}", gasprice);
							BigInteger gaslimit = BigInteger.valueOf(90000);
							BigDecimal value = new BigDecimal(txDatac.getAmount());
							CanYaCoin contract = CanYaCoin.load(CAN_CONTRACT, web3, credentials, gasprice, gaslimit);

							BigDecimal valueToMultiply = new BigDecimal("1000000");

							BigDecimal s = value.multiply(valueToMultiply);

							System.out.println("Address is " + txDatac.getAddress());
							TransactionReceipt transactionReceipt = contract
									.transfer(txDatac.getAddress(), s.toBigInteger()).send();
							System.out.println("HASH " + transactionReceipt.getTransactionHash());
							System.out.println("SUCCESS");
							txDatac.setStatus(Status.COMPLETE.name());
							txDatac.setHashethertoaccount(transactionReceipt.getTransactionHash());
							log.debug("CAN Sent {}", txDatac);
							transactionRepository.save(txDatac);
							timer.cancel();
						} catch (Exception e) {
							timer.cancel();
							txDatac.setStatus(Status.ERROR.name());
							transactionRepository.save(txDatac);
							e.printStackTrace();
						}
					}

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
	public void sentMail(@RequestBody String orderId) {
		System.out.println("Email send for Id " + orderId);
		Transaction transaction = transactionRepository.findOneByOrderid(orderId);
		Context context = new Context();
		context.setVariable("order", transaction.getOrderid());
		context.setVariable("email", transaction.getEmail());
		context.setVariable("hash", "https://" + CAN_NETWORK + "etherscan.io/tx/" + transaction.getHash());
		context.setVariable("can", "https://" + CAN_NETWORK + "etherscan.io/tx/" + transaction.getHashethertoaccount());
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
	public void sendStagingMail(@RequestBody String orderId) {
		System.out.println("Email send for Id " + orderId);
		Transaction transaction = transactionRepository.findOneByOrderid(orderId);
		Context context = new Context();
		context.setVariable("order", transaction.getOrderid());
		context.setVariable("email", transaction.getEmail());
		context.setVariable("hash", transaction.getHash());
		context.setVariable("can", "PENDING");
		context.setVariable("amount", transaction.getAmount() + " CAN");
		context.setVariable("url", "https://" + CAN_NETWORK + "io/tx/" + transaction.getAddress());
		context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
		String content = templateEngine.process("mail/transactionConfirm", context);
		sendEmail(transaction.getEmail(), "Transaction Successful", content, false, true);
		log.debug("Called sendStagingMail");
	}

	@Async
	public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {

		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setUsername(SENDGRID_USERNAME);
		javaMailSender.setPassword(SENDGRID_PASSWORD);

		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.host", "smtp.sendgrid.net");
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.ssl.trust", "smtp.sendgrid.net");
		javaMailSender.setJavaMailProperties(properties);

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
		try {
			Transaction transaction = transactionRepository.findOneByOrderid(id);
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

	public static File stream2file(InputStream in) throws IOException {
		final File tempFile = File.createTempFile("src/main/resources/stream2file", ".tmp");
		tempFile.deleteOnExit();
		try (FileOutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
		}
		return tempFile;
	}

	@PostMapping("/staging")
	@Async
	public void sendStagingActivity(@RequestBody TransactionDTO txData) {

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

		try {
			Context context = new Context();
			context.setVariable("order", "fsdf");
			context.setVariable("email", "fsdf");
			context.setVariable("hash", "https://" + CAN_NETWORK + "etherscan.io/tx/");
			context.setVariable("can", "https://" + CAN_NETWORK + "etherscan.io/tx/");
			context.setVariable("amount", "CAN");
			context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
			String content = templateEngine.process("mail/transactionConfirm", context);

			sendEmail("my3d3d@gmail.com", "Transaction Successful", content, false, true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
