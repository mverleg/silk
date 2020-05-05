package nl.markv.silk.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;

import javax.annotation.Nonnull;

import nl.markv.silk.pojos.v0_1_0.SilkSchema;

public interface SilkParser {

	@Nonnull
	default <T> T feedFileToParser(
			@Nonnull Path jsonPath,
			@Nonnull Function<BufferedReader, T> parseFunc
	) {
		Validator.validate(jsonPath);
		BufferedReader reader;
		File file = jsonPath.toAbsolutePath().toFile();
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException ex) {
			throw new IllegalStateException(ex);
		}
		try {
			return parseFunc.apply(reader);
		} finally {
			try {
				reader.close();
			} catch (IOException ex) {
				throw new IllegalStateException(ex);
			}
		}
	}

	@Nonnull
	default SilkSchema parse(@Nonnull Path jsonPath) {
		return feedFileToParser(jsonPath, this::parse);
	}

	@Nonnull
	SilkSchema parse(@Nonnull BufferedReader reader);
}
