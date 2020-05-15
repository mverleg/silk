package nl.markv.silk.parse;

import java.io.BufferedReader;
import java.io.IOException;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.markv.silk.types.SilkSchema;

public class Jackson2SilkParser implements SilkParser {

	Enricher enricher = new Enricher();

	@Override
	@Nonnull
	public SilkSchema parse(@Nonnull String name, @Nonnull BufferedReader reader) {
		nl.markv.silk.pojos.v0_3_0.SilkSchema schema = deserialize(reader);
		return enricher.enrich(name, schema);
	}

	@Nonnull
	private nl.markv.silk.pojos.v0_3_0.SilkSchema deserialize(@Nonnull BufferedReader reader) {
		ObjectMapper mapper = new ObjectMapper();
		nl.markv.silk.pojos.v0_3_0.SilkSchema result;
		try {
			result = mapper.readValue(reader, nl.markv.silk.pojos.v0_3_0.SilkSchema.class);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex);
		}
		if (result == null) {
			throw new IllegalStateException("Failed to parse json");
		}
		return result;
	}
}
