package nl.markv.silk.parse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.annotation.Nonnull;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import nl.markv.silk.SilkVersion;

public class Validator {

	public static void validate(@Nonnull Path jsonPath) {
		try (InputStream jsonStream = new FileInputStream(jsonPath.toString())) {
			Schema schema = SchemaLoader.load(SilkVersion.loadSchema());
			JSONObject rawJson = new JSONObject(new JSONTokener(jsonStream));
			schema.validate(rawJson);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		} catch (ValidationException ex) {
			System.out.println("Validation errors:\n" + ex.toJSON().toString(4));
			throw ex;
		}
		System.out.println("json in '" + jsonPath + "' is valid");
	}
}
