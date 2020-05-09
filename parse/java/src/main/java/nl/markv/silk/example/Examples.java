package nl.markv.silk.example;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import nl.markv.silk.parse.GsonSilkParser;
import nl.markv.silk.parse.SilkDb;

import static org.apache.commons.lang3.Validate.isTrue;

public class Examples {

	public static final String[] SILK_EXAMPLES = new String[]{
			//"/silk/example/shop01.json",
			"/silk/example/shop02.json",
	};

	private List<SilkDb> cachedJsonExamples;

	@Nonnull
	public List<SilkDb> jsons() {
		if (cachedJsonExamples != null) {
			return cachedJsonExamples;
		}
		cachedJsonExamples = new ArrayList<>();
		for (URL path : urls()) {
			cachedJsonExamples.add(new GsonSilkParser().parse(path));
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
