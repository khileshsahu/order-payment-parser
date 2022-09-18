package order.payment.parser.app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import order.payment.parser.model.OrderDetails;
import order.payment.parser.parse.Parser;
import order.payment.parser.parse.ParserFactory;
import order.payment.parser.printer.OrderDetailsPrinter;

@Component
@RequiredArgsConstructor
public class ParserApp {

	private final ParserFactory parserFactory;
	private final OrderDetailsPrinter orderDetailsPrinter;

	public void parse(String[] filePathArr) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(Math.min(10, filePathArr.length));
		List<Future<List<OrderDetails>>> list = new ArrayList<>();
		for (String filePath : filePathArr) {
			Parser parser = parserFactory.getParser(filePath);
			Callable<List<OrderDetails>> callable = new ParserExecutor(parser, filePath);
			Future<List<OrderDetails>> future = executor.submit(callable);
			list.add(future);
		}

		for (Future<List<OrderDetails>> fut : list) {
			orderDetailsPrinter.print(fut.get());
		}
		
		executor.shutdown();
	}
}
