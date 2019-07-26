package com.muralis.watchingdirectory.core.adapter;

import org.springframework.stereotype.Component;

import com.dvsmedeiros.bce.core.controller.IAdapter;
import com.muralis.watchingdirectory.domain.BooleanParamValue;
import com.muralis.watchingdirectory.domain.Param;

@Component
public class ParamToBooleanAdapter implements IAdapter<Param, Boolean> {

	@Override
	public Boolean adapt(Param source) {
		if (source instanceof BooleanParamValue) {
			BooleanParamValue parsed = (BooleanParamValue) source;
			return parsed.getValue();
		}
		return Boolean.FALSE;
	}
}
