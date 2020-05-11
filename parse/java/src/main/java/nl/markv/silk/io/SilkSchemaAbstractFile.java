package nl.markv.silk.io;

import java.nio.file.Path;

import javax.annotation.Nonnull;

import nl.markv.silk.types.SilkSchema;

import static nl.markv.silk.io.SilkLockHelper.directlySave;
import static nl.markv.silk.io.SilkLockHelper.lockFilePath;
import static nl.markv.silk.io.SilkLockHelper.waitForLockFileOrFail;

//TODO @mark: A check that remembers the latest change time at read, and rejects changes if it is not the same on write.

/**
 * Obtain a subclass instance by using {@link SilkLockHelper#lockAndLoad(Path)} or {@link SilkLockHelper#loadWithoutLock(Path)}.
 */
public abstract class SilkSchemaAbstractFile {

	@Nonnull
	public final SilkSchema schema;
	@Nonnull
	final Path schemaPath;

	SilkSchemaAbstractFile(@Nonnull SilkSchema schema, @Nonnull Path schemaPath) {
		this.schema = schema;
		this.schemaPath = schemaPath;
	}
}
