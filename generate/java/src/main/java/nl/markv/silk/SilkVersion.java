package nl.markv.silk;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;

public class SilkVersion {

	private static final SilkVersion inst;
	private String cacheVersion;
	private String cacheVersionPath;
	private Path cachedSchemaPath;

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

			cachedSchemaPath = Paths.get("/silk/schema", cacheVersion, "silk.schema.json");
		}
		cacheVersionPath = "v" + cacheVersion.replace(".", "_");
	}

	@Nonnull
	public static String version() {
		return inst.cacheVersion;
	}

	@Nonnull
	public static String versionDirname() {
		return inst.cacheVersionPath;
	}

	@Nonnull
	public static Path schemaPath() {
		return inst.cachedSchemaPath;
	}

	//TODO: list of all versions
}
