package nl.markv.silk;

import java.nio.file.Path;

import javax.annotation.Nonnull;

import nl.markv.silk.types.SilkSchema;

import static nl.markv.silk.SilkLoader.directlySave;
import static nl.markv.silk.SilkLoader.lockFilePath;
import static nl.markv.silk.SilkLoader.waitForLockFileOrFail;

//TODO @mark: A check that remembers the latest change time at read, and rejects changes if it is not the same on write.

/**
 * Obtain an instance of this class by using {@link SilkLoader#lockAndLoad(Path)} or {@link SilkLoader#loadWithoutLock(Path)}.
 */
public class SilkSchemaFile {

	@Nonnull
	public final SilkSchema schema;
	@Nonnull
	final Path schemaPath;
	boolean locked;

	SilkSchemaFile(@Nonnull SilkSchema schema, @Nonnull Path schemaPath, boolean locked) {
		this.schema = schema;
		this.schemaPath = schemaPath;
		this.locked = locked;
	}

	/**
	 * Save the changes, and unlock the schema (if it was locked).
	 */
	public void saveAndUnlock() {
		if (!locked) {
			// Wait for locks by other processes to release.
			waitForLockFileOrFail(lockFilePath(schemaPath));
		}
		try {
			directlySave(schema, schemaPath);
		} finally {
			if (locked) {
				unlock();
			}
		}
	}

	/**
	 * Save the changed, hanging on to the lock (if any) for possible further changes.
	 */
	public void saveWithoutUnlock() {
		if (!locked) {
			// Wait for locks by other processes to release.
			waitForLockFileOrFail(lockFilePath(schemaPath));
		}
		directlySave(schema, schemaPath);
	}

	/**
	 * Unlock the schema file. For example when discarding changes. Should not save changes after this.
	 */
	public void unlock() {
		if (locked) {
			locked = false;
			SilkLoader.unlock(schemaPath);
		}
	}
}
