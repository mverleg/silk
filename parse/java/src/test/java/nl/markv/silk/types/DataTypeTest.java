package nl.markv.silk.types;

import org.junit.jupiter.api.Test;

import static nl.markv.silk.types.DataType.fromSilkString;
import static org.junit.jupiter.api.Assertions.*;

class DataTypeTest {

	// decimal\(\d+\)|decimal\(\d+,\d+\)

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
	void testTimestamp() {
		DataType typ = fromSilkString("timestamp");
		assertTrue(typ instanceof DataType.Timestamp);
	}

	@Test
	void testLimitedText() {
		DataType typ = fromSilkString("text(10)");
		assertTrue(typ instanceof DataType.Text);
		assertEquals(10, ((DataType.Text) typ).maxLength);
	}

	@Test
	void testSignedInt() {
		DataType typ = fromSilkString("int");
		assertTrue(typ instanceof DataType.Int);
		assertNull(((DataType.Int) typ).min);
		assertNull(((DataType.Int) typ).max);
	}

	@Test
	void testUnsignedInt() {
		DataType typ = fromSilkString("uint");
		assertTrue(typ instanceof DataType.Int);
		assertEquals(0, ((DataType.Int) typ).min);
		assertNull(((DataType.Int) typ).max);
	}

	@Test
	void testDecimal1() {
		DataType typ = fromSilkString("decimal(10)");
		assertTrue(typ instanceof DataType.Decimal);
		assertEquals(10, ((DataType.Decimal) typ).precision);
		assertNull(((DataType.Decimal) typ).scale);
	}

	@Test
	void testDecimal2() {
		DataType typ = fromSilkString("decimal(10,2)");
		assertTrue(typ instanceof DataType.Decimal);
		assertEquals(10, ((DataType.Decimal) typ).precision);
		assertEquals(2, ((DataType.Decimal) typ).scale);
		DataType lenient = fromSilkString("decimal(10,  2)");
	}
}