/**
 * 
 */
package de.mat2095.my_slither;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author dick
 *
 */
public enum ConfigMan {
    INSTANCE;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private final static String s_myslither	= "myslither";
	private final static String	s_jsonFile	= "myslither.json";

	public <T> void saveConfig(T configuration) {
		File file = getConfigFile();
		PrintWriter out;
		try {
			if (!file.exists()) {
				//file.getParentFile().mkdirs();
				file.createNewFile();
			}
			out = new PrintWriter(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.print(GSON.toJson(configuration));
		out.close();
	}

	public <T> T readConfig(Class<T> configClass) {
		File file = getConfigFile();
		try {
			if (file.exists()) {
				return GSON.fromJson(new FileReader(file), configClass);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		try {
			return configClass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private File getConfigFile() {
		String actualFilename;
		String s = System.getProperty("user.home") + File.separator + ".config";
		File file = new File(s);
		if (file.exists()) {
			s += File.separator + s_myslither;
			file = new File(s);
			if (!file.exists()) {
				file.mkdir();
			}
			s += File.separator + s_jsonFile;
			actualFilename = s;
		} else {
			s = System.getProperty("user.home") + File.separator + s_jsonFile;
			actualFilename = s;
		}
		return(new File(actualFilename));
	}
}
