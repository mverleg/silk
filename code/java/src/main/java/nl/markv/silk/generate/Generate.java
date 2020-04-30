package nl.markv.silk.generate;

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
		generateSilkObjects(
				Paths.get("schema", "v0.0.1", "silk.schema.json"),
				Paths.get("src", "main", "java", "nl", "markv", "silk", "objects")
		);
	}

	public static void generateSilkObjects(
			@Nonnull Path inputFile,
			@Nonnull Path outputDir
	) {

		JCodeModel codeModel = new JCodeModel();

		URL source;
		try {
			source = inputFile.toUri().toURL();
		} catch (MalformedURLException ex) {
			throw new IllegalStateException(ex);
		}

		GenerationConfig config = SilkConfig.make();
		SchemaMapper mapper = new SchemaMapper(
				new RuleFactory(config, new NoopAnnotator(), new SchemaStore()),
				new SchemaGenerator());
		mapper.generate(codeModel, "ClassName", "nl.markv.silk", source);

		try {
			codeModel.build(outputDir.toFile());
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
	}
}
