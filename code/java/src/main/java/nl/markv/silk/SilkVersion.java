package nl.markv.silk;

public class SilkVersion {

	private static final SilkVersion inst = new SilkVersion();
	private final String cacheVersion;
	private final String cacheVersionPath;

	public SilkVersion() {
		cacheVersion = inst.getClass().getPackage().getSpecificationVersion();
		cacheVersionPath = cacheVersion.replace(".", "_");
	}

	public static String version() {
		return inst.cacheVersion;
	}

	public static String versionPath() {
		return inst.cacheVersionPath;
	}
}
