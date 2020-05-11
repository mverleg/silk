package nl.markv.silk.io;

import java.nio.file.Path;

import javax.annotation.Nonnull;

import nl.markv.silk.types.SilkSchema;

import static nl.markv.silk.io.SilkLockHelper.directlySave;
import static nl.markv.silk.io.SilkLockHelper.waitForLockFileOrFail;

/**
 * Obtain an instance of this class by using {@link SilkLockHelper#lockAndLoad(Path)}.
 *
 * @apiNote Make sure to call one of the unlocking methods, or the file will remain locked until cleared manually!
 */
public class SilkSchemaLockedFile extends SilkSchemaAbstractFile {

	SilkSchemaLockedFile(@Nonnull SilkSchema schema, @Nonnull Path schemaPath) {
		super(schema, schemaPath);
	}

	/**
	 * Save the changes, and unlock the schema.
	 */
	@Nonnull
	public SilkSchemaUnlockedFile saveAndUnlock() {
		// Intentionally not updating timestamp here, to 'encourage' using the return object.
		assertNotChangedByOtherProcess();
		directlySave(schema, schemaPath);
		return unlock();
	}

	/**
	 * Save the changes to a new file, and unlock the schema (if it was locked).
	 *
	 * @apiNote If you know beforehand that you will use this, perhaps open without lock.
	 */
	@Nonnull
	public SilkSchemaUnlockedFile saveNewAndUnlock(@Nonnull Path newSchemaPath) {
		// Intentionally not updating timestamp here, to 'encourage' using the return object.
		waitForLockFileOrFail(newSchemaPath);
		assertNotChangedByOtherProcess();
		directlySave(schema, newSchemaPath);
		unlock();
		return new SilkSchemaUnlockedFile(schema, newSchemaPath);
	}

	/**
	 * Save the changes but keep the lock.
	 */
	public void saveWithoutUnlock() {
		assertNotChangedByOtherProcess();
		directlySave(schema, schemaPath);
		updateLastChanged();
	}

	/**
	 * Unlock the schema file. For example when discarding changes.
	 */
	@Nonnull
	public SilkSchemaUnlockedFile unlock() {
		SilkLockHelper.unlock(schemaPath);
		return new SilkSchemaUnlockedFile(schema, schemaPath);
	}
}
