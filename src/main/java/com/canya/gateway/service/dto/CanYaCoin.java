package com.canya.gateway.service.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>
 * Auto generated code.
 * <p>
 * <strong>Do not modify!</strong>
 * <p>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j
 * command line tools</a>, or the
 * org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen
 * module</a> to update.
 *
 * <p>
 * Generated with web3j version 3.5.0.
 */
public class CanYaCoin extends Contract {
	private static final String BINARY = "608060405234801561001057600080fd5b50336000908152602081905260409020655af3107a400090556105be806100386000396000f3006080604052600436106100b95763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde0381146100be578063095ea7b31461014857806318160ddd1461018057806323b872dd146101a757806327e235e3146101d1578063313ce567146101f25780635c6581651461020757806370a082311461022e5780637e1c0c091461024f57806395d89b4114610264578063a9059cbb14610279578063dd62ed3e1461029d575b600080fd5b3480156100ca57600080fd5b506100d36102c4565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561010d5781810151838201526020016100f5565b50505050905090810190601f16801561013a5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561015457600080fd5b5061016c600160a060020a03600435166024356102fb565b604080519115158252519081900360200190f35b34801561018c57600080fd5b50610195610362565b60408051918252519081900360200190f35b3480156101b357600080fd5b5061016c600160a060020a036004358116906024351660443561036c565b3480156101dd57600080fd5b50610195600160a060020a036004351661044d565b3480156101fe57600080fd5b5061019561045f565b34801561021357600080fd5b50610195600160a060020a0360043581169060243516610464565b34801561023a57600080fd5b50610195600160a060020a0360043516610481565b34801561025b57600080fd5b5061019561049c565b34801561027057600080fd5b506100d36104a6565b34801561028557600080fd5b5061016c600160a060020a03600435166024356104dd565b3480156102a957600080fd5b50610195600160a060020a0360043581169060243516610567565b60408051808201909152600981527f43616e5961436f696e0000000000000000000000000000000000000000000000602082015281565b336000818152600160209081526040808320600160a060020a038716808552908352818420869055815186815291519394909390927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925928290030190a35060015b92915050565b655af3107a400090565b600160a060020a03831660009081526020819052604081205482118015906103b75750600160a060020a03841660009081526001602090815260408083203384529091529020548211155b1561044257600160a060020a038085166000818152602081815260408083208054889003905560018252808320338452825280832080548890039055938716808352828252918490208054870190558351868152935191937fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929081900390910190a3506001610446565b5060005b9392505050565b60006020819052908152604090205481565b600681565b600160209081526000928352604080842090915290825290205481565b600160a060020a031660009081526020819052604090205490565b655af3107a400081565b60408051808201909152600381527f43414e0000000000000000000000000000000000000000000000000000000000602082015281565b33600090815260208190526040812054821161055e573360008181526020818152604080832080548790039055600160a060020a03871680845292819020805487019055805186815290519293927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929181900390910190a350600161035c565b50600092915050565b600160a060020a039182166000908152600160209081526040808320939094168252919091522054905600a165627a7a723058202e11e3e0ae56a52bcce7dd1c17a0f4baca88d3ef5656edb2a7310c93eb4591bf0029";

	public static final String FUNC_NAME = "name";

	public static final String FUNC_APPROVE = "approve";

	public static final String FUNC_TOTALSUPPLY = "totalSupply";

	public static final String FUNC_TRANSFERFROM = "transferFrom";

	public static final String FUNC_BALANCES = "balances";

	public static final String FUNC_DECIMALS = "decimals";

	public static final String FUNC_ALLOWED = "allowed";

	public static final String FUNC_BALANCEOF = "balanceOf";

	public static final String FUNC_TOTALTOKENS = "totalTokens";

	public static final String FUNC_SYMBOL = "symbol";

	public static final String FUNC_TRANSFER = "transfer";

	public static final String FUNC_ALLOWANCE = "allowance";

