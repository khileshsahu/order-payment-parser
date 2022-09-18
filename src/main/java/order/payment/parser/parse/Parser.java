package order.payment.parser.parse;

import java.util.List;

import order.payment.parser.model.OrderDetails;

public interface Parser {
	public String getType();
	public List<OrderDetails> parse(String filePath);
}
