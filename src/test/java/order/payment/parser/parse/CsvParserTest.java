package order.payment.parser.parse;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import order.payment.parser.model.OrderDetails;
import order.payment.parser.validator.OrderValidator;

class CsvParserTest {
	
	private CsvParser csvParser;

	@Test
	void testCsvParser() throws FileNotFoundException {
		csvParser = new CsvParser(new OrderValidator());
		File file = ResourceUtils.getFile("classpath:order1.csv");
		List<OrderDetails> responseList = csvParser.parse(file.getPath());
		
		assertNotNull(responseList);
		assertEquals(2, responseList.size());
	}

}
