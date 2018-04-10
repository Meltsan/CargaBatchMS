package com.web.sucursal.model;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class SucursalMapper implements FieldSetMapper<SucursalModel>{

	@Override
	public SucursalModel mapFieldSet(FieldSet field) throws BindException {
		SucursalModel res = new SucursalModel();
		
			res.setIdSucursal(field.readString(0));
			res.setSucursal(field.readString(1));
			
		return res;
	}

}
