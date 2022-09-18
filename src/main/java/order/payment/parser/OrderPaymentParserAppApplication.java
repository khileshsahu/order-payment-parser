package order.payment.parser;

import java.util.concurrent.ExecutionException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import order.payment.parser.app.ParserApp;

@SpringBootApplication
public class OrderPaymentParserAppApplication {

	public static void main(String[] filePaths) throws InterruptedException, ExecutionException {
		ApplicationContext appContext = SpringApplication.run(OrderPaymentParserAppApplication.class, filePaths);
		
		ParserApp parserApp = appContext.getBean(ParserApp.class);
		parserApp.parse(filePaths);
	}

}
