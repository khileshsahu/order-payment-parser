package order.payment.parser.model;

import org.springframework.stereotype.Component;

@Component
public class OrderValidator {
	
	public String validate(Integer orderId, Double amount, String currency, String comment) {
		String errorMessage = validateMissingFields(orderId, amount, currency, comment);
		
		if(errorMessage != null) 
			return errorMessage;
		
		return validateEmptyFields(orderId, amount, currency, comment);
	}

	private String validateMissingFields(Integer orderId, Double amount, String currency, String comment) {
		if(orderId == null || amount == null || currency == null || comment == null) {
			return addMissingMessage(orderId, amount, currency, comment);
		}
		
		return null;
	}

	private String addMissingMessage(Integer orderId, Double amount, String currency, String comment) {
		return "Missing required fields : " 
				+ (orderId == null ? "orderId" : "")
				+ (amount == null ? "amount" : "")
				+ (currency == null ? "currency" : "")
				+ (comment == null ? "comment" : "");
	}
	
	private String validateEmptyFields(Integer orderId, Double amount, String currency, String comment) {
		if(orderId == 0 || amount == 0 || currency.isEmpty() || comment.isEmpty()) {
			return addEmptyMessage(orderId, amount, currency, comment);
		}
		
		return null;
	}
	
	private String addEmptyMessage(Integer orderId, Double amount, String currency, String comment) {
		return "These fields can not be empty : "
				+ (orderId == 0 ? "orderId" : "")
				+ (amount == 0 ? "amount" : "")
				+ (currency.isEmpty() ? "currency" : "")
				+ (comment.isEmpty() ? "comment" : "");
	}
}
