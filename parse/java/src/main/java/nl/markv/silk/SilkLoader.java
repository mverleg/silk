package nl.markv.silk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import org.apache.commons.lang3.NotImplementedException;

import nl.markv.silk.types.SilkSchema;


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
	public static SilkSchemaFile loadWithoutLock(@Nonnull Path path) {
		return new SilkSchemaFile(directlyLoad(path), path, false);
	}

	/**
	 * Load the Silk schema. If it is locked, waits a while, and fails if lock cannot be obtained.
	 */
	@Nonnull
	@CheckReturnValue
	public static SilkSchemaFile lockAndLoad(@Nonnull Path path) {
		lock(path);
		SilkSchema schema;
		try {
			schema = directlyLoad(path);
		} catch (Exception ex) {
			unlock(path);
			throw ex;
		}
		return new SilkSchemaFile(schema, path, true);
	}

	@Nonnull
	@CheckReturnValue
	static SilkSchema directlyLoad(@Nonnull Path schemaPath) {
		throw new NotImplementedException("todo");  //TODO @mark: implement
	}

	static void directlySave(@Nonnull SilkSchema silkSchema, @Nonnull Path schemaPath) {
		throw new NotImplementedException("todo");  //TODO @mark: implement
	}

	static void lock(@Nonnull Path schemaPath) {
		if (!Files.exists(schemaPath)) {
			throw new IllegalStateException("Could not lock schema at '" + schemaPath + "' because it does not exist");
		}
		Path lockFile = lockFilePath(schemaPath);
		waitForLockFileOrFail(lockFile);
		try {
			Files.createFile(lockFile);
		} catch (IOException ex) {
			throw new IllegalStateException("Failed to obtain lock", ex);
		}
	}

	static void unlock(@Nonnull Path schemaPath) {
		Path lockFile = lockFilePath(schemaPath);
		if (!Files.exists(lockFile)) {
			throw new IllegalStateException("Tried to unlock file '" + schemaPath +
					"', but the file was not locked");
		}
		try {
			Files.delete(lockFile);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * Wait for a file to disappear for a specified maximum time.
	 *
	 * @return Whether the file has disappeared before the time expired.
	 */
	private static boolean waitForLockFile(@Nonnull Path lockFilePath, int timeoutMs) {
		if (!Files.exists(lockFilePath)) {
			return true;
		}
		System.out.println("waiting for lock file to disappear (" + lockFilePath +
				") for at most " + (timeoutMs / 1000) + "s");
		int waitTimeMs = 0;
		while (waitTimeMs < timeoutMs) {
			waitTimeMs += 50;
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				// Someone wants us to wake up; we'll just skip this tick.
			}
			if (!Files.exists(lockFilePath)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Like {@link #waitForLockFile(Path, int)}, but throws if the lock is not obtained in 30s.
	 */
	static void waitForLockFileOrFail(@Nonnull Path schemaPath) {
		Path lockPath = lockFilePath(schemaPath);
		if (!waitForLockFile(lockPath, 30_000)) {
			throw new IllegalStateException("Could not lock Silk schema at '" + schemaPath +
					"' because it is already locked, and was not released quickly enough " +
					"(if you are sure that no other program uses the schema, delete the " +
					"lock file at '" + lockPath + "')");
		}
	}

	@Nonnull
	static Path lockFilePath(@Nonnull Path schemaPath) {
		return schemaPath.resolveSibling(schemaPath.getFileName() + ".lock~");
	}
}
