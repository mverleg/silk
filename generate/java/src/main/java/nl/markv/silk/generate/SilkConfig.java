package nl.markv.silk.generate;

import org.jsonschema2pojo.AnnotationStyle;
import org.jsonschema2pojo.Annotator;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.Language;
import org.jsonschema2pojo.SourceType;

public class SilkConfig extends DefaultGenerationConfig {

	private SilkConfig() {}

	public static SilkConfig make() {
		return new SilkConfig();
	}

	@Override
	public SourceType getSourceType() {
		return SourceType.JSONSCHEMA;
	}

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
		// Also change makeAnnotator
		return AnnotationStyle.JACKSON2;
	}

	public Annotator makeAnnotator() {
		// Also change getAnnotationStyle
		return new Jackson2Annotator(this);
	}

	@Override
	public boolean isParcelable() {
		return false;
	}

	@Override
	public boolean isSerializable() {
		return false;
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
		return true;
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
