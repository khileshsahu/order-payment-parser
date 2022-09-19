package order.payment.parser.model;

import com.opencsv.bean.CsvBindByName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsvOrderDetails {
	@CsvBindByName(column = "Order ID")
	private String orderId;

	@CsvBindByName(column = "amount")
	private String amount;

	@CsvBindByName(column = "currency")
	private String currency;

	@CsvBindByName(column = "comment")
	private String comment;

}