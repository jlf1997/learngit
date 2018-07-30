package com.cimr.api.statistics.service.gen;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.comm.configuration.ProjectPropertities;
import com.cimr.api.statistics.model.StaticsicsRealDateOil;
import com.cimr.api.statistics.service.interfaces.RealDateSignalGen;
import com.cimr.boot.utils.TimeUtil;

@Service
public class RealDateSignalOilGen extends RealDateSignalGen{
	
	@Autowired
	private ProjectPropertities projectPropertities;
	
	
	
	

	@Override
	protected void parseFalutList(List<Map<String, Object>> list, List<Map<String, Object>> listun,
			List<Object> finalResult) {
		Map<String,StaticsicsRealDateOil> terMap = new HashMap<>();
		for(Map<String,Object> map:list) {
			String terId = getTerId(map);
			StaticsicsRealDateOil staticsicsRealDateOil = terMap.get(terId);
			if(staticsicsRealDateOil==null) {
				//初始化
				staticsicsRealDateOil = new StaticsicsRealDateOil();
				staticsicsRealDateOil.setTerminalNo(terId);
				staticsicsRealDateOil.setDay(TimeUtil.getDay(getbTime()));
				staticsicsRealDateOil.setYear(TimeUtil.getYear(getbTime()));
				staticsicsRealDateOil.setMonth(TimeUtil.getMonth(getbTime()));
				terMap.put(terId, staticsicsRealDateOil);
			}
			Integer FQ_DAY_OIL = (Integer) map.get("FQ_DAY_OIL");
			Integer KQ_DAY_WORK =(Integer) map.get("KQ_DAY_WORK");
			staticsicsRealDateOil.setFQ_DAY_OIL(staticsicsRealDateOil.getFQ_DAY_OIL()+FQ_DAY_OIL);
			staticsicsRealDateOil.setKQ_DAY_WORK(staticsicsRealDateOil.getKQ_DAY_WORK()+KQ_DAY_WORK);
		}
		
		Iterator<String> iterator = terMap.keySet().iterator();
		while(iterator.hasNext()) {
			StaticsicsRealDateOil staticsicsRealDateOil = terMap.get(iterator.next());
			staticsicsRealDateOil.setLogTime(getbTime());
			finalResult.add(staticsicsRealDateOil);
		}
		
		
	}





	@Override
	protected String getSignal() {
		// TODO Auto-generated method stub
		return projectPropertities.getSingalOil();
	}





	@Override
	protected String getTerId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return map.get("deviceNo").toString();
	}





	

	

	
}
