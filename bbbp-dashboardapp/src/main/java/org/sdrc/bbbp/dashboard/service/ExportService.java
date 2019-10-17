package org.sdrc.bbbp.dashboard.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdrc.bbbp.dashboard.models.SnapSortModel;
import org.sdrc.bbbp.dashboard.models.ThematicViewImageModel;

public interface ExportService {

	String exportThematicViewPDF(ThematicViewImageModel thematicViewImageModel, HttpServletResponse response, HttpServletRequest request);

	String exportThematicViewExcel(ThematicViewImageModel thematicViewImageModel, HttpServletResponse response, HttpServletRequest request);

	String snapShotViewPDF(SnapSortModel snapSortModel, HttpServletResponse response, HttpServletRequest request);

	String snapShotViewExcel(SnapSortModel snapSortModel, HttpServletResponse response, HttpServletRequest request);

}
