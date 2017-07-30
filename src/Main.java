import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hildan.fxgson.FxGson;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Daniel on 22/07/2017.
 */
public class Main {

	static File jsonFile;
	private static final Gson gson =
			FxGson.coreBuilder()
					.setPrettyPrinting()
					.disableHtmlEscaping()
					.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
					.create();

	public static void main(String[] args) {
		setJsonFile();
		loadSaveData();
	}

	private static void setJsonFile() {
		try {
			//savedTasksFile = new File(this.getClass().getClassLoader().getResource("tasks.json").toURI());
			//savedTasksStream = getClass().getClassLoader().getResourceAsStream("tasks.json");
			Path folder = Paths.get(System.getProperty("user.home") + "/JsonToSwift");
			System.out.println(folder.toString());

			if (!Files.isDirectory(folder)) {
				Files.createDirectory(folder);
			}
			System.out.println(System.getProperty("os.name").toLowerCase());

			jsonFile = new File(folder + "/questions.json");

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	private static void loadSaveData() {

		if (jsonFile.exists()) {

			try {
				ArrayList<Question> list = readGsonStream(jsonFile);
				int count = 0;
				for (Question question : list) {
					// Output question
					// Desired format: addQuestion(question: "Question", answers: ["answer1", "answer2"], hints: ["hint1", "hint2"])
					String output = "addQuestion(question: \"" + question.question + "\", answers: [";

					for (String answer : question.answers) {
						output += "\"" + answer + "\", ";

					}
					output = output.substring(0, output.length() - 2);

					output += "], hints: [";

					int i = 0;
					for (String hint : question.hint) {
						// Check if the hint is "hint" and prevent it from being used as a hint
						if (hint.equals("hint")) {
							// Don't add it to the output
							// Need to increment i to make sure we don't delete the wrong stuff later if every hint is "hint"
							i += 1;
						} else {
							output += "\"" + hint + "\", ";
						}


					}
					if (i == 0) {
						output = output.substring(0, output.length() - 2);
					}

					output += "])";

					System.out.println(output);

				}

			} catch (IOException e) {
				System.out.println("Error reading file. Please turn it off and on again.");
				System.out.println(e.getLocalizedMessage());
			}

		} else {
			System.out.println("JSON file does not exist!");
			System.out.println("File name: " + jsonFile.getPath());
			// ?
		}
	}

	private static ArrayList<Question> readGsonStream(File file) throws IOException {
		//ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(tasksFile));
		InputStream inputStream = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
		//JsonReader reader = new JsonReader(isr);

		Type listType = new TypeToken<ArrayList<Question>>() {}.getType();

		return gson.fromJson(isr, listType);
	}

}
