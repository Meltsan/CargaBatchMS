package com.web.tmp.procesor;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.web.annotation.ValidateMasiveLoad;
import com.web.common.CommonModel;

public class FileProcesor implements ItemProcessor<CommonModel, CommonModel>{

	private static final Logger log = Logger.getLogger(FileProcesor.class);
	
	private String idLayout;
	private String schema;
	static int rowNumber;
	ValidateMasiveLoad validateLoad;
	
	@Override
	public CommonModel process(CommonModel model) throws Exception {

		log.debug(String.format("REGISTRO [%d]", rowNumber));
		
		String msjValidate = validateLoad.validateLoadMasive(model, idLayout, schema);
		model.setRowNumber(rowNumber);
		model.setDescError(msjValidate);
		rowNumber++;
		
		return model;
		
	}

	public ValidateMasiveLoad getValidateLoad() {
		return validateLoad;
	}

	public void setValidateLoad(ValidateMasiveLoad validateLoad) {
		this.validateLoad = validateLoad;
	}

	public String getIdLayout() {
		return idLayout;
	}

	public void setIdLayout(String idLayout) {
		this.idLayout = idLayout;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

}