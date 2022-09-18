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
import order.payment.parser.model.OrderValidator;

@Component
@RequiredArgsConstructor
public class CsvParser implements Parser {

	private final OrderValidator orderValidator;

	@Override
	public String getType() {
		return "csv";
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
		Integer orderId = csvOrderDetails.getOrderId();
		Double amount = csvOrderDetails.getAmount();
		String currency = csvOrderDetails.getCurrency();
		String comment = csvOrderDetails.getComment();

		String validationResult = orderValidator.validate(orderId, amount, currency, comment);
		String result = validationResult == null ? "OK" : validationResult;
		return OrderDetails.builder().orderId(orderId).amount(amount).currency(currency).comment(comment)
				.fileName(fileName).line(index).result(result).build();
	}

}
