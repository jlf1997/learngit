package com.cimr.api.schedule.interfaces.st;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.schedule.model.comm.HttpResult;
import com.netflix.ribbon.proxy.annotation.Hystrix;



@FeignClient(name= "iot-sv-st")
@Hystrix
@RequestMapping("statistics/fault")
public interface FaultStatisticsController {
	

	

	@GetMapping("/page")
	public HttpResult findByPage(
			@RequestParam(value="pageNumber",defaultValue="0") Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="10") Integer pageSize,
			@RequestParam(value="bTime") Long bTime,
			@RequestParam(value="endTime") Long endTime,
			@RequestParam(value="status",required=false) Boolean status,
			@RequestParam(value="code",required=false) String code,
			@RequestParam(value="terid",required=false) String terId
			);

	
	@PostMapping("/day")
	public HttpResult getStatisticsDataDay(
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime,
			@RequestParam(name="codes",required=false) String codes,
			@RequestBody List<String> terIds
			);
	
	@PostMapping("/month")
	public HttpResult getStatisticsDataMonth(
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime,
			@RequestParam(name="codes",required=false) String codes,
			@RequestBody List<String> terIds
			) ;
	

	
	@PostMapping("/year")
	public HttpResult getStatisticsDataYear(
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime,
			@RequestParam(name="codes",required=false) String codes,
			@RequestBody List<String> terIds
			) ;

}
