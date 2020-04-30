package nl.markv.silk.generate;

import org.jsonschema2pojo.AnnotationStyle;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Language;
import org.jsonschema2pojo.SourceType;

public class SilkConfig extends DefaultGenerationConfig {

	private SilkConfig() {}

	public static GenerationConfig make() {
		return new SilkConfig();
	}

	@Override
	public SourceType getSourceType() {
		return SourceType.JSONSCHEMA;
	}

//	@Override
//	public boolean isGenerateBuilders() {
//		return true;
//	}

	@Override
	public boolean isUsePrimitives() {
		return true;
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
		return false;
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
}
