package order.payment.parser.parse;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import order.payment.parser.exception.ParsingException;
import order.payment.parser.model.Constants;
import order.payment.parser.model.OrderDetails;
import order.payment.parser.validator.OrderValidator;

@Component
@RequiredArgsConstructor
public class JsonParser implements Parser {
	
	private static final String JSON = "json";
	private final OrderValidator orderValidator;

	@Override
	public List<OrderDetails> parse(String filePath) {
		File file = new File(filePath);
		List<OrderDetails> orderDetailsList = new ArrayList<>();
		JSONArray jsonArr = getJsonArr(file);
		
		if(jsonArr != null) {
			for(int i=0; i<jsonArr.size(); i++) {
				JSONObject jsonObj = (JSONObject) jsonArr.get(i);
				OrderDetails orderDetails = fetchOrderDetails(jsonObj, file.getName(), i+1);
				orderDetailsList.add(orderDetails);
			}
		}
		
		return orderDetailsList;
	}
	
	@Override
	public String getType() {
		return JSON;
	}

	private OrderDetails fetchOrderDetails(JSONObject jsonObj, String fileName, int index) {
		Integer orderId = null;
		Double amount = null;
		String classCastErrorMsg = null;
		try {
			orderId = ((Long) jsonObj.get(Constants.ORDER_ID)).intValue();
		} catch(ClassCastException e) {
			classCastErrorMsg = e.getMessage();
		}
		
		try {
			amount = (Double) jsonObj.get(Constants.AMOUNT);
		} catch(ClassCastException e) {
			classCastErrorMsg = e.getMessage();
		}

		String currency = (String) jsonObj.get(Constants.CURRENCY);
		String comment = (String) jsonObj.get(Constants.COMMENT);
		
		String result = findResult(orderId, amount, classCastErrorMsg, currency, comment);
		
		return OrderDetails.builder()
				.orderId(orderId)
				.amount(amount)
				.currency(currency)
				.comment(comment)
				.fileName(fileName)
				.line(index)
				.result(result)
				.build();
	}

	private String findResult(Integer orderId, Double amount, String classCastErrorMsg, String currency,
			String comment) {
		String result = "OK";
		if(classCastErrorMsg != null) {
			result = classCastErrorMsg;
		}
		else {
			String validationResult = orderValidator.validate(orderId, amount, currency, comment);
			result = validationResult == null ? result : validationResult;
		}
		return result;
	}

	private JSONArray getJsonArr(File file) {
		JSONParser parserObj = new JSONParser();
		JSONArray jsonArr = null;
		try {
			jsonArr = (JSONArray) parserObj.parse(new FileReader(file));
		} catch (IOException | ParseException e) {
			throw new ParsingException(e.getMessage(), e);
		}
		return jsonArr;
	}

}
