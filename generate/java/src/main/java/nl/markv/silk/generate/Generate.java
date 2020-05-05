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

import static org.apache.commons.lang3.Validate.notNull;

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

		Path schemaPth = Paths.get("schema", version, "silk.schema.json");
		URL source = notNull(
				Generate.class.getResource(schemaPth.toString()),
				"could not find schema file as class resource"
		);

		SilkConfig config = SilkConfig.make();
		SchemaMapper mapper = new SchemaMapper(
				new RuleFactory(config, config.makeAnnotator(), new SchemaStore()),
				new SchemaGenerator());
		mapper.generate(codeModel, "Silk", "nl.markv.silk.pojos." + version, source);

		File absoluteFile = Paths.get(outputDir.toString()).toFile().getAbsoluteFile();
		//noinspection ResultOfMethodCallIgnored
		absoluteFile.mkdirs();
		Path pojoDir = Paths.get(absoluteFile.toString(), "nl", "markv", "silk", "pojos", version);
		try {
			if (pojoDir.toFile().exists()) {
				FileUtils.cleanDirectory(pojoDir.toFile());
			}
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
