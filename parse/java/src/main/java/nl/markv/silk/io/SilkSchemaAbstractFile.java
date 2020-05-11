package nl.markv.silk.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import javax.annotation.Nonnull;

import nl.markv.silk.types.SilkSchema;

//TODO @mark: A check that remembers the latest change time at read, and rejects changes if it is not the same on write.

/**
 * Obtain a subclass instance by using {@link SilkLockHelper#lockAndLoad(Path)} or {@link SilkLockHelper#loadWithoutLock(Path)}.
 */
public abstract class SilkSchemaAbstractFile {

	@Nonnull
	public final SilkSchema schema;
	@Nonnull
	final Path schemaPath;
	@Nonnull
	protected FileTime lastChangedAtLoad;

	SilkSchemaAbstractFile(@Nonnull SilkSchema schema, @Nonnull Path schemaPath) {
		this.schema = schema;
		this.schemaPath = schemaPath;
		lastChangedAtLoad = updateLastChanged();
	}

	@Nonnull
	private static FileTime lastChanged(@Nonnull Path path) {
		try {
			return Files.getLastModifiedTime(path);
		} catch (IOException ex) {
			throw new IllegalStateException("Could not get last modified time of schema '" +
					path + "'", ex);
		}
	}

	@Nonnull
	FileTime updateLastChanged() {
		return this.lastChangedAtLoad = lastChanged(schemaPath);
	}

	void assertNotChangedByOtherProcess() {
		FileTime lastChangedCurrently = lastChanged(schemaPath);
		if (!lastChangedCurrently.equals(lastChangedAtLoad)) {
			throw new IllegalStateException();
		}
	}
}
