package order.payment.parser.app;

import java.util.List;
import java.util.concurrent.Callable;

import order.payment.parser.model.OrderDetails;
import order.payment.parser.parse.Parser;

public class ParserExecutor implements Callable<List<OrderDetails>>{
	
	private Parser parser;
	private String fileName;

	public ParserExecutor(Parser parser, String fileName) {
		this.parser = parser;
		this.fileName = fileName;
	}

	@Override
	public List<OrderDetails> call() throws Exception {
		return parser.parse(fileName);
	}

}
