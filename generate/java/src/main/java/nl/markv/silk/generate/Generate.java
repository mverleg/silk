package nl.markv.silk.generate;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Nonnull;

import org.apache.commons.io.FileUtils;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;
import nl.markv.silk.SilkVersion;

public class Generate {

	public static void main(String[] args) {
		generateSilkObjects(
				SilkVersion.versionPath(),
				Paths.get("..", ".."),
				Paths.get("src", "main", "java")
		);
	}

	public static void generateSilkObjects(
			@Nonnull String version,
			@Nonnull Path projectDir,
			@Nonnull Path outputDir
	) {

		JCodeModel codeModel = new JCodeModel();

		URL source;
		try {
			source = Paths.get(projectDir.toString(), "schema",
					version, "silk.schema.json").toUri().toURL();
		} catch (MalformedURLException ex) {
			throw new IllegalStateException(ex);
		}

		SilkConfig config = SilkConfig.make();
		SchemaMapper mapper = new SchemaMapper(
				new RuleFactory(config, config.makeAnnotator(), new SchemaStore()),
				new SchemaGenerator());
		mapper.generate(codeModel, "Silk", "nl.markv.silk.pojos." + version, source);

		File absoluteFile = Paths.get(outputDir.toString()).toFile().getAbsoluteFile();
		//noinspection ResultOfMethodCallIgnored
		absoluteFile.mkdirs();
		Path pojoDir = Paths.get(absoluteFile.toString(), "nl", "markv", "silk", "pojos", version);
		System.out.println(pojoDir);
		try {
			FileUtils.cleanDirectory(pojoDir.toFile());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		try {
			codeModel.build(absoluteFile);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
	}
}
