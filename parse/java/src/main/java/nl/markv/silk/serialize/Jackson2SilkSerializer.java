package nl.markv.silk.serialize;

import java.io.IOException;
import java.io.StringWriter;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.markv.silk.types.Db;
import nl.markv.silk.types.SilkSchema;
import nl.markv.silk.types.Table;

public class Jackson2SilkSerializer {

	private static String objectToJson(@Nonnull Object silkEntity) {
		StringWriter writer = new StringWriter();
		try {
			new ObjectMapper().writeValue(writer, silkEntity);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return writer.toString();
	}

	public static String toJson(@Nonnull SilkSchema schema) {
		return objectToJson(schema);
	}

	public static String toJson(@Nonnull Db db) {
		return objectToJson(db);
	}

	public static String toJson(@Nonnull Table table) {
		return objectToJson(table);
	}
}
