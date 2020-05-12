package nl.markv.silk;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import nl.markv.silk.io.SilkLockHelper;
import nl.markv.silk.io.SilkSchemaUnlockedFile;

import static nl.markv.silk.SilkLoader.newLocked;
import static nl.markv.silk.SilkLoader.newUnlocked;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SilkLoaderTest {

	@TempDir
	Path tempDir;

	@BeforeAll
	static void globalSetup() {
		SilkLockHelper.LOCK_DURATION_MS = 1;
	}

	@Test
	void testLoadWithoutLock(@TempDir Path tempDir) {
		throw new NotImplementedException("todo: ");  //TODO @mark: implement
	}

	@Test
	void testLockAndLoad(@TempDir Path tempDir) {
		throw new NotImplementedException("todo: ");  //TODO @mark: implement
	}

	@Nested
	class NewUnlocked {
		@Test
		void doesNotLock (@TempDir Path tempDir){
			Path newPth = Paths.get(tempDir.toString(), "test_schema.silk.json");
			newUnlocked(newPth);
			newUnlocked(newPth);
		}

		@Test
		void waitsForLock (@TempDir Path tempDir){
			Path newPth = Paths.get(tempDir.toString(), "test_schema.silk.json");
			newLocked(newPth);
			//TODO @mark: does this test take 30s?
			assertThrows(RuntimeException.class, () -> newUnlocked(newPth));
		}
	}

	@Test
	void testNewLocked(@TempDir Path tempDir) {
		throw new NotImplementedException("todo: ");  //TODO @mark: implement
	}
}
