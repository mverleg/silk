package nl.markv.silk.generate;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import nl.markv.silk.SilkVersion;

import static nl.markv.silk.generate.Generate.generateSilkObjects;

public class GenerateTest {

	@Test
	public void parseExamples() {
		generateSilkObjects(
				SilkVersion.versionPath(),
				Paths.get("src", "main", "java")
		);
	}
}
