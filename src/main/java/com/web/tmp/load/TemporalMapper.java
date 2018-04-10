package com.web.tmp.load;

import org.apache.log4j.Logger;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.web.common.CommonModel;

public class TemporalMapper implements FieldSetMapper<CommonModel>{

	private static Logger LOG = Logger.getLogger(TemporalMapper.class);
	private String idProceso;
	
	@Override
	public CommonModel mapFieldSet(FieldSet field) throws BindException {
		CommonModel result = new CommonModel();
		if(LOG.isDebugEnabled()){
			LOG.debug("PROCESO: " + idProceso);
		}
		try{
			result.setIdProceso(idProceso);
			result.setCol1(field.readString(0).trim());
			result.setCol2(field.readString(1).trim());
			result.setCol3(field.readString(2).trim());
			result.setCol4(field.readString(3).trim());
			result.setCol5(field.readString(4).trim());
			result.setCol6(field.readString(5).trim());
			result.setCol7(field.readString(6).trim());
			result.setCol8(field.readString(7).trim());
			result.setCol9(field.readString(8).trim());
			result.setCol10(field.readString(9).trim());
			result.setCol11(field.readString(10).trim());
			result.setCol12(field.readString(11).trim());
			result.setCol13(field.readString(12).trim());
			result.setCol14(field.readString(13).trim());
			result.setCol15(field.readString(14).trim());
			result.setCol16(field.readString(15).trim());
			result.setCol17(field.readString(16).trim());
			result.setCol18(field.readString(17).trim());
			result.setCol19(field.readString(18).trim());
			result.setCol20(field.readString(19).trim());
			result.setCol21(field.readString(20).trim());
			result.setCol22(field.readString(21).trim());
			result.setCol23(field.readString(22).trim());
			result.setCol24(field.readString(23).trim());			
			result.setCol25(field.readString(24).trim());			
			result.setCol26(field.readString(25).trim());			
			result.setCol27(field.readString(26).trim());			
			result.setCol28(field.readString(27).trim());			
			result.setCol29(field.readString(28).trim());			
			result.setCol30(field.readString(29).trim());			
			result.setCol31(field.readString(30).trim());			
			result.setCol32(field.readString(31).trim());			
			result.setCol33(field.readString(32).trim());			
			result.setCol34(field.readString(33).trim());			
			result.setCol35(field.readString(34).trim());			
			result.setCol36(field.readString(35).trim());			
			result.setCol37(field.readString(36).trim());			
			result.setCol38(field.readString(37).trim());
			result.setCol39(field.readString(38).trim());
			result.setCol40(field.readString(39).trim());
			result.setCol41(field.readString(40).trim());
			result.setCol42(field.readString(41).trim());
			result.setCol43(field.readString(42).trim());
			result.setCol44(field.readString(43).trim());
			result.setCol45(field.readString(44).trim());
			result.setCol46(field.readString(45).trim());
			result.setCol47(field.readString(46).trim());
			result.setCol48(field.readString(47).trim());
			result.setCol49(field.readString(48).trim());
			result.setCol50(field.readString(49).trim());
		
			
		}catch(ArrayIndexOutOfBoundsException e){
			
			
		}
		
		return result;
	}

	public String getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}

}
