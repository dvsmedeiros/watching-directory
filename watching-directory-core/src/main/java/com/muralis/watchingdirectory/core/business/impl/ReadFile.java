package com.muralis.watchingdirectory.core.business.impl;

import org.springframework.stereotype.Component;

import com.dvsmedeiros.bce.core.controller.INavigationCase;
import com.dvsmedeiros.bce.core.controller.business.IStrategy;
import com.muralis.watchingdirectory.domain.WatchFile;

@Component
public class ReadFile implements IStrategy<WatchFile> {
	
	@Override
	public void process(WatchFile aEntity, INavigationCase<WatchFile> aCase) {

		if (aEntity != null && aEntity.getFullSourcePath() != null) {
			aEntity.setFile(aEntity.getFullSourcePath().toFile());
			return;			
		}		
		aCase.suspendExecution("Não foi possível ler arquivo. Caminho inexistente ou inválido");
	}
}