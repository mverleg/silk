package nl.markv.silk.parse;

import java.io.BufferedReader;
import java.io.IOException;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.markv.silk.pojos.v0_2_0.SilkSchema;

public class Jackson2SilkParser implements SilkParser {

	@Override
	@Nonnull
	public SilkDb parse(@Nonnull String name, @Nonnull BufferedReader reader) {
		ObjectMapper mapper = new ObjectMapper();
		SilkSchema result;
		try {
			result = mapper.readValue(reader, SilkSchema.class);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex);
		}
		if (result == null) {
			throw new IllegalStateException("Failed to parse json");
		}
		return SilkDb.wrap(name, result);
	}
}
