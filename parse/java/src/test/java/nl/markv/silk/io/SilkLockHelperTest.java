package nl.markv.silk.io;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;

import nl.markv.silk.example.Examples;

import static nl.markv.silk.io.SilkLockHelper.lockAndLoad;

class SilkLockHelperTest {

	@Test
	void testLockedEdit() throws URISyntaxException {
		for (URL example : new Examples().urls()) {
			Path pth = Paths.get(example.toURI());
			SilkSchemaLockedFile schemaFile = lockAndLoad(pth);
			schemaFile.saveAndUnlock();
		}
		throw new NotImplementedException("");
	}
}
