package com.cimr.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;

public class INI4j {
	final LinkedHashMap<String, LinkedHashMap<String, String>> coreMap = new LinkedHashMap();

	String currentSection = null;

	public INI4j(File file) throws FileNotFoundException {
		init(new BufferedReader(new FileReader(file)));
	}

	public INI4j(String path) throws FileNotFoundException {
		init(new BufferedReader(new FileReader(path)));
	}

	public INI4j(ClassPathResource source) throws IOException {
		this(source.getFile());
	}

	void init(BufferedReader bufferedReader) {
		try {
			read(bufferedReader);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("IO Exception:" + e);
		}
	}

	void read(BufferedReader reader) throws IOException {
		String line = null;
		while ((line = reader.readLine()) != null) {
			parseLine(line);
		}
	}

	void parseLine(String line) {
		line = line.trim();

		if (line.matches("^\\#.*$"))
			return;
		if (line.matches("^\\[\\S+\\]$")) {
			String section = line.replaceFirst("^\\[(\\S+)\\]$", "$1");
			addSection(section);
		} else if (line.matches("^\\S+=.*$")) {
			int i = line.indexOf("=");
			String key = line.substring(0, i).trim();
			String value = line.substring(i + 1).trim();
			addKeyValue(this.currentSection, key, value);
		}
	}

	void addKeyValue(String currentSection, String key, String value) {
		if (!this.coreMap.containsKey(currentSection)) {
			return;
		}
		Map<String, String> childMap = (Map) this.coreMap.get(currentSection);
		childMap.put(key, value);
	}

	void addSection(String section) {
		if (!this.coreMap.containsKey(section)) {
			this.currentSection = section;
			LinkedHashMap<String, String> childMap = new LinkedHashMap();
			this.coreMap.put(section, childMap);
		}
	}

	public String get(String section, String key) {
		if (this.coreMap.containsKey(section)) {
			return get(section).containsKey(key) ? (String) get(section).get(key) : null;
		}
		return null;
	}

	public Map<String, String> get(String section) {
		return this.coreMap.containsKey(section) ? (LinkedHashMap) this.coreMap.get(section) : null;
	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> get() {
		return this.coreMap;
	}
}
