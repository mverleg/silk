package nl.markv.silk.parse;

import java.io.BufferedReader;
import java.nio.file.Paths;

import javax.annotation.Nonnull;

import com.google.gson.Gson;
import nl.markv.silk.pojos.v0_1_0.SilkSchema;

public class GsonSilkParser implements SilkParser {

	static Gson gson = new Gson();

	public static void main(String[] args) {
		SilkParser parser = new GsonSilkParser();
		SilkSchema silk = parser.parse(Paths.get("..", "..", "example", "shop.json"));
		System.out.println(gson.toJson(silk));
	}

	@Override
	@Nonnull
	public SilkSchema parse(@Nonnull BufferedReader reader) {
		SilkSchema result = gson.fromJson(reader, SilkSchema.class);
		if (result == null) {
			throw new IllegalStateException("Failed to parse json");
		}
		return result;
	}

//	@Nonnull
//	public static Optional<Column>
}
