package nl.markv.silk.parse;

import java.io.BufferedReader;

import javax.annotation.Nonnull;

import com.google.gson.Gson;
import nl.markv.silk.types.SilkSchema;

public class GsonSilkParser implements SilkParser {

	static Gson gson = new Gson();

	@Override
	@Nonnull
	public SilkSchema parse(@Nonnull String name, @Nonnull BufferedReader reader) {
		throw new UnsupportedOperationException("GSon parser no longer supported after 0.1.0");
//		SilkSchema result = gson.fromJson(reader, SilkSchema.class);
//		if (result == null) {
//			throw new IllegalStateException("Failed to parse json");
//		}
//		return SilkDb.wrap(name, result);
	}
}
