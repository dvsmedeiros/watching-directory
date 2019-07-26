package com.muralis.watchingdirectory;

import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Strings;
import com.muralis.watchingdirectory.core.impl.WatchDirectory;
import com.muralis.watchingdirectory.domain.FileExtension;
import com.muralis.watchingdirectory.domain.WatchConfiguration;

@Configuration
@ComponentScan({ "com.dvsmedeiros", "com.muralis" })
@EnableAutoConfiguration
@SpringBootApplication
public class WatchingDirectoryApplication {

	@Value("${watch.directory.source:./source}")
	private String source;

	@Value("${watch.directory.output:./output}")
	private String output;

	@Value("${watch.directory.parse.output.extension:JSON}")
	private String outputExtention;

	@Value("${watch.directory.events.kind:CREATE,MODIFY,OVERFLOW}")
	private String[] eventsKind;

	@Value("${watch.directory.file.supported.extensions:XML,JSON}")
	private String[] supportedExtentions;

	@Bean(name = "watchConfiguration")
	public WatchConfiguration getWatchConfiguration() {
		
		
		Set<String> events = Arrays.asList(eventsKind)
				.stream()
				.filter(kind -> !Strings.isNullOrEmpty(kind)).map(kind -> {
					if ("ENTRY_".concat(kind).toUpperCase().equals(StandardWatchEventKinds.ENTRY_CREATE.name())) {
						return StandardWatchEventKinds.ENTRY_CREATE.name();
					}
					if ("ENTRY_".concat(kind).toUpperCase().equals(StandardWatchEventKinds.ENTRY_MODIFY.name())) {
						return StandardWatchEventKinds.ENTRY_MODIFY.name();
					}
					if (kind.toUpperCase().equals(StandardWatchEventKinds.OVERFLOW.name())) {
						return StandardWatchEventKinds.OVERFLOW.name();
					}
					return null;
				})
				.filter(kind -> !Strings.isNullOrEmpty(kind))
				.collect(Collectors.toSet());
		
		FileExtension extentsion = FileExtension.valueOf(outputExtention) != null 
				? FileExtension.valueOf(outputExtention) 
						: FileExtension.XML;
				
		return new WatchConfiguration(Paths.get(source), Paths.get(output), events, new HashSet<String>(Arrays.asList(supportedExtentions)), extentsion);
	}

	@Bean(name = "watchDirectory")
	@Autowired
	public WatchDirectory getWatchDirectory() {
		return new WatchDirectory();
	}

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(WatchingDirectoryApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);

	}

}
