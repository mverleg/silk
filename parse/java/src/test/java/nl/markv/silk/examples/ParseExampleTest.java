package nl.markv.silk.examples;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;

import nl.markv.silk.SilkVersion;

import static nl.markv.silk.generate.Generate.generateSilkObjects;

public class ParseExampleTest {

	@Test
	public void generateExamples() throws IOException {
		generateSilkObjects(
				SilkVersion.versionDirname(),
				Files.createTempDirectory("silk")
		);
	}
}
