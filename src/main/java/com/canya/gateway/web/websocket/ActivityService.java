package com.canya.gateway.web.websocket;

import static com.canya.gateway.config.WebsocketConfiguration.IP_ADDRESS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import com.canya.gateway.domain.Transaction;
import com.canya.gateway.repository.TransactionRepository;
import com.canya.gateway.service.dto.Etherscan;
import com.canya.gateway.service.dto.Result;
import com.canya.gateway.service.dto.TransactionDTO;
import com.canya.gateway.web.rest.util.Globals;
import com.canya.gateway.web.websocket.dto.ActivityDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ActivityService implements ApplicationListener<SessionDisconnectEvent> {

	private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

	private final TransactionRepository transactionRepository;

	private final SimpMessageSendingOperations messagingTemplate;

	private static String key = "T4RFNMPATQFVFE7NSNYCXF2R89CWM4HGBK";

	public ActivityService(SimpMessageSendingOperations messagingTemplate,
			TransactionRepository transactionRepository) {
		this.messagingTemplate = messagingTemplate;
		this.transactionRepository = transactionRepository;
	}

	@MessageMapping("/topic/activity")
	@SendTo("/topic/tracker")
	public ActivityDTO sendActivity(@Payload ActivityDTO activityDTO, StompHeaderAccessor stompHeaderAccessor,
			Principal principal) {
		activityDTO.setUserLogin(principal.getName());
		activityDTO.setSessionId(stompHeaderAccessor.getSessionId());
		activityDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
		activityDTO.setTime(Instant.now());
		log.debug("Sending user tracking data {}", activityDTO);
		return activityDTO;
	}

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		ActivityDTO activityDTO = new ActivityDTO();
		activityDTO.setSessionId(event.getSessionId());
		activityDTO.setPage("logout");
		messagingTemplate.convertAndSend("/topic/tracker", activityDTO);
	}

	@MessageMapping("/topic/transaction/{id}")
	public void sendActivity(@DestinationVariable("id") String id, @PathVariable String data,
			@Payload Transaction transaction, StompHeaderAccessor stompHeaderAccessor)
			throws UnsupportedOperationException, IOException {

	}

	public void sendether() throws InterruptedException, TransactionException, Exception {
		Web3j web3 = Web3j.build(new HttpService(Globals.CAN_INFURA));

		Credentials credentials = WalletUtils.loadCredentials("cessna123",
				"C:\\Users\\ABC\\Desktop\\UTC--2018-08-08T13-24-01.761Z--0bec79ce3bcfc041a677ba82ef02b9738b628d42");

		TransactionReceipt transactionReceipt = Transfer.sendFunds(web3, credentials,
				"0x0BEc79CE3BcFC041A677Ba82EF02B9738B628d42", BigDecimal.valueOf(0.001), Convert.Unit.ETHER).send();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(formatter);

		System.out.println("success");
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

}
