package nl.markv.silk.parse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import javax.annotation.Nonnull;

import nl.markv.silk.objects.v0_0_1.Db;
import nl.markv.silk.objects.v0_0_1.Table;

public interface SilkParser {

	@Nonnull
	default Db parseDb(@Nonnull Path jsonPath) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(jsonPath.toFile()));
			return parseDb(reader);
		} catch (FileNotFoundException ex) {
			throw new IllegalStateException(ex);
		} finally {
			try {
				reader.close();
			} catch (IOException ex) {
				throw new IllegalStateException(ex);
			}
		}
	}

	@Nonnull
	Db parseDb(@Nonnull BufferedReader reader);

	@Nonnull
	default Table parseTable(@Nonnull Path jsonPath) {

	}

	@Nonnull
	Table parseTable(@Nonnull BufferedReader reader);
}
