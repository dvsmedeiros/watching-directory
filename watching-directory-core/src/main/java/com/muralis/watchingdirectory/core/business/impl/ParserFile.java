package com.muralis.watchingdirectory.core.business.impl;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dvsmedeiros.bce.core.controller.INavigationCase;
import com.dvsmedeiros.bce.core.controller.business.IStrategy;
import com.fasterxml.jackson.databind.JsonNode;
import com.muralis.watchingdirectory.core.IWatchFileHandler;
import com.muralis.watchingdirectory.domain.FileExtension;
import com.muralis.watchingdirectory.domain.WatchFile;

@Component
public class ParserFile implements IStrategy<WatchFile> {

	@Resource(name = "parsers")
	private Map<FileExtension, IWatchFileHandler> parsers;

	@Override
	public void process(WatchFile aEntity, INavigationCase<WatchFile> aCase) {

		boolean hasEntity = aEntity != null;
		boolean hasFileExtension = hasEntity && aEntity.getFileExtension() != null;
				
		if (hasFileExtension && parsers.containsKey(aEntity.getFileExtension())) {
			try {
				JsonNode readTree = parsers.get(aEntity.getFileExtension()).parse(aEntity.getFile());
				aEntity.setParsedFile(readTree);
			} catch (IOException e) {
				e.printStackTrace();
				aCase.suspendExecution("Erro ao converter arquivo: " + aEntity.getFullSourcePath());
			}
			return;
		}
		aCase.suspendExecution("Não foi possível ler converter o arquivo. Tipo de arquivo inexistente ou inválido");
	}
}