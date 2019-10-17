package org.sdrc.bbbp.dashboard.service;

import java.util.List;
import java.util.Map;

import org.sdrc.bbbp.dashboard.models.CSRTrendChartModel;
import org.sdrc.bbbp.dashboard.models.CsrAllAreaMapModel;
import org.sdrc.bbbp.dashboard.models.CsrAreaModel;
import org.sdrc.bbbp.dashboard.models.SectorModel;
import org.sdrc.bbbp.dashboard.models.SnapShortDataModel;
import org.sdrc.bbbp.dashboard.models.ThematicViewModel;
import org.sdrc.bbbp.dashboard.models.ValueObject;
import org.sdrc.bbbp.models.AreaModel;
import org.springframework.web.multipart.MultipartFile;

public interface DashboardService {
	
	List<CSRTrendChartModel> csrTrendChartModel(int areaId,int indicatorId);
	
//	String uploadUtdata()throws Exception;
	
	List<CsrAllAreaMapModel> csrMapView();
	
	List<SectorModel> getAllSectorsSubsctorsIndicators();
	
	
	List<CsrAllAreaMapModel> thimaticView(Integer indicatorNid);
	
	List<CsrAreaModel>  getAllStateData();
	
	List<ValueObject> getAllAreaLevels();
	
	Map<String, List<AreaModel>> getAllArea();
	
	SnapShortDataModel getSnapChatData(Integer areaLevelId, Integer areaId);

	Map<String, List<AreaModel>> getAreaList();

	ThematicViewModel getThematicView(Integer indicatorId, Integer sector, Integer subSectorId);

	List<CsrAllAreaMapModel> getYearList();

	String uploadUtdata(MultipartFile uploadCSRFile) throws Exception;
	
}
