package nl.markv.silk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.json.JSONObject;
import org.json.JSONTokener;

public class SilkVersion {

	private static final SilkVersion inst;
	private String cacheVersion;
	private String cacheVersionDirname;
	private Path cachedSchemaPath;
	private JSONObject cachedSchemaObject;

	static {
		inst = new SilkVersion();
		inst.init();
	}

	private void init() {
		Package pack = this.getClass().getPackage();
		if (pack.getSpecificationVersion() != null) {
			cacheVersion = pack.getSpecificationVersion();
		} else {
			// For IDE use we'll just grab the version from the pom-file.
			File file = new File("pom.xml").getAbsoluteFile();
			String pom;
			try {
				pom = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
			} catch (IOException ex) {
				throw new IllegalStateException(ex);
			}
			Pattern re = Pattern.compile("<version>([^<>]+)</version>");
			Matcher match = re.matcher(pom);
			Validate.isTrue(match.find(), "could not extract version from pomfile");
			cacheVersion = match.group(1).trim();

			cacheVersionDirname = "v" + cacheVersion.replace(".", "_");

			cachedSchemaPath = Paths.get("/silk/schema", cacheVersionDirname, "silk.schema.json");
		}
	}

	@Nonnull
	public static String version() {
		return inst.cacheVersion;
	}

	@Nonnull
	public static String versionDirname() {
		return inst.cacheVersionDirname;
	}

	@Nonnull
	public static Path schemaPath() {
		return inst.cachedSchemaPath;
	}

	@Nonnull
	public static JSONObject loadSchema() {
		if (inst.cachedSchemaObject != null) {
			return inst.cachedSchemaObject;
		}
		try (InputStream schemaStream = new FileInputStream(SilkVersion.schemaPath().toString())) {
			inst.cachedSchemaObject = new JSONObject(new JSONTokener(schemaStream));
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return inst.cachedSchemaObject;
	}

	//TODO: list of all versions
}
