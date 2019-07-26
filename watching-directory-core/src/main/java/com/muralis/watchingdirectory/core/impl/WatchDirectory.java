package com.muralis.watchingdirectory.core.impl;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.dvsmedeiros.bce.core.controller.INavigator;
import com.dvsmedeiros.bce.core.controller.impl.BusinessCaseBuilder;
import com.dvsmedeiros.bce.domain.ApplicationEntity;
import com.google.common.io.Files;
import com.muralis.watchingdirectory.domain.WatchConfiguration;
import com.muralis.watchingdirectory.domain.WatchFile;

@Component
public class WatchDirectory extends ApplicationEntity {

	@Autowired
	@Qualifier("navigator")
	private INavigator<WatchFile> navigator;

	@Autowired
	@Qualifier("businessCaseBuilder")
	private BusinessCaseBuilder<WatchFile> businessCaseBuilder;
	
	@Autowired
	@Qualifier("watchConfiguration")
	private WatchConfiguration configuration;
	
	
	@PostConstruct
	public void init() {
		
		WatchService watchService;

		try {
			watchService = FileSystems.getDefault().newWatchService();
			configuration.getSource()
				.register(watchService, 
						StandardWatchEventKinds.ENTRY_CREATE,						
						StandardWatchEventKinds.ENTRY_MODIFY,
						StandardWatchEventKinds.OVERFLOW);

			WatchKey key;
			while ((key = watchService.take()) != null) {
				for (WatchEvent<?> event : key.pollEvents()) {
					
					String extension = Files.getFileExtension(event.context().toString()).replace(".", "").trim().toUpperCase();
					
					boolean containEventKind = configuration.getEventKinds().contains(event.kind().name());					
					boolean isSupportedExtension = configuration.getSupportedExtensions().contains(extension);
					
					if(containEventKind && isSupportedExtension) {
						WatchFile watchFile = new WatchFile(event.context().toString(), configuration);
						navigator.run(watchFile, businessCaseBuilder.withName("HANDLE_WATCHED_FILE"));
						getLogger(WatchDirectory.class).info("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");						
					}										
				}
				key.reset();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
