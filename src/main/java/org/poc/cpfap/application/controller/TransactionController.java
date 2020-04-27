package org.poc.cpfap.application.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.poc.cpfap.application.model.TransactionDetail;
import org.poc.cpfap.application.model.TransactionRequest;
import org.poc.cpfap.application.model.TransactionStatus;
import org.poc.cpfap.application.repository.TransactionDetailRepository;
import org.poc.cpfap.application.repository.TransactionRequestRepository;
import org.poc.cpfap.application.request.TransactionDetailsJSON;
import org.poc.cpfap.application.request.TransactionDtl;
import org.poc.cpfap.application.request.TransactionJSONPostRequest;
import org.poc.cpfap.application.request.TransactionStatusJSONRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/v1")
public class TransactionController {

	@Autowired
	RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    @Autowired
    TransactionRequestRepository transactionRequestRepository;
    @Autowired
    TransactionDetailRepository transactionDetailRepository;
    @Value("${account_api_uri}")
	private String accountUri;
    @GetMapping("/transaction")
    public ResponseEntity<TransactionDetailsJSON> getTransactionDetails(@RequestParam long accountNum) {
    	List<TransactionDetail> tdList = transactionDetailRepository.findByAccountNumberOrderByIdDesc(accountNum);
//                .orElseThrow(() -> new ResourceNotFoundException("Transaction Detail", "account_no", accountNum));
    	TransactionDetailsJSON json = new TransactionDetailsJSON();
    	json.setAccountNumber(Long.toString(accountNum));
    	List<TransactionDtl> list = new ArrayList<>();
    	for(TransactionDetail td:tdList) {
    		TransactionDtl tdtl = new TransactionDtl();
    		tdtl.setAmount(String.valueOf(td.getAmount()));
    		tdtl.setStatus(td.getStatus());
    		if(td.getTransactionDateTime()!=null){
    			DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        		tdtl.setTransactionDateTime(dateFormat.format(td.getTransactionDateTime()));
    		}
    		tdtl.setTransactionDesc(td.getDescription());
    		tdtl.setTransactionType(td.getTransactionType());
    		tdtl.setTargetAccount(null);
    		tdtl.setTransactionId(td.getTransactionId());
    		list.add(tdtl);
    	}
    	json.setTransactions(list);
    	return new ResponseEntity<TransactionDetailsJSON>(json, HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public ResponseEntity<String> addTransaction(@Valid @RequestBody TransactionJSONPostRequest transactionJSONPostRequest,HttpServletRequest request) {
    	long accountBalance = Long.parseLong(transactionJSONPostRequest.getTransaction().get(0).getAccountBalance());
    	long amount = transactionJSONPostRequest.getTransaction().get(0).getAmount();
    	logger.info("accountBalance"+accountBalance);;
    	if (accountBalance < amount) {
    		return new ResponseEntity<>("Failed. Insufficient Account Balance.", HttpStatus.NOT_ACCEPTABLE);
    	}
    	TransactionRequest tr = new TransactionRequest();
    	tr.setAmount(transactionJSONPostRequest.getTransaction().get(0).getAmount());
    	tr.setDescription(transactionJSONPostRequest.getTransaction().get(0).getTransactionDesc());
    	tr.setSourceAccountNumber(transactionJSONPostRequest.getAccountNumber());
    	tr.setStatus(TransactionStatus.PENDING.name());
    	tr.setTargetAccountNumber(transactionJSONPostRequest.getTransaction().get(0).getTargetAccount());
    	tr.setTransactionType(transactionJSONPostRequest.getTransaction().get(0).getTransactionType());
    	transactionRequestRepository.save(tr);

    	TransactionDetail td = new TransactionDetail();
    	td.setCategory("debit");
    	td.setAccountNumber(transactionJSONPostRequest.getAccountNumber());
    	td.setAmount(transactionJSONPostRequest.getTransaction().get(0).getAmount());
    	td.setStatus(TransactionStatus.PENDING.name());
    	try {
			td.setTransactionDateTime(new SimpleDateFormat("yyyy-MM-dd").parse(transactionJSONPostRequest.getTransaction().get(0).getTransactionDateTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("error while setting tr date",e);;
		}
    	String transactionID = UUID.randomUUID().toString();
    	td.setTransactionId(transactionID);
    	td.setDescription(transactionJSONPostRequest.getTransaction().get(0).getTransactionDesc());
    	td.setAvailableBalance(accountBalance - amount);
    	td.setTransactionType(transactionJSONPostRequest.getTransaction().get(0).getTransactionType());
    	transactionDetailRepository.save(td);

    	String url = "http://dnb.eu-gb.mybluemix.net/api/v1/accounts";
    	List<TransactionDetail> tdlist = transactionDetailRepository.findByAccountNumberOrderByIdDesc(transactionJSONPostRequest.getAccountNumber());
//    			.orElseThrow(() -> new ResourceNotFoundException("Transaction Detail", "account_no", transactionJSONPostRequest.getAccountNumber()));
    	Map<String, Object> jo = new HashMap<>();
    	jo.put("accountNumber", transactionJSONPostRequest.getAccountNumber());
    	jo.put("balance", tdlist.get(tdlist.size()-1).getAvailableBalance());
    	jo.put("transactionId", transactionID);
    	jo.put("txdomain", request.getHeader("HOST"));
    	logger.info("accountUri:"+accountUri);
    	logger.info("txdomain:"+request.getHeader("HOST"));
    	restTemplate.put(accountUri, jo);
    	return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PutMapping("/transaction")
    public ResponseEntity<String> updateTransactionStatus(@Valid @RequestBody TransactionStatusJSONRequest transactionStatusJSONRequest) {
    	TransactionDetail tdl = transactionDetailRepository.findByTransactionIdOrderByIdDesc(transactionStatusJSONRequest.getTransactionId());
//    			.orElseThrow(() -> new ResourceNotFoundException("Transaction Detail", "account_no", transactionStatusJSONRequest.getAccountNumber()));
    	//tdl.setStatus(transactionStatusJSONRequest.getStatus());
    	tdl.setStatus(TransactionStatus.APPROVED.name());
    	transactionDetailRepository.save(tdl);
    	return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @GetMapping("/transaction/liveness")
    public String liveness() {
        return "{\"status\":\"UP\"}";
    }

}
