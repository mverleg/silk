package nl.markv.silk.io;

import java.nio.file.Path;

import javax.annotation.Nonnull;

import nl.markv.silk.types.SilkSchema;

import static nl.markv.silk.io.SilkLockHelper.directlySave;
import static nl.markv.silk.io.SilkLockHelper.lockFilePath;
import static nl.markv.silk.io.SilkLockHelper.waitForLockFileOrFail;

/**
 * Obtain an instance of this class by using {@link SilkLockHelper#loadWithoutLock(Path)}.
 */
public class SilkSchemaUnlockedFile extends SilkSchemaAbstractFile {

	SilkSchemaUnlockedFile(@Nonnull SilkSchema schema, @Nonnull Path schemaPath) {
		super(schema, schemaPath);
	}

	/**
	 * Save the changes, waiting for any other processes that may have locked it.
	 */
	public void save() {
		waitForLockFileOrFail(schemaPath);
		directlySave(schema, schemaPath);
	}

	/**
	 * Save the changes to a new file, waiting for any other processes that may have locked it if it exists.
	 */
	public void saveNew(@Nonnull Path newSchemaPath) {
		waitForLockFileOrFail(newSchemaPath);
		directlySave(schema, newSchemaPath);
	}
}
