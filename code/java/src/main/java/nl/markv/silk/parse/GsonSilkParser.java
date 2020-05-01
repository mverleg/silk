package nl.markv.silk.parse;

import java.io.BufferedReader;
import java.nio.file.Paths;

import javax.annotation.Nonnull;

import com.google.gson.Gson;
import nl.markv.silk.objects.v0_0_1.Db;
import nl.markv.silk.objects.v0_0_1.Table;

public class GsonSilkParser implements SilkParser {

	Gson gson = new Gson();

	public static void main(String[] args) {
		SilkParser parser = new GsonSilkParser();
		Db db = parser.parseDb(Paths.get("..", "..", "example", "shop.json"));
		System.out.println(db);
	}

	@Override
	@Nonnull
	public Db parseDb(@Nonnull BufferedReader reader) {
		Db result = gson.fromJson(reader, Db.class);
		if (result == null) {
			throw new IllegalStateException("Failed to parse json");
		}
		return result;
	}

	@Override
	@Nonnull
	public Table parseTable(@Nonnull BufferedReader reader) {
		Table result = gson.fromJson(reader, Table.class);
		if (result == null) {
			throw new IllegalStateException("Failed to parse json");
		}
		return result;
	}
}
