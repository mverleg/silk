package nl.markv.silk.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.json.JSONObject;
import org.json.JSONTokener;

import nl.markv.silk.generate.Generate;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

public class Examples {

	public static final String SILK_EXAMPLE_DIR = "/silk/example";
	private List<JSONObject> cachedJsonExamples;

	@Nonnull
	public List<JSONObject> jsons() {
		if (cachedJsonExamples != null) {
			return cachedJsonExamples;
		}
		cachedJsonExamples = new ArrayList<>();
		for (URL path : getResourceFiles()) {
			try {
				JSONObject example = new JSONObject(new JSONTokener(path.openStream()));
				cachedJsonExamples.add(example);
			} catch (IOException ex) {
				throw new IllegalStateException(ex);
			}
		}
		isTrue(!cachedJsonExamples.isEmpty());
		return cachedJsonExamples;
	}

	@Nonnull
	public List<URL> getResourceFiles() {
		List<URL> filenames = new ArrayList<>();

		try (InputStream in = getResourceAsStream();
			 BufferedReader br = new BufferedReader(new InputStreamReader(in))) {

			String resource;

			while ((resource = br.readLine()) != null) {
				String path = Paths.get(SILK_EXAMPLE_DIR, resource).toString();
				URL url = notNull(
						Examples.class.getResource(path),
						"json example not found: '" + path + "'"
				);
				filenames.add(url);
			}
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}

		return filenames;
	}

	private InputStream getResourceAsStream() {
		final InputStream in = getContextClassLoader().getResourceAsStream(SILK_EXAMPLE_DIR);
		return in == null ? getClass().getResourceAsStream(SILK_EXAMPLE_DIR) : in;
	}

	private ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
