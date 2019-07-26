package com.muralis.watchingdirectory.core.business;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dvsmedeiros.bce.core.controller.impl.Navigation;
import com.dvsmedeiros.bce.core.controller.impl.NavigationBuilder;
import com.muralis.watchingdirectory.core.IWatchFileHandler;
import com.muralis.watchingdirectory.core.business.impl.ParserFile;
import com.muralis.watchingdirectory.core.business.impl.ReadFile;
import com.muralis.watchingdirectory.core.business.impl.WriteFile;
import com.muralis.watchingdirectory.domain.FileExtension;
import com.muralis.watchingdirectory.domain.WatchFile;

@Configuration
public class WatchingDirectoryNavigation {

	@Autowired
	@Qualifier("xmlHandler")
	private IWatchFileHandler xmlParser;

	@Autowired
	@Qualifier("jsonHandler")
	private IWatchFileHandler jsonParser;

	@Autowired
	private ReadFile readFile;

	@Autowired
	private ParserFile parserFile;

	@Autowired
	private WriteFile writeFile;
	
	@Bean(name = "parsers")
	public Map<FileExtension, IWatchFileHandler> getParsers() {
		ConcurrentHashMap<FileExtension, IWatchFileHandler> parsers = new ConcurrentHashMap<>();
		parsers.put(FileExtension.XML, xmlParser);
		parsers.put(FileExtension.JSON, jsonParser);
		return parsers;
	}

	@Bean(name = "HANDLE_WATCHED_FILE")
	public Navigation<WatchFile> generateReport() {
		return new NavigationBuilder<WatchFile>()
				.next(readFile)
				.next(parserFile)
				.next(writeFile).build();
	}
		
}