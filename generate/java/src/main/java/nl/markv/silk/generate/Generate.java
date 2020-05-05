package nl.markv.silk.generate;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
		try {
			//TODO @mark: TEMPORARY! REMOVE THIS!
			System.out.println(new Generate().getResourceFiles("schema/v0_1_0/"));
			System.out.println(Generate.class.getResource("/schema.v0_1_0.silk.schema.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		generateSilkObjects(
				SilkVersion.versionPath(),
				Paths.get("src", "main", "java")
		);
	}

	public static void generateSilkObjects(
			@Nonnull String version,
			@Nonnull Path outputDir
	) {

		JCodeModel codeModel = new JCodeModel();

		Path schemaPth = Paths.get("/schema", version, "silk.schema.json");
		URL source = notNull(
				Generate.class.getResource(schemaPth.toString()),
				"could not find schema file as class resource at '" + schemaPth + "'"
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


	//TODO @mark: TEMPORARY! REMOVE THIS!
	private List<String> getResourceFiles(String path) throws IOException {
		List<String> filenames = new ArrayList<>();

		try (
				InputStream in = getResourceAsStream(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String resource;

			while ((resource = br.readLine()) != null) {
				filenames.add(resource);
			}
		}

		return filenames;
	}

	private InputStream getResourceAsStream(String resource) {
		final InputStream in
				= getContextClassLoader().getResourceAsStream(resource);

		return in == null ? getClass().getResourceAsStream(resource) : in;
	}

	private ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
