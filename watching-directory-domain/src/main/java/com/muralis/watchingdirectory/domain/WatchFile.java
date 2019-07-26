package com.muralis.watchingdirectory.domain;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dvsmedeiros.bce.domain.ApplicationEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.Files;

@Component
public class WatchFile extends ApplicationEntity {

	
	private File file;
	private String fileName;	
	private JsonNode parsedFile;
	@Autowired
	private WatchConfiguration configuration;

	public WatchFile(String fileName, WatchConfiguration configuration) {		
		this.configuration = configuration;
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public String getFileName() {
		return fileName;
	}

	public FileExtension getFileExtension() {
		return FileExtension.valueOf(Files.getFileExtension(fileName).toUpperCase());
	}

	public JsonNode getParsedFile() {
		return parsedFile;
	}

	public WatchConfiguration getConfiguration() {
		return configuration;
	}
	
	public void setFile(File file) {
		this.file = file;
	}

	public void setParsedFile(JsonNode parsedFile) {
		this.parsedFile = parsedFile;
	}
	
	public Path getFullSourcePath() {
		return Paths.get(new StringBuilder(configuration.getSource().toString())
				.append(File.separator)
				.append(fileName)				
				.toString());
	}
	
	public Path getFullOutputPath() {
		return Paths.get(new StringBuilder(configuration.getOutput().toString())
				.append(File.separator)
				.append(fileName)				
				.toString());
	}
	
	public String getFileNameWithoutExtension() {		
		return fileName.replace(getFileExtension().getExtention(), "");
	}

}
