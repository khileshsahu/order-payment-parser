package order.payment.parser.printer;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import order.payment.parser.model.Constants;
import order.payment.parser.model.OrderDetails;

@Service
public class StandardPrinter implements OrderDetailsPrinter{

	@SuppressWarnings("unchecked")
	@Override
	public void print(List<OrderDetails> orderDetailsList) {
		for(OrderDetails orderDetails : orderDetailsList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(Constants.ORDER_ID, orderDetails.getOrderId());
			jsonObject.put(Constants.AMOUNT, orderDetails.getAmount());
			jsonObject.put(Constants.CURRENCY, orderDetails.getCurrency());
			jsonObject.put(Constants.COMMENT, orderDetails.getComment());
			jsonObject.put(Constants.FILE_NAME, orderDetails.getFileName());
			jsonObject.put(Constants.LINE, orderDetails.getLine());
			jsonObject.put(Constants.RESULT, orderDetails.getResult());
			
			System.out.println(jsonObject.toJSONString());
			System.out.println(jsonObject.toString());
		}
	}

}
