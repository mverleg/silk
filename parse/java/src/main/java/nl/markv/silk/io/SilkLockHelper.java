package nl.markv.silk.io;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import org.apache.commons.lang3.NotImplementedException;

import nl.markv.silk.parse.Jackson2SilkParser;
import nl.markv.silk.types.SilkSchema;

import static nl.markv.silk.SilkVersion.versionUrl;
import static org.apache.commons.lang3.Validate.isTrue;


/**
 * Implements the locking described by {@link nl.markv.silk.SilkLoader}.
 */
public class SilkLockHelper {

	public static int LOCK_DURATION_MS = 30_000;

	/**
	 * Load the Silk schema. If it is locked, waits a while, and fails if lock cannot be obtained.
	 */
	@Nonnull
	@CheckReturnValue
	public static SilkSchemaUnlockedFile loadWithoutLock(@Nonnull Path path) {
		return new SilkSchemaUnlockedFile(directlyLoad(path), path);
	}

	/**
	 * Load the Silk schema. If it is locked, waits a while, and fails if lock cannot be obtained.
	 */
	@Nonnull
	@CheckReturnValue
	public static SilkSchemaLockedFile lockAndLoad(@Nonnull Path path) {
		lock(path);
		SilkSchema schema;
		try {
			schema = directlyLoad(path);
		} catch (Exception ex) {
			unlock(path);
			throw ex;
		}
		return new SilkSchemaLockedFile(schema, path);
	}

	/**
	 * Create a new Silk schema. It will save an empty schema file if unlocked, without locking.
	 */
	@Nonnull
	public static SilkSchemaUnlockedFile newUnlocked(@Nonnull Path schemaPath) {
		waitForLockFileOrFail(schemaPath);
		SilkSchema schema = SilkSchema.empty(schemaPath.getFileName().toString(), versionUrl());
		directlySave(schema, schemaPath);
		return new SilkSchemaUnlockedFile(schema, schemaPath);
	}

	/**
	 * Create a new Silk schema. It will save an empty schema file and lock it.
	 */
	@Nonnull
	public static SilkSchemaUnlockedFile newLocked(@Nonnull Path schemaPath) {
		waitForLockFileOrFail(schemaPath);
		SilkSchema schema = SilkSchema.empty(schemaPath.getFileName().toString(), versionUrl());
		directlySave(schema, schemaPath);
		lock(schemaPath);
		return new SilkSchemaUnlockedFile(schema, schemaPath);
	}

	@Nonnull
	@CheckReturnValue
	static SilkSchema directlyLoad(@Nonnull Path schemaPath) {
		Jackson2SilkParser parser = new Jackson2SilkParser();
		return parser.parse(schemaPath);
	}

	static void directlySave(@Nonnull SilkSchema silkSchema, @Nonnull Path schemaPath) {
		throw new NotImplementedException("todo");  //TODO @mark: implement
	}

	static void lock(@Nonnull Path schemaPath) {
		if (!Files.exists(schemaPath)) {
			throw new IllegalStateException("Could not lock schema at '" + schemaPath +
					"' because it does not exist");
		}
		waitForLockFileOrFail(schemaPath);
		Path lockFile = lockFilePath(schemaPath);
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
		assert !schemaPath.endsWith(".lock~");
		Path lockPath = lockFilePath(schemaPath);
		if (!waitForLockFile(lockPath, LOCK_DURATION_MS)) {
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
