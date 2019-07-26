package com.muralis.watchingdirectory.core.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.muralis.watchingdirectory.core.IWatchFileHandler;
import com.muralis.watchingdirectory.domain.FileExtension;

@Component
public class JsonHandler implements IWatchFileHandler {

	@Override
	public JsonNode parse(File file) throws JsonProcessingException, IOException {
		return jsonMapper.readTree(file);
	}

	@Override
	public void write(JsonNode file, Path output, String fileName, FileExtension extension) throws JsonProcessingException, IOException {
		output = Paths.get(output.toString().concat(File.separator).concat(fileName).concat(extension.getExtention()));
		jsonMapper.writeValue(output.toFile(), file);		
	}

}