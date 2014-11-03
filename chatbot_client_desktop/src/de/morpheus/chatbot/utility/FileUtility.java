package de.morpheus.chatbot.utility;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileUtility {
	
	/**
	 * 'cuz utilities are static!
	 */
	private FileUtility() {}
	
	public static final Path DEFAULTPATH = Paths.get("");

	public static File[] readFilesFromDirectory(Path path){;
		return path.toFile().listFiles();
	}
	
}
