package nl.markv.silk.example;

import org.apache.commons.lang.Validate;
import org.junit.jupiter.api.Test;

class ExamplesTest {

	@Test
	void testLoadExamples() {
		Validate.isTrue(new Examples().jsons().size() >= 1);
	}

}