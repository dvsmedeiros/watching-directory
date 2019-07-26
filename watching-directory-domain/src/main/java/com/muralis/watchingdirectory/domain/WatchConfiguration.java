package com.muralis.watchingdirectory.domain;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class WatchConfiguration {

	private Path source;
	private Path output;	
	private FileExtension outputTo;
	private Set<String> eventKinds;
	private Set<String> supportedExtensions;

	public WatchConfiguration() {
	}
		
	public WatchConfiguration(Path source, Path output, Set<String> events, Set<String> extension, FileExtension outputTo) {
		this.source = source;
		this.output = output;		
		this.outputTo = outputTo;
		this.eventKinds = events != null ? events : new HashSet<String>();
		this.supportedExtensions = extension != null ? extension : new HashSet<String>();
	}

	public WatchConfiguration(Path source) {
		this.source = source;
		this.output = source;
		this.eventKinds = new HashSet<String>();
	}

	public Path getSource() {
		return source;
	}

	public void setSource(Path source) {
		this.source = source;
	}

	public Path getOutput() {
		return output;
	}

	public void setOutput(Path output) {
		this.output = output;
	}

	public Set<String> getEventKinds() {
		return new HashSet<String>(eventKinds);
	}
	
	public Set<String> getSupportedExtensions() {
		return new HashSet<String>(supportedExtensions);
	}
	
	public FileExtension getOutputTo() {
		return outputTo;
	}

}
