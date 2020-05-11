package nl.markv.silk;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import static nl.markv.silk.SilkLoader.lockAndLoad;

class SilkLoaderTest {

	@Test
	void testLockedEdit() {
		SilkSchemaFile schemaFile = lockAndLoad(
			Paths.get("path", "to", "demo.silk.json"));
		// Do things with schemaFile.schema
		schemaFile.saveAndUnlock();
	}
}