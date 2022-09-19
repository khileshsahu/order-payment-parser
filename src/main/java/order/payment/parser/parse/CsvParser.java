package order.payment.parser.parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.bean.CsvToBeanBuilder;

import lombok.RequiredArgsConstructor;
import order.payment.parser.exception.ParsingException;
import order.payment.parser.model.CsvOrderDetails;
import order.payment.parser.model.OrderDetails;
import order.payment.parser.validator.OrderValidator;

@Component
@RequiredArgsConstructor
public class CsvParser implements Parser {

	private static final String CSV = "csv";
	private final OrderValidator orderValidator;

	@Override
	public String getType() {
		return CSV;
	}

	@Override
	public List<OrderDetails> parse(String filePath) {
		File file = new File(filePath);
		List<CsvOrderDetails> beans = fetchCsvOrderDetailsList(file);

		List<OrderDetails> orderDetailsList = new ArrayList<>();
		for (int i = 0; i < beans.size(); i++) {
			OrderDetails orderDetails = fetchOrderDetails(beans.get(i), file.getName(), i + 1);
			orderDetailsList.add(orderDetails);
		}

		return orderDetailsList;
	}

	private List<CsvOrderDetails> fetchCsvOrderDetailsList(File file) {
		List<CsvOrderDetails> beans = new ArrayList<>();
		try {
			beans = new CsvToBeanBuilder<CsvOrderDetails>(new FileReader(file)).withType(CsvOrderDetails.class).build()
					.parse();
		} catch (IllegalStateException | FileNotFoundException e) {
			throw new ParsingException(e.getMessage(), e);
		}

		return beans;
	}

	private OrderDetails fetchOrderDetails(CsvOrderDetails csvOrderDetails, String fileName, int index) {
		String numberFormatErrorMsg = null;
		Integer orderId = null;
		Double amount = null;
		String currency = csvOrderDetails.getCurrency();
		String comment = csvOrderDetails.getComment();
		try {
			orderId = Integer.parseInt(csvOrderDetails.getOrderId()) ;
		} catch (NumberFormatException e) {
			numberFormatErrorMsg = e.getClass().toString() + " " + e.getMessage();
		}
		
		try {
			amount = Double.valueOf(csvOrderDetails.getAmount());
		} catch (NumberFormatException e) {
			numberFormatErrorMsg = e.getClass().toString() + " " + e.getMessage();
		}
		
		String result = findResult(numberFormatErrorMsg, orderId, amount, currency, comment);
		
		return OrderDetails.builder().orderId(orderId).amount(amount).currency(currency).comment(comment)
				.fileName(fileName).line(index).result(result).build();
	}

	private String findResult(String numberFormatErrorMsg, Integer orderId, Double amount, String currency,
			String comment) {
		String result = "OK";
		if (numberFormatErrorMsg != null) {
			result = numberFormatErrorMsg;
		} else {
			String validationResult = orderValidator.validate(orderId, amount, currency, comment);
			result = validationResult == null ? result : validationResult;
		}
		return result;
	}

}
