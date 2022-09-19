package order.payment.parser.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderValidatorTest {
	
	OrderValidator orderValidator = null;

	@Test
	void testNullRequiredFields() {
		orderValidator = new OrderValidator();
		String errorMsg = orderValidator.validate(null, null, null, null);
		
		assertNotNull(errorMsg);
		assertEquals("Missing required fields : orderId amount currency comment ", errorMsg);
	}
	
	@Test
	void testEmptyRequiredFields() {
		orderValidator = new OrderValidator();
		String errorMsg = orderValidator.validate(0, 0.0, "", "");
		
		assertNotNull(errorMsg);
		assertEquals("These fields can not be empty : orderId amount currency comment ", errorMsg);
	}

}
