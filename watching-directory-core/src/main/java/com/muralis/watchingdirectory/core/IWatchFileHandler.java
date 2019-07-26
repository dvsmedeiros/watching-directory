package com.muralis.watchingdirectory.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.muralis.watchingdirectory.domain.FileExtension;

public interface IWatchFileHandler {
		
	static final ObjectMapper jsonMapper = new ObjectMapper();
	static final XmlMapper xmlMapper = new XmlMapper();
	
	public JsonNode parse(File file) throws JsonProcessingException, IOException;
	public void write(JsonNode file, Path output, String fileName, FileExtension extention) throws JsonProcessingException, IOException;
	
}