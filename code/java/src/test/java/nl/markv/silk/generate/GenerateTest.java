package nl.markv.silk.generate;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import static nl.markv.silk.generate.Generate.generateSilkObjects;

public class GenerateTest {

	@Test
	public void generateObjects() {
		generateSilkObjects(
				Paths.get("schema", "v0.0.1", "silk.schema.json"),
				Paths.get("src", "main", "java", "nl", "markv", "silk", "objects")
		);
	}
}
