package nl.markv.silk.generate;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Nonnull;

import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.NoopAnnotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;

public class Generate {

	public static void main(String[] args) {
		String version = "v0_0_1";
		generateSilkObjects(
				version,
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

		GenerationConfig config = SilkConfig.make();
		SchemaMapper mapper = new SchemaMapper(
				new RuleFactory(config, new NoopAnnotator(), new SchemaStore()),
				new SchemaGenerator());
		mapper.generate(codeModel, "Silk", "nl.markv.silk.objects." + version, source);

		File absoluteFile = Paths.get(outputDir.toString()).toFile().getAbsoluteFile();
		absoluteFile.mkdirs();
		try {
			codeModel.build(absoluteFile);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
	}
}
