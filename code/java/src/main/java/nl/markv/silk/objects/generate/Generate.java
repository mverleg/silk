package nl.markv.silk.objects.generate;

import java.io.File;
import java.net.URL;

import org.jsonschema2pojo.AnnotationStyle;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Language;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.rules.RuleFactory;
import org.junit.Test;
import org.junit.jupiter.api.Test;

import com.sun.codemodel.JCodeModel;

public class Generate {
	@Test
	public void generateObjects() {
		JCodeModel codeModel = new JCodeModel();

		URL source = Generate.class.getResource("../../schema/v0.0.1/silk.schema.json");

		GenerationConfig config = new DefaultGenerationConfig() {
			@Override
			public boolean isGenerateBuilders() {
				return true;
			}

			@Override
			public boolean isUsePrimitives() {
				return true;
			}

			@Override
			public File getTargetDirectory() {
				return new File("src/main/java/nl/markv/silk/objects");
			}

			@Override
			public boolean isUseLongIntegers() {
				return false;
			}

			@Override
			public boolean isUseDoubleNumbers() {
				return true;
			}

			@Override
			public boolean isIncludeHashcodeAndEquals() {
				return true;
			}

			@Override
			public boolean isIncludeToString() {
				return true;
			}

			@Override
			public AnnotationStyle getAnnotationStyle() {
				return AnnotationStyle.NONE;
			}

			@Override
			public boolean isParcelable() {
				return true;
			}

			@Override
			public boolean isSerializable() {
				return true;
			}

			@Override
			public boolean isInitializeCollections() {
				return true;
			}

			@Override
			public boolean isIncludeConstructors() {
				return true;
			}

			@Override
			public boolean isConstructorsRequiredPropertiesOnly() {
				return false;
			}

			@Override
			public boolean isIncludeRequiredPropertiesConstructor() {
				return false;
			}

			@Override
			public boolean isIncludeAllPropertiesConstructor() {
				return true;
			}

			@Override
			public boolean isIncludeAdditionalProperties() {
				return false;
			}

			@Override
			public boolean isIncludeGetters() {
				return false;
			}

			@Override
			public boolean isIncludeSetters() {
				return false;
			}

			@Override
			public Language getTargetLanguage() {
				return Language.JAVA;
			}
		};

		SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
		mapper.generate(codeModel, "ClassName", "com.example", source);

		codeModel.build(Files.createTempDirectory("required").toFile());
	}
}
