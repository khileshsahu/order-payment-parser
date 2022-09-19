package order.payment.parser.parse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import order.payment.parser.model.OrderDetails;
import order.payment.parser.validator.OrderValidator;

class JsonParserTest {
	
	private JsonParser jsonParser; 

	@Test
	void testJsonParser() throws FileNotFoundException {
		jsonParser = new JsonParser(new OrderValidator());
		File file = ResourceUtils.getFile("classpath:order2.json");
		List<OrderDetails> responseList = jsonParser.parse(file.getPath());
		
		assertNotNull(responseList);
		assertEquals(2, responseList.size());
	}

}
