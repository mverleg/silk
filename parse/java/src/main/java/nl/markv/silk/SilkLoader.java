package nl.markv.silk;

import java.nio.file.Path;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import nl.markv.silk.io.SilkLockHelper;
import nl.markv.silk.io.SilkSchemaLockedFile;
import nl.markv.silk.io.SilkSchemaUnlockedFile;


/**
 * Helper class to load and save Silk schemas.
 *
 * Includes rudimentary locking to prevent overwriting changes by other processes. Works by
 * <ul>
 * <li> An exclusive file lock (if requested) that allows a single read/write process.
 * <li> A check that remembers the latest change time at read, and rejects changes if it is not the same on write.
 * </ul>
 *
 * Use as:
 *
 * <code><pre>
 * SilkSchemaFile schemaFile = lockAndLoad(
 * 	Paths.get("path", "to", "demo.silk.json"));
 * // Do things with schemaFile.schema
 * schemaFile.saveAndUnlock();
 * </pre></code>
 */
public class SilkLoader {

	/**
	 * Load the Silk schema. If it is locked, waits a while, and fails if lock cannot be obtained.
	 */
	@Nonnull
	@CheckReturnValue
	public static SilkSchemaUnlockedFile loadWithoutLock(@Nonnull Path schemaPath) {
		return SilkLockHelper.loadWithoutLock(schemaPath);
	}

	/**
	 * Load the Silk schema. If it is locked, waits a while, and fails if lock cannot be obtained.
	 */
	@Nonnull
	@CheckReturnValue
	public static SilkSchemaLockedFile lockAndLoad(@Nonnull Path schemaPath) {
		return SilkLockHelper.lockAndLoad(schemaPath);
	}


	/**
	 * Create a new Silk schema. It will save an empty schema file if unlocked, without locking.
	 *
	 * You should fill either 'db' or 'table' in the new schema.
	 */
	@Nonnull
	public static SilkSchemaUnlockedFile newUnlocked(@Nonnull Path schemaPath) {
		return SilkLockHelper.newUnlocked(schemaPath);
	}

	/**
	 * Create a new Silk schema. It will save an empty schema file and lock it.
	 *
	 * You should fill either 'db' or 'table' in the new schema.
	 */
	@Nonnull
	public static SilkSchemaUnlockedFile newLocked(@Nonnull Path schemaPath) {
		return SilkLockHelper.newLocked(schemaPath);
	}
}