	public static final Event TRANSFER_EVENT = new Event("Transfer",
			Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
			}, new TypeReference<Address>(true) {
			}, new TypeReference<Uint256>() {
			}));;

	public static final Event APPROVAL_EVENT = new Event("Approval",
			Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
			}, new TypeReference<Address>(true) {
			}, new TypeReference<Uint256>() {
			}));;

	protected CanYaCoin(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
			BigInteger gasLimit) {
		super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
	}

	protected CanYaCoin(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice,
			BigInteger gasLimit) {
		super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
	}

	public RemoteCall<String> name() {
		final Function function = new Function(FUNC_NAME, Arrays.<Type>asList(),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
				}));
		return executeRemoteCallSingleValueReturn(function, String.class);
	}

	public RemoteCall<TransactionReceipt> approve(String _spender, BigInteger _value) {
		final Function function = new Function(FUNC_APPROVE,
				Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_spender),
						new org.web3j.abi.datatypes.generated.Uint256(_value)),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteCall<BigInteger> totalSupply() {
		final Function function = new Function(FUNC_TOTALSUPPLY, Arrays.<Type>asList(),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeRemoteCallSingleValueReturn(function, BigInteger.class);
	}

	public RemoteCall<TransactionReceipt> transferFrom(String _from, String _to, BigInteger _value) {
		final Function function = new Function(FUNC_TRANSFERFROM,
				Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_from),
						new org.web3j.abi.datatypes.Address(_to),
						new org.web3j.abi.datatypes.generated.Uint256(_value)),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteCall<BigInteger> balances(String param0) {
		final Function function = new Function(FUNC_BALANCES,
				Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeRemoteCallSingleValueReturn(function, BigInteger.class);
	}

	public RemoteCall<BigInteger> decimals() {
		final Function function = new Function(FUNC_DECIMALS, Arrays.<Type>asList(),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeRemoteCallSingleValueReturn(function, BigInteger.class);
	}

	public RemoteCall<BigInteger> allowed(String param0, String param1) {
		final Function function = new Function(FUNC_ALLOWED,
				Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0),
						new org.web3j.abi.datatypes.Address(param1)),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeRemoteCallSingleValueReturn(function, BigInteger.class);
	}

	public RemoteCall<BigInteger> balanceOf(String _owner) {
		final Function function = new Function(FUNC_BALANCEOF,
				Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner)),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeRemoteCallSingleValueReturn(function, BigInteger.class);
	}

	public RemoteCall<BigInteger> totalTokens() {
		final Function function = new Function(FUNC_TOTALTOKENS, Arrays.<Type>asList(),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeRemoteCallSingleValueReturn(function, BigInteger.class);
	}

	public RemoteCall<String> symbol() {
		final Function function = new Function(FUNC_SYMBOL, Arrays.<Type>asList(),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
				}));
		return executeRemoteCallSingleValueReturn(function, String.class);
	}

	public RemoteCall<TransactionReceipt> transfer(String _to, BigInteger _value) {
		final Function function = new Function(FUNC_TRANSFER,
				Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to),
						new org.web3j.abi.datatypes.generated.Uint256(_value)),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteCall<BigInteger> allowance(String _owner, String _spender) {
		final Function function = new Function(FUNC_ALLOWANCE,
				Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner),
						new org.web3j.abi.datatypes.Address(_spender)),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeRemoteCallSingleValueReturn(function, BigInteger.class);
	}

	public static RemoteCall<CanYaCoin> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
			BigInteger gasLimit) {
		return deployRemoteCall(CanYaCoin.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
	}

	public static RemoteCall<CanYaCoin> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice,
			BigInteger gasLimit) {
		return deployRemoteCall(CanYaCoin.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
	}

	public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
		List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
		ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			TransferEventResponse typedResponse = new TransferEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
			typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
			typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
			responses.add(typedResponse);
		}
		return responses;
	}

	public Observable<TransferEventResponse> transferEventObservable(EthFilter filter) {
		return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
			@Override
			public TransferEventResponse call(Log log) {
				Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
				TransferEventResponse typedResponse = new TransferEventResponse();
				typedResponse.log = log;
				typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
				typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
				typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
				return typedResponse;
			}
		});
	}

	public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock,
			DefaultBlockParameter endBlock) {
		EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
		return transferEventObservable(filter);
	}

	public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
		List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
		ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			ApprovalEventResponse typedResponse = new ApprovalEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
			typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
			typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
			responses.add(typedResponse);
		}
		return responses;
	}

	public Observable<ApprovalEventResponse> approvalEventObservable(EthFilter filter) {
		return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalEventResponse>() {
			@Override
			public ApprovalEventResponse call(Log log) {
				Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
				ApprovalEventResponse typedResponse = new ApprovalEventResponse();
				typedResponse.log = log;
				typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
				typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
				typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
				return typedResponse;
			}
		});
	}

	public Observable<ApprovalEventResponse> approvalEventObservable(DefaultBlockParameter startBlock,
			DefaultBlockParameter endBlock) {
		EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
		return approvalEventObservable(filter);
	}

	public static CanYaCoin load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
			BigInteger gasLimit) {
		return new CanYaCoin(contractAddress, web3j, credentials, gasPrice, gasLimit);
	}

	public static CanYaCoin load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
			BigInteger gasPrice, BigInteger gasLimit) {
		return new CanYaCoin(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
	}

	public static class TransferEventResponse {
		public Log log;

		public String from;

		public String to;

		public BigInteger value;
	}

	public static class ApprovalEventResponse {
		public Log log;

		public String owner;

		public String spender;

		public BigInteger value;
	}
}
