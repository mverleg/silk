package nl.markv.silk;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;

public class SilkVersion {

	private static final SilkVersion inst;
	private String cacheVersion;
	private String cacheVersionPath;

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
		}
		cacheVersionPath = "v" + cacheVersion.replace(".", "_");
	}

	public static String version() {
		return inst.cacheVersion;
	}

	public static String versionPath() {
		return inst.cacheVersionPath;
	}

	//TODO: list of all versions
}
