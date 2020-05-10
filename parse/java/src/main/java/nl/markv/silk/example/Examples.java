package nl.markv.silk.example;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import nl.markv.silk.parse.Jackson2SilkParser;
import nl.markv.silk.types.SilkSchema;

import static org.apache.commons.lang3.Validate.isTrue;

public class Examples {

	public static final String[] SILK_EXAMPLES = new String[]{
			//"/silk/example/shop01.json",
			"/silk/example/shop02.json",
			"/silk/example/features02.json",
			"/silk/example/tableonly02.json",
	};

	private List<SilkSchema> cachedJsonExamples;

	@Nonnull
	public List<SilkSchema> jsons() {
		if (cachedJsonExamples != null) {
			return cachedJsonExamples;
		}
		cachedJsonExamples = new ArrayList<>();
		Jackson2SilkParser parser = new Jackson2SilkParser();
		for (URL path : urls()) {
			cachedJsonExamples.add(parser.parse(path));
		}
		isTrue(!cachedJsonExamples.isEmpty());
		return cachedJsonExamples;
	}

	@Nonnull
	public List<URL> urls() {
		return Arrays.stream(SILK_EXAMPLES)
				.map(Examples.class::getResource)
				.collect(Collectors.toList());
	}
}
