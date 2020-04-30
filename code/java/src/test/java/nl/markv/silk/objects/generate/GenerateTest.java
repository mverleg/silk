package nl.markv.silk.objects.generate;

import java.io.File;
import java.net.URL;

import org.jsonschema2pojo.AnnotationStyle;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.Language;
import org.jsonschema2pojo.NoopAnnotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.rules.RuleFactory;
import org.junit.Test;

import com.sun.codemodel.JCodeModel;

public class GenerateTest {
	@Test
	public void generateObjects() {
		JCodeModel codeModel = new JCodeModel();

		URL source = GenerateTest.class.getResource("../../schema/v0.0.1/silk.schema.json");

		GenerationConfig config = SilkConfig.make();
		SchemaMapper mapper = new SchemaMapper(
				new RuleFactory(config, new NoopAnnotator(), new SchemaStore()),
				new SchemaGenerator());
		mapper.generate(codeModel, "ClassName", "com.example", source);

		codeModel.build(Files.createTempDirectory("required").toFile());
	}
}
