package nl.markv.silk.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.util.function.Function;

import javax.annotation.Nonnull;

import nl.markv.silk.types.SilkSchema;

public interface SilkParser {

	@Nonnull
	default <T> T feedBufferedReader(
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
		return feedFileToParser(jsonPath, reader -> parse(jsonPath.toFile().getName(), reader));
	}

	@Nonnull
	default SilkSchema parse(@Nonnull URL jsonUrl) {
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(jsonUrl.openStream()));
			return parse(toName(jsonUrl.getFile()), in);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
	}

	default String toName(@Nonnull String pth) {
		String[] parts = pth.split("[/\\]]");
		return parts[parts.length - 1];
	}

	@Nonnull
	SilkSchema parse(@Nonnull String name, @Nonnull BufferedReader reader);
}
