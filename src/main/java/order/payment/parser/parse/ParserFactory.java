package order.payment.parser.parse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParserFactory {
	
	private final List<Parser> parsersList;
	
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
