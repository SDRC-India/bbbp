
package org.sdrc.bbbp.dashboard.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.sdrc.bbbp.dashboard.models.CSRTrendChartModel;
import org.sdrc.bbbp.dashboard.models.CsrAllAreaMapModel;
import org.sdrc.bbbp.dashboard.models.CsrAreaModel;
import org.sdrc.bbbp.dashboard.models.SectorModel;
import org.sdrc.bbbp.dashboard.models.SnapShortDataModel;
import org.sdrc.bbbp.dashboard.models.ThematicViewModel;
import org.sdrc.bbbp.dashboard.models.ValueObject;
import org.sdrc.bbbp.dashboard.service.AggregationService;
import org.sdrc.bbbp.dashboard.service.DashboardService;
import org.sdrc.bbbp.models.AreaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/dashboard")
public class HomeController {
	
	@Autowired
	DashboardService dashboardService;
	
	@Autowired
	AggregationService aggregationService;
	
	@Autowired
	private ConfigurableEnvironment env; 
	
	@GetMapping("/home")
	public String getHome() {
		return "I am home!";
	}
	
	@RequestMapping("/csrMapView")
	@ResponseBody
	public List<CsrAllAreaMapModel>  csrMapView(){
		return dashboardService.csrMapView();
	}
	
	@RequestMapping("/csrTrendView")
	@ResponseBody
	public List<CSRTrendChartModel>  csrTrendView(@RequestParam("areaId")Integer areaId,@RequestParam("indicatorId")Integer indicatorId){
		return dashboardService.csrTrendChartModel(areaId,indicatorId);
	}
	
//	@RequestMapping("/uploadExcel")
//	@ResponseBody
//	public String  uploadUtdata()throws Exception{
//		return dashboardService.uploadUtdata();
//	}
	
	@RequestMapping("/getSectorsSubsectorIndecators")
	@ResponseBody
	public List<SectorModel>  getAllSectorsSubsctorsIndicators()throws Exception{
		return dashboardService.getAllSectorsSubsctorsIndicators();
	}
	
	@RequestMapping("/thematicView")
	@ResponseBody
	public List<CsrAllAreaMapModel>  thimaticView(@RequestParam("indicatorId")Integer indicatorId){
		return dashboardService.thimaticView(indicatorId);
	}
	
	
	@RequestMapping("/getAllStateData")
	@ResponseBody
	public List<CsrAreaModel>  getAllStateData(){
		return dashboardService.getAllStateData();
	}
	
	@RequestMapping("/getAllAreaLevels")
	@ResponseBody
	public List<ValueObject> getAllAreaLevels(){
		return dashboardService.getAllAreaLevels();
	}
	
	@RequestMapping("/getAllArea")
	@ResponseBody
	public Map<String, List<AreaModel>> getAllArea(){
		return dashboardService.getAllArea();
	}
	
	@RequestMapping("/getSnapChatData")
	@ResponseBody
	public SnapShortDataModel getSnapChatData(@RequestParam("areaLevelId") Integer areaLevelId, @RequestParam("areaId") Integer areaId){
		return dashboardService.getSnapChatData(areaLevelId, areaId);
	}
	
	@RequestMapping("/aggregateData")
	public String aggregateData(){
		aggregationService.aggregateAllData();
		return "Success";
	}
	
	
	@RequestMapping("/aggregateDataQuarterly")
	public String aggregateDataQuarterly(@RequestParam("tpId") Integer tpId, @RequestParam("yearId") Integer yearId,
			@RequestParam("period") Integer period){
		aggregationService.aggregateDataQuarterly(tpId, yearId, period);
		return "Success";
	}
	
	@RequestMapping("/getAreaList")
	public Map<String, List<AreaModel>> getAreaList(){
		return dashboardService.getAreaList();
	}
	
	@RequestMapping("/getThematicView")
	public ThematicViewModel getThematicView(@RequestParam("indicatorId") Integer indicatorId, 
														@RequestParam("timePeriodId") Integer timePeriodId,
														@RequestParam("areaId") Integer areaId){
		return dashboardService.getThematicView(indicatorId, timePeriodId, areaId);
	}
	
	@RequestMapping("/getYearList")
	public List<CsrAllAreaMapModel> getYearList(){
		return dashboardService.getYearList();
	}

	@Scheduled(cron="0 0 3 1/1 * ?")
	@RequestMapping("/deleteBBBPTempFolder")
	public String deleteBBBPTempFolder() throws IOException{
		FileUtils.deleteDirectory(new File(env.getProperty("output.path.pdf")));
		FileUtils.deleteDirectory(new File(env.getProperty("output.path.excel")));
		return "success";
	}
	
    @RequestMapping(value = "/downloadTemplate", method = RequestMethod.POST)
	public ResponseEntity<InputStreamResource> downLoad(HttpServletResponse response,
			@RequestParam(value = "inline", required = false) Boolean inline) throws Exception {
		try {
			
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			URL url = loader.getResource("templates/");
			String path = url.getPath().replaceAll("%20", " ");
			File files[] = new File(path).listFiles();
			
			if (files == null) {
				throw new RuntimeException("No file found in path " + path);
			}
			
			HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.add("Content-Disposition", "attachment; filename=" + files[0].getName());
			InputStreamResource isr = new InputStreamResource(new FileInputStream(files[0]));

			return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
    
    @PostMapping(value = "/uploadTemplate")
    public String uploadTemplate(@RequestParam(value = "templateFile", required=false) MultipartFile uploadCSRFile) throws Exception {
		return dashboardService.uploadUtdata(uploadCSRFile);
 } 
}
