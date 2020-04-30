package nl.markv.silk.objects.generate;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.NoopAnnotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;

public class Generate {

	public static void main(String[] args) {
		generateSilkObjects();
	}

	public static void generateSilkObjects() {

		JCodeModel codeModel = new JCodeModel();

		URL source = Generate.class.getResource("../../schema/v0.0.1/silk.schema.json");

		GenerationConfig config = SilkConfig.make();
		SchemaMapper mapper = new SchemaMapper(
				new RuleFactory(config, new NoopAnnotator(), new SchemaStore()),
				new SchemaGenerator());
		mapper.generate(codeModel, "ClassName", "com.example", source);

		try {
			codeModel.build(new File("output_dir"));
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
	}
}
