package nl.markv.silk.parse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Nonnull;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import nl.markv.silk.SilkVersion;

public class Validator {

	public static void validate(@Nonnull Path jsonPath) {
		Path schemaPth = Paths.get("..", "..", "schema", SilkVersion.versionPath(), "silk.schema.json").toAbsolutePath();
		try (InputStream inputStream = new FileInputStream(schemaPth.toString())) {
			JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
			Schema schema = SchemaLoader.load(rawSchema);
			schema.validate(new JSONObject(jsonPath));
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		} catch (ValidationException ex) {
			System.out.println(ex.toJSON().toString(4));
			throw ex;
		}
	}
}
