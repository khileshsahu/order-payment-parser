package order.payment.parser.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class OrderDetails {
	
	private Integer orderId;
	private Double amount;
	private String currency;
	private String comment;
	private String fileName;
	private Integer line;
	private String result;
}
