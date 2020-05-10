package nl.markv.silk.example;

import java.net.URL;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import nl.markv.silk.parse.Jackson2SilkParser;

class ExamplesTest {

	@Nonnull
	static Stream<URL> exampleProvider() {
		return new Examples().urls().stream();
	}

	@ParameterizedTest
	@MethodSource("exampleProvider")
	void testLoadExample(@Nonnull URL url) {
		Jackson2SilkParser parser = new Jackson2SilkParser();
		parser.parse(url);
	}
}