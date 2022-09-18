package order.payment.parser.parse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParserFactory {
	
	@Autowired
	private List<Parser> parsersList;
	
	private Map<String, Parser> parserCache = new HashMap<>();
	
	@PostConstruct
	public void initCache() {
		for(Parser parser : parsersList) {
			parserCache.put(parser.getType(), parser);
		}
	}
	
	public Parser getParser(String filePath) {
		String fileType = filePath.substring(filePath.lastIndexOf(".") + 1);
		
		Parser parser = parserCache.get(fileType);
		
		if(parser == null)
			throw new IllegalArgumentException("Unknown file type");
		
		return parser;
	}

}
