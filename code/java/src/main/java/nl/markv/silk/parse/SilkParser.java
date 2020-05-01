package nl.markv.silk.parse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.Validate;

import nl.markv.silk.objects.v0_0_1.Db;
import nl.markv.silk.objects.v0_0_1.Table;

public interface SilkParser {

	@Nonnull
	default <T> T feedFileToParser(
			@Nonnull Path jsonPath,
			@Nonnull Function<BufferedReader, T> parseFunc
	) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(jsonPath.toFile()));
			return parseFunc.apply(reader);
		} catch (FileNotFoundException ex) {
			throw new IllegalStateException(ex);
		} finally {
			Validate.notNull(reader);
			try {
				reader.close();
			} catch (IOException ex) {
				//noinspection ThrowFromFinallyBlock
				throw new IllegalStateException(ex);
			}
		}
	}

	@Nonnull
	default Db parseDb(@Nonnull Path jsonPath) {
		return feedFileToParser(jsonPath, this::parseDb);
	}

	@Nonnull
	Db parseDb(@Nonnull BufferedReader reader);

	@Nonnull
	default Table parseTable(@Nonnull Path jsonPath) {
		return feedFileToParser(jsonPath, this::parseTable);
	}

	@Nonnull
	Table parseTable(@Nonnull BufferedReader reader);
}
