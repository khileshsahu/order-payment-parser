package order.payment.parser.parse;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import order.payment.parser.validator.OrderValidator;

class ParserFactoryTest {
	

	private ParserFactory parserFactory;
	
	@Test
	void test() {
		
		List<Parser> parsersList = createParserList();
		parserFactory = new ParserFactory(parsersList);
		parserFactory.initCache();
		
		Parser parser = parserFactory.getParser("abc.json");
		
		assertTrue(parser instanceof JsonParser);
	}

	private List<Parser> createParserList() {
		List<Parser> parsersList = new ArrayList<>();
		JsonParser jsonParser = new JsonParser(new OrderValidator());
		parsersList.add(jsonParser);
		CsvParser csvParser = new CsvParser(new OrderValidator());
		parsersList.add(csvParser);
		return parsersList;
	}

}
