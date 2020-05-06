package nl.markv.silk.parse;

import java.io.BufferedReader;

import javax.annotation.Nonnull;

import com.google.gson.Gson;
import nl.markv.silk.pojos.v0_1_0.SilkSchema;

public class GsonSilkParser implements SilkParser {

	static Gson gson = new Gson();

	@Override
	@Nonnull
	public SilkDb parse(@Nonnull BufferedReader reader) {
		SilkSchema result = gson.fromJson(reader, SilkSchema.class);
		if (result == null) {
			throw new IllegalStateException("Failed to parse json");
		}
		return SilkDb.wrap(result);
	}

//	@Nonnull
//	public static Optional<Column>
}
