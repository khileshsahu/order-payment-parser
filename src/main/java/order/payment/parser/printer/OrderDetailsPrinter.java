package order.payment.parser.printer;

import java.util.List;

import order.payment.parser.model.OrderDetails;

public interface OrderDetailsPrinter {
	public void print(List<OrderDetails> orderDetails);
}
