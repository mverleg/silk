package nl.markv.silk.types;

import org.junit.jupiter.api.Test;

import static nl.markv.silk.types.DataType.fromSilkString;
import static org.junit.jupiter.api.Assertions.*;

class DataTypeTest {

	@Test
	void testNonsense() {
		assertThrows(UnsupportedOperationException.class, () -> fromSilkString("null"));
	}

	@Test
	void testFreeText() {
		DataType typ = fromSilkString("text");
		assertTrue(typ instanceof DataType.Text);
		assertNull(((DataType.Text) typ).maxLength);
	}

	@Test
	void testLimitedText() {
		DataType typ = fromSilkString("text(10)");
		assertTrue(typ instanceof DataType.Text);
		assertEquals(10, ((DataType.Text) typ).maxLength);
	}

}