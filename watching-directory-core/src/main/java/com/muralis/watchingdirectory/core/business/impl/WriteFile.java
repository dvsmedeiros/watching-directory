package com.muralis.watchingdirectory.core.business.impl;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dvsmedeiros.bce.core.controller.INavigationCase;
import com.dvsmedeiros.bce.core.controller.business.IStrategy;
import com.muralis.watchingdirectory.core.IWatchFileHandler;
import com.muralis.watchingdirectory.domain.FileExtension;
import com.muralis.watchingdirectory.domain.WatchFile;

@Component
public class WriteFile implements IStrategy<WatchFile> {

	@Resource(name = "parsers")
	private Map<FileExtension, IWatchFileHandler> parsers;

	@Override
	public void process(WatchFile aEntity, INavigationCase<WatchFile> aCase) {

		boolean hasEntity = aEntity != null;
		boolean hasConfig = hasEntity && aEntity.getConfiguration() != null;
		boolean hasOutputTo = hasConfig && aEntity.getConfiguration().getOutputTo() != null;
		boolean hasOutput = hasConfig && aEntity.getConfiguration().getOutput() != null;
		
		if (hasOutput && hasOutputTo && parsers.containsKey(aEntity.getConfiguration().getOutputTo())) {
			try {
				
				parsers.get(aEntity.getConfiguration().getOutputTo())
				.write(
					aEntity.getParsedFile(), 
					aEntity.getConfiguration().getOutput(), 
					aEntity.getFileNameWithoutExtension(), 
					aEntity.getConfiguration().getOutputTo());
				
			} catch (IOException e) {
				e.printStackTrace();
				aCase.suspendExecution("Erro ao salvar arquivo: " + aEntity.getFullOutputPath());
			}
			return;
		}
		aCase.suspendExecution("Não foi possível ler salvar o arquivo. Tipo de arquivo inexistente ou inválido");
	}
}