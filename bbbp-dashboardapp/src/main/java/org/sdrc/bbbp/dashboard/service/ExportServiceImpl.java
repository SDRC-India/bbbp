package org.sdrc.bbbp.dashboard.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sdrc.bbbp.dashboard.domain.IndicatorQuestionMapping;
import org.sdrc.bbbp.dashboard.domain.UtData;
import org.sdrc.bbbp.dashboard.domain.UtTimeperiod;
import org.sdrc.bbbp.dashboard.models.GroupIndicatorModel;
import org.sdrc.bbbp.dashboard.models.SnapSortModel;
import org.sdrc.bbbp.dashboard.models.ThematicViewImageModel;
import org.sdrc.bbbp.dashboard.repository.IndicatorGroupRepository;
import org.sdrc.bbbp.dashboard.repository.IndicatorQuestionMappingRepository;
import org.sdrc.bbbp.dashboard.repository.UtDataReporsitory;
import org.sdrc.bbbp.dashboard.repository.UtTimeperiodRepository;
import org.sdrc.bbbp.dashboard.util.BBBPUtil;
import org.sdrc.bbbp.dashboard.util.HeaderFooter;
import org.sdrc.bbbp.domain.Area;
import org.sdrc.bbbp.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ExportServiceImpl implements ExportService {
	
	@Autowired
	private ConfigurableEnvironment env; 
	
	@Autowired
	private UtDataReporsitory utDataReporsitory;
	
	@Autowired
	private AreaRepository areaRepository;
	
	@Autowired
	private IndicatorGroupRepository indicatorGroupRepository;

	@Autowired
	private UtTimeperiodRepository utTimeperiodRepository;
	
	@Autowired
	private IndicatorQuestionMappingRepository indicatorQuestionMappingRepository;
	
	String date = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
	
	@Override
	public String exportThematicViewPDF(ThematicViewImageModel thematicViewImageModel, HttpServletResponse response, HttpServletRequest request) {
		
		String outputPathPdf=env.getProperty("output.path.pdf");
		try {
			File file = new File(outputPathPdf);
			if(!file.exists()){
				file.mkdirs();
			}
			outputPathPdf = outputPathPdf +"_"+date+".pdf";
			
//			Rectangle layout = new Rectangle(PageSize.A4);
//		    layout.setBackgroundColor(new BaseColor(248,236,211));
		    
		    Document document = new Document(PageSize.A4);//
		    Font font = new Font(FontFamily.HELVETICA, 12, Font.NORMAL, GrayColor.BLACK);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPathPdf));
			
			
			String uri = request.getRequestURI();
			String url = request.getRequestURL().toString();
			url = url.replaceFirst(uri, "");
			
			HeaderFooter headerFooter = new HeaderFooter(url,"portrait");
			writer.setPageEvent(headerFooter);
			
			Paragraph areaParagraph = new Paragraph();
			areaParagraph.setAlignment(Element.ALIGN_LEFT);
			areaParagraph.setSpacingBefore(35);
			areaParagraph.setSpacingAfter(15);
			Chunk areaChunk = null;
			String header = "Sector : " + thematicViewImageModel.getSectorName();
			
			if(thematicViewImageModel.getSubSectorName()!=null) {
				header += "\nSub sector : " + thematicViewImageModel.getSubSectorName();
			}
			if(thematicViewImageModel.getIndicatorName() != null) {
				header += "\nIndicator : " + thematicViewImageModel.getIndicatorName();
			} 
			if(thematicViewImageModel.getTimePeriod() != null) {
				header += "\nTime Period : " + thematicViewImageModel.getTimePeriod();
			}
			
			areaChunk = new Chunk(header, font);
				
			areaParagraph.add(areaChunk);
			
			document.open();
			document.add(areaParagraph);
			
			byte [] indiaMapImageBytes =Base64.decodeBase64( ((String) thematicViewImageModel.getIndiaMapImage()).split(",")[1]);
			Image svgImage = Image.getInstance(indiaMapImageBytes);
			svgImage.setAlignment(Element.ALIGN_CENTER);
			svgImage.setBorder(Rectangle.BOX);
			svgImage.setAbsolutePosition(40, 250);
			svgImage.scalePercent(65);
			
			document.add(svgImage);
			if(thematicViewImageModel.getIndiaMapImageLegend()!= null) {
				byte [] indiaMapImageLegendBytes =Base64.decodeBase64( ((String) thematicViewImageModel.getIndiaMapImageLegend()).split(",")[1]);
				Image svgImageLegend = Image.getInstance(indiaMapImageLegendBytes);
				svgImageLegend.setAlignment(Element.ALIGN_CENTER);
				svgImageLegend.setBorder(Rectangle.BOX);
				svgImageLegend.setAbsolutePosition(400, 130);
				svgImageLegend.scalePercent(60);
				
				document.add(svgImageLegend);
			}
			if(thematicViewImageModel.getIndicatorId()!=null) {
			Integer parentAreaId = 1; //For State Data Sending parent 1
			Integer indicatorId = thematicViewImageModel.getIndicatorId();
			Integer timePeriodId = thematicViewImageModel.getTimePeriodId();
			
			List<Object[]> utDatas = utDataReporsitory.getUtDatasForAllState(indicatorId, timePeriodId, parentAreaId);
			Map<String, String> mapOfData = utDatas.stream().collect(Collectors.toMap(k->k[2].toString(), v->(v[1]==null?"N/A":v[1].toString()))); 
			
			List<Area> allState = areaRepository.findByParentAreaIdOrderByAreaName(parentAreaId);
			
			PdfPTable page1Table1 = new PdfPTable(2);
			page1Table1.setTotalWidth(295f);
			PdfPCell pdfPCell1 = null;
						
			pdfPCell1 = new PdfPCell(new Phrase("Area Name", new Font(FontFamily.HELVETICA, 9, Font.NORMAL, GrayColor.BLACK)));
			pdfPCell1.setBackgroundColor(new BaseColor(92, 127, 168));
			page1Table1.addCell(pdfPCell1);
			pdfPCell1 = new PdfPCell(new Phrase("Value", new Font(FontFamily.HELVETICA, 9, Font.NORMAL, GrayColor.BLACK)));
			pdfPCell1.setBackgroundColor(new BaseColor(92, 127, 168));
			page1Table1.addCell(pdfPCell1);
			
			int colorIndex = 0;
			for (Area area : allState) {
				
				pdfPCell1 = new PdfPCell(new Phrase(area.getAreaName(), font));
				pdfPCell1.setMinimumHeight(8f);
				if(colorIndex%2==0) {
					pdfPCell1.setBackgroundColor(new BaseColor(227, 231, 235));
				}
				page1Table1.addCell(pdfPCell1);

				pdfPCell1 = new PdfPCell(new Phrase((mapOfData.get(area.getAreaId().toString())==null?"N/A":mapOfData.get(area.getAreaId().toString())), font));
				pdfPCell1.setMinimumHeight(8f);
				if(colorIndex%2==0) {
					pdfPCell1.setBackgroundColor(new BaseColor(227, 231, 235));
				}
				page1Table1.addCell(pdfPCell1);

				colorIndex++;
			}
			
			document.newPage();
			document.add(Chunk.NEWLINE);
			document.add(page1Table1);
			if(thematicViewImageModel.getAreaId()!=null && thematicViewImageModel.getDistrictImage()!=null) {
			document.newPage();
			Paragraph areaNamePara = new Paragraph();
			areaNamePara.setAlignment(Element.ALIGN_LEFT);
			areaNamePara.setSpacingBefore(20);
			areaNamePara.setSpacingAfter(20);
			Chunk areaNameChunk = new Chunk("State : " + thematicViewImageModel.getAreaName()
											+"\nSector : " + thematicViewImageModel.getSectorName()
											+"\nSub sector : " + thematicViewImageModel.getSubSectorName()
											+"\nIndicator : " + thematicViewImageModel.getIndicatorName()
											+"\nTime Period : " + thematicViewImageModel.getTimePeriod() , font);
			areaNamePara.add(areaNameChunk);
			document.add(areaNamePara);
			if(thematicViewImageModel.getDistrictImage()!=null) {
				byte [] districtImageBytes =Base64.decodeBase64( ((String) thematicViewImageModel.getDistrictImage()).split(",")[1]);
				Image svgImageDistrict = Image.getInstance(districtImageBytes);
				svgImageDistrict.setAlignment(Element.ALIGN_CENTER);
				svgImageDistrict.setBorder(Rectangle.BOX);
				System.out.println(svgImageDistrict.getHeight());
				if(svgImageDistrict.getHeight()<=560) {
					svgImageDistrict.setAbsolutePosition(60, 300);
					svgImageDistrict.scalePercent(60);
				} else if(svgImageDistrict.getHeight()>560 && svgImageDistrict.getHeight()<=1000) {
					svgImageDistrict.setAbsolutePosition(60, 140);
					svgImageDistrict.scalePercent(55);
				} else if(svgImageDistrict.getHeight()>1000 && svgImageDistrict.getHeight()<=1500) {
					svgImageDistrict.setAbsolutePosition(60, 10);
					svgImageDistrict.scalePercent(45);
				} 
				
				document.add(svgImageDistrict);
			}
			PdfPTable page1Table2 = new PdfPTable(2);
			page1Table2.setHeaderRows(1);
//			page1Table2.setTotalWidth(295f);
			PdfPCell pdfPCell2 = null;
						
			pdfPCell2 = new PdfPCell(new Phrase("Area	 Name", new Font(FontFamily.HELVETICA, 9, Font.NORMAL, GrayColor.BLACK)));
			pdfPCell2.setBackgroundColor(new BaseColor(92, 127, 168));
			
			page1Table2.addCell(pdfPCell2);
			pdfPCell2 = new PdfPCell(new Phrase("Value", new Font(FontFamily.HELVETICA, 9, Font.NORMAL, GrayColor.BLACK)));
			pdfPCell2.setBackgroundColor(new BaseColor(92, 127, 168));
			page1Table2.addCell(pdfPCell2);
			
//			if(thematicViewImageModel.getAreaId()!=null) {
				int colorIndex2 = 0;
				
				List<Area> areaList = areaRepository.findByParentAreaIdOrderByAreaName(thematicViewImageModel.getAreaId());
				List<Integer> childAreaList = areaList.stream().map(Area::getAreaId).collect(Collectors.toList());
				
				List<UtData> utDataList = utDataReporsitory.getThematicViewData(indicatorId, timePeriodId, childAreaList);
				Map<Integer, String> utDataForSelectedState = new LinkedHashMap<>();
				for (UtData utData : utDataList) {
					utDataForSelectedState.put(utData.getArea_NId().getAreaId(), utData.getData_Value());
				}
				int gotoNextPage = 0;		
				for (Area area : areaList) {
					pdfPCell2 = new PdfPCell(new Phrase(area.getAreaName(), font));
					
					pdfPCell2.setMinimumHeight(8f);
					if(colorIndex2%2==0) {
						pdfPCell2.setBackgroundColor(new BaseColor(227, 231, 235));
					}
					page1Table2.addCell(pdfPCell2);
					pdfPCell2 = new PdfPCell(new Phrase(!utDataForSelectedState.containsKey(area.getAreaId()) ? "N/A" : utDataForSelectedState.get(area.getAreaId()), font));
					
					pdfPCell2.setMinimumHeight(8f);
					if(colorIndex2%2==0) {
						pdfPCell2.setBackgroundColor(new BaseColor(227, 231, 235));
					}
					page1Table2.addCell(pdfPCell2);
	
					colorIndex2++;
					gotoNextPage++;
					if(gotoNextPage>43) {
						document.newPage();
						document.add(Chunk.NEWLINE);
						document.add(page1Table2);
						page1Table2 = null;
						page1Table2 = new PdfPTable(2);
						gotoNextPage=0;
					}
				}
				document.newPage();
				document.add(Chunk.NEWLINE);
				document.add(page1Table2);
			}
			}
 			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return outputPathPdf;
	}
	
	
	@Override
	public String exportThematicViewExcel(ThematicViewImageModel thematicViewImageModel, HttpServletResponse response, HttpServletRequest request) {
		String outputPathExcel=env.getProperty("output.path.excel");
		try {
			File file = new File(outputPathExcel);
			if (!file.exists()) {
				file.mkdirs();
			}
			outputPathExcel = outputPathExcel + "_" + date + ".xlsx";

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("BBBP");
			XSSFRow row = null;
			XSSFCell cell = null;
			int rowNum = 0;
			int cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);

			BufferedImage bImage = ImageIO.read(new URL("classpath:images/header.jpg"));
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(bImage, "jpg", bos);

			byte[] headerImgBytes = bos.toByteArray();
			rowNum = insertimage(rowNum, headerImgBytes, workbook, sheet);

			rowNum += 3;
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue("Sector : " + thematicViewImageModel.getSectorName());
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, cellNum, cellNum + 12));
			if (thematicViewImageModel.getSubSectorName() != null) {
				rowNum++;
				cellNum = 0;
				row = sheet.createRow(rowNum);
				cell = row.createCell(cellNum);
				cell.setCellValue("Sub sector : " + thematicViewImageModel.getSubSectorName());
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, cellNum, cellNum + 12));
			}
			if (thematicViewImageModel.getIndicatorName() != null) {
				rowNum++;
				cellNum = 0;
				row = sheet.createRow(rowNum);
				cell = row.createCell(cellNum);
				cell.setCellValue("Indicator : " + thematicViewImageModel.getIndicatorName());
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, cellNum, cellNum + 12));
			}
			rowNum++;
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue("Time Period : " + thematicViewImageModel.getTimePeriod());
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, cellNum, cellNum + 12));
			rowNum += 4;

			byte[] indiaMapImageBytes = Base64
					.decodeBase64(((String) thematicViewImageModel.getIndiaMapImage()).split(",")[1]);

			rowNum = insertimage(rowNum, indiaMapImageBytes, workbook, sheet);
			if(thematicViewImageModel.getIndiaMapImageLegend()!= null) {
				byte[] indiaMapImageLegendBytes = Base64
						.decodeBase64(((String) thematicViewImageModel.getIndiaMapImageLegend()).split(",")[1]);
				rowNum -= 1;
				
				rowNum = insertimage(rowNum, indiaMapImageLegendBytes, workbook, sheet);
				rowNum += 30;
			}
			Integer parentAreaId = 1; // For State Data Sending parent 1
			Integer indicatorId = thematicViewImageModel.getIndicatorId();
			Integer timePeriodId = thematicViewImageModel.getTimePeriodId();
//			if (thematicViewImageModel.getIndicatorId() != null) {
				List<Object[]> utDatas = utDataReporsitory.getUtDatasForAllState(indicatorId, timePeriodId,
						parentAreaId);
				Map<String, String> mapOfData = utDatas.stream()
						.collect(Collectors.toMap(k -> k[2].toString(), v -> (v[1] == null ? "N/A" : v[1].toString())));

				List<Area> allState = areaRepository.findByParentAreaIdOrderByAreaName(parentAreaId);

				rowNum += 9;
				row = sheet.createRow(rowNum);
				cell = row.createCell(cellNum);
				cell.setCellValue("Area Name");
				cellNum++;
				cell = row.createCell(cellNum);
				cell.setCellValue("Value");
				rowNum++;
				cellNum = 0;
				for (Area area : allState) {
					row = sheet.createRow(rowNum);
					cell = row.createCell(cellNum);
					cell.setCellValue(area.getAreaName());
					sheet.setColumnWidth(cellNum, 10000);
					cellNum++;
					cell = row.createCell(cellNum);
					cell.setCellValue(mapOfData.get(area.getAreaId().toString()) == null ? "N/A"
							: mapOfData.get(area.getAreaId().toString()));
					rowNum++;
					cellNum = 0;
				}
				if (thematicViewImageModel.getDistrictImage() != null) {
					rowNum += 3;
					cellNum = 0;
					row = sheet.createRow(rowNum);
					cell = row.createCell(cellNum);
					cell.setCellValue("State : " + thematicViewImageModel.getAreaName());
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, cellNum, cellNum + 12));

					rowNum++;
					cellNum = 0;
					row = sheet.createRow(rowNum);
					cell = row.createCell(cellNum);
					cell.setCellValue("Sector : " + thematicViewImageModel.getSectorName());
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, cellNum, cellNum + 12));

					rowNum++;
					cellNum = 0;
					row = sheet.createRow(rowNum);
					cell = row.createCell(cellNum);
					cell.setCellValue("Sub sector : " + thematicViewImageModel.getSubSectorName());
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, cellNum, cellNum + 12));

					rowNum++;
					cellNum = 0;
					row = sheet.createRow(rowNum);
					cell = row.createCell(cellNum);
					cell.setCellValue("Indicator : " + thematicViewImageModel.getIndicatorName());
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, cellNum, cellNum + 12));

					rowNum++;
					cellNum = 0;
					row = sheet.createRow(rowNum);
					cell = row.createCell(cellNum);
					cell.setCellValue("Time Period : " + thematicViewImageModel.getTimePeriod());
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, cellNum, cellNum + 12));

					rowNum += 2;
					byte[] districtMapImageBytes = Base64
							.decodeBase64(((String) thematicViewImageModel.getDistrictImage()).split(",")[1]);
					rowNum += insertimage(rowNum, districtMapImageBytes, workbook, sheet);
					rowNum += 2;
					List<Area> areaList = areaRepository
							.findByParentAreaIdOrderByAreaName(thematicViewImageModel.getAreaId());
					List<Integer> childAreaList = areaList.stream().map(v -> v.getAreaId())
							.collect(Collectors.toList());

					List<UtData> utDataList = utDataReporsitory.getThematicViewData(indicatorId, timePeriodId,
							childAreaList);
					// Map<Integer, String> utDataForSelectedState =
					// utDataList.stream().collect(Collectors.toMap(k->k.getArea_NId().getAreaId(),v->v.getData_Value()));

					Map<Integer, String> utDataForSelectedState = new LinkedHashMap<>();
					for (UtData utData : utDataList) {
						utDataForSelectedState.put(utData.getArea_NId().getAreaId(), utData.getData_Value());
					}

					row = sheet.createRow(rowNum);
					cell = row.createCell(cellNum);
					cell.setCellValue("Area Name");
					cellNum++;
					cell = row.createCell(cellNum);
					cell.setCellValue("Value");
					rowNum++;
					cellNum = 0;

					for (Area area : areaList) {
						row = sheet.createRow(rowNum);
						cell = row.createCell(cellNum);
						cell.setCellValue(area.getAreaName());
						sheet.setColumnWidth(cellNum, 10000);
						cellNum++;
						cell = row.createCell(cellNum);
						cell.setCellValue(!utDataForSelectedState.containsKey(area.getAreaId()) ? "N/A"
								: utDataForSelectedState.get(area.getAreaId()));
						rowNum++;
						cellNum = 0;
					}
				}
//			}
			FileOutputStream fileOutputStream = new FileOutputStream(new File(outputPathExcel));
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			workbook.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outputPathExcel;
	}
	
	// insert image in excel file
	private int insertimage(int rowNum, byte[] imageBytes, XSSFWorkbook xssfWorkbook, XSSFSheet sheet) {
		Integer size = null;
		try {
			int pictureIdx = xssfWorkbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
			CreationHelper helper = xssfWorkbook.getCreationHelper();
			Drawing drawing = sheet.createDrawingPatriarch();
			ClientAnchor anchor = helper.createClientAnchor();
			anchor.setCol1(0);
			anchor.setCol2(6);
//			anchor.setRow1(rowNum);
//			anchor.setRow2(rowNum + 12);
			Picture pict = drawing.createPicture(anchor, pictureIdx);
			if (pict.getImageDimension().getHeight() == 86.0) {
				anchor.setCol2(13);
				anchor.setRow2(4);
				size = 4;
			}  else if (pict.getImageDimension().getHeight() <= 100) {
				anchor.setCol2(13);
				anchor.setRow2(3);
				size = 3;
			} else if (pict.getImageDimension().getHeight() == 136.0) {
				anchor.setCol1(6);
				anchor.setCol2(9);
				anchor.setRow1(rowNum);
				anchor.setRow2(rowNum+5);
				size = 4;
			} else if (pict.getImageDimension().getHeight() == 140.0) {
				anchor.setRow1(rowNum);
				anchor.setRow2(rowNum + 6);
				size = 12;
			}else if (pict.getImageDimension().getHeight() < 150) {
				anchor.setRow1(rowNum);
				anchor.setRow2(rowNum + 12);
				size = 12;
			} else if (pict.getImageDimension().getHeight() > 150 && pict.getImageDimension().getHeight() <= 300) {
				anchor.setRow1(rowNum);
				anchor.setRow2(rowNum + 12);
				size = 18;
			} else if (pict.getImageDimension().getHeight() > 300 && pict.getImageDimension().getHeight() <= 560) {
				anchor.setRow1(rowNum);
				anchor.setRow2(rowNum + 20);
				size = 24;
			}else if (pict.getImageDimension().getHeight() > 560 && pict.getImageDimension().getHeight() <= 1000) {
				anchor.setRow1(rowNum);
				anchor.setRow2(rowNum +25);
				size = 35;
			}else if (pict.getImageDimension().getHeight() > 1000 && pict.getImageDimension().getHeight() <= 1500) {
				anchor.setRow1(rowNum);
				anchor.setRow2(rowNum + 50);
				size = 50;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return size.intValue();
	}
		
	@Override
	public String snapShotViewPDF(SnapSortModel snapSortModel, HttpServletResponse response,
			HttpServletRequest request) {
		String outputPathPdf=env.getProperty("output.path.pdf");
		try {
			
			File file = new File(outputPathPdf);
			if(!file.exists()){
				file.mkdirs();
			}
			outputPathPdf = outputPathPdf +"_"+date+".pdf";
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
		    layout.setBackgroundColor(new BaseColor(194, 206, 232));
			Document document = new Document(layout);
		    Font font = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, GrayColor.BLACK);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPathPdf));
			
			
			String uri = request.getRequestURI();
			String url = request.getRequestURL().toString();
			url = url.replaceFirst(uri, "");
			
			HeaderFooter headerFooter = new HeaderFooter(url,"landscape");
			writer.setPageEvent(headerFooter);
			
			Paragraph areaParagraph = new Paragraph();
			areaParagraph.setAlignment(Element.ALIGN_LEFT);
			areaParagraph.setSpacingBefore(35);
			areaParagraph.setSpacingAfter(15);
			String areaLevel = snapSortModel.getAreaLevelName().toLowerCase().equals("national")?"India":snapSortModel.getAreaLevelName();
			String selectedOption = "Level : " + areaLevel;
			if(snapSortModel.getStateId() != null) {
				selectedOption += "      State : "+snapSortModel.getStateName();
			}
			if(snapSortModel.getDistrictId() != null) {
				selectedOption += "      District : "+snapSortModel.getDistrictName();
			}
			selectedOption += "      Time Period : "+snapSortModel.getTimeperiod();
			
			Chunk areaChunk = new Chunk(selectedOption, font);
				
			areaParagraph.add(areaChunk);
			
			document.open();
			document.add(areaParagraph);
			int districtIndex = 0;
			Map<String, Map<String, List<String>>> svgs = snapSortModel.getSvgs();
			for(String sectorName : svgs.keySet()) {
				PdfPTable table1 = new PdfPTable(1);
		        table1.setTotalWidth(840);
		        table1.setLockedWidth(true);
		        PdfPCell cell1 = new PdfPCell();
		        cell1.setBackgroundColor(new BaseColor(85, 145, 197));
		        Paragraph p1 = new Paragraph(sectorName, font);
		        p1.setAlignment(Element.ALIGN_CENTER);
				p1.setSpacingBefore(1);
				p1.setSpacingAfter(10);
		        cell1.addElement(p1);
		        cell1.setBorder(Rectangle.NO_BORDER);
		        table1.addCell(cell1);
		       
		        Map<String, List<String>> subSectorMap = svgs.get(sectorName);
				for (String subSectorName : subSectorMap.keySet()) {
				
					if(snapSortModel.getAreaLevelId() != 3) {
						PdfPTable table = new PdfPTable(1);
						table.setTotalWidth(840);
						PdfPCell cell = new PdfPCell();
						cell.setBackgroundColor(new BaseColor(194, 206, 232));
						Paragraph p2 = new Paragraph(subSectorName, font);
						p2.setAlignment(Element.ALIGN_CENTER);
						p2.setSpacingBefore(1);
						p2.setSpacingAfter(7);
						cell.addElement(p2);
						cell.setBorder(Rectangle.NO_BORDER);
						table.setSpacingBefore(3);
						table.addCell(cell);
						
						if("Education".equals(subSectorName.trim())) {
							table1.writeSelectedRows(-1, -1, 0, 295, writer.getDirectContent());
							table.writeSelectedRows(-1, -1, 0, 255, writer.getDirectContent());
						} else if("Protection".equals(subSectorName.trim())) {
							table1.writeSelectedRows(-1, -1, 0, 270, writer.getDirectContent());
							table.writeSelectedRows(-1, -1, 0, 240, writer.getDirectContent());
						} else if("Survival".equals(subSectorName.trim())) {
							table1.writeSelectedRows(-1, -1, 0, 540, writer.getDirectContent());
							table.writeSelectedRows(-1, -1, 0, 500, writer.getDirectContent());
						} else {
							document.add(table1);
							document.add(table);
						}
						List<String> listOfSVGS = subSectorMap.get(subSectorName);
						int index = 0;
						for (String svgString : listOfSVGS) {
							byte[] svgImageBytes = Base64.decodeBase64(((String) svgString).split(",")[1]);
							Image svgImage = Image.getInstance(svgImageBytes);

							if("BBBP Institutional Mechanisms".equals(subSectorName.trim())) {
								if(index==0) {
									svgImage.setAbsolutePosition(85, 230);
								}else if(index == 1) {
									svgImage.setAbsolutePosition(445, 230);
								}else if(index == 2) {
									svgImage.setAbsolutePosition(85, 35);
								}else if(index == 3) {
									svgImage.setAbsolutePosition(445, 35);
								}
							}else if("Survival".equals(subSectorName.trim())) {
								if(index==0) {
									svgImage.setAbsolutePosition(85, 300);
								}else if(index == 1) {
									svgImage.setAbsolutePosition(445, 300);
								}
							}else if("Protection".equals(subSectorName.trim())){
								if(index == 0) {
									svgImage.setAbsolutePosition(85, 35);
								}else if(index == 1) {
									svgImage.setAbsolutePosition(445, 35);
								}else if(index==2) {
									svgImage.setAbsolutePosition(85, 280);
								}else if(index == 3) {
									svgImage.setAbsolutePosition(445, 280);
								}else if(index == 4) {
									svgImage.setAbsolutePosition(85, 70);
								}else if(index == 5) {
									svgImage.setAbsolutePosition(445, 70);
								}else if(index == 6) {
									svgImage.setAbsolutePosition(85, 320);
								}else if(index == 7) {
									svgImage.setAbsolutePosition(445, 320);
								}
							}else if("Education".equals(subSectorName.trim())){
								if(index == 0) {
									svgImage.setAbsolutePosition(85, 35);
								}else if(index == 1) {
									svgImage.setAbsolutePosition(445, 35);
								}else if(index==2) {
									svgImage.setAbsolutePosition(85, 280);
								}else if(index == 3) {
									svgImage.setAbsolutePosition(445, 280);
								}else if(index == 4) {
									svgImage.setAbsolutePosition(85, 70);
								}else if(index == 5) {
									svgImage.setAbsolutePosition(445, 70);
								}
							}
							svgImage.scalePercent(70);
							document.add(svgImage);
							if("BBBP Institutional Mechanisms".equals(subSectorName.trim()) && index == 3) {
								document.newPage();
								document.add(Chunk.NEWLINE);
							} else if("Protection".equals(subSectorName.trim()) && (index == 1 || index == 5)) {
								document.newPage();
								document.add(Chunk.NEWLINE);
							}else if("Education".equals(subSectorName.trim()) && index == 1){
								document.newPage();
								document.add(Chunk.NEWLINE);
							}
							index++;
						}
					} else {
						districtIndex=0; 
						PdfPTable table = new PdfPTable(1);
						table.setTotalWidth(860);
						PdfPCell cell = new PdfPCell();
						cell.setBackgroundColor(new BaseColor(194, 206, 232));
						Paragraph p2 = new Paragraph(subSectorName, font);
						p2.setAlignment(Element.ALIGN_CENTER);
						p2.setSpacingBefore(10);
						p2.setSpacingAfter(7);
						cell.addElement(p2);
						cell.setBorder(Rectangle.NO_BORDER);
						table.addCell(cell);
						table.setSpacingBefore(3);
						
						if("Survival".equals(subSectorName.trim())) {
							table1.writeSelectedRows(0, -1, 0, 220, writer.getDirectContent());
							table.writeSelectedRows(0, -1, 0, 180, writer.getDirectContent());
						} else if("Education".equals(subSectorName.trim())) {
							table1.writeSelectedRows(0, -1, 0, 280, writer.getDirectContent());
							table.writeSelectedRows(0, -1, 0, 240, writer.getDirectContent());
						}else if("Protection".equals(subSectorName.trim())) {
							table1.writeSelectedRows(-1, -1, 0, 540, writer.getDirectContent());
							table.writeSelectedRows(-1, -1, 0, 500, writer.getDirectContent());
						} else {
							document.add(table1);
							document.add(table);
						}

						List<String> listOfSVGS = subSectorMap.get(subSectorName);
						
						for (String svgString : listOfSVGS) {
							byte[] svgImageBytes = Base64.decodeBase64(((String) svgString).split(",")[1]);
							Image svgImage = Image.getInstance(svgImageBytes);
							if("BBBP Institutional Mechanisms".equals(subSectorName.trim())) {
								if(districtIndex==0) {
									svgImage.setAbsolutePosition(85, 250);
								}else if(districtIndex == 1) {
									svgImage.setAbsolutePosition(445, 250);
								}
							}else if("Survival".equals(subSectorName.trim())) {
								if(districtIndex==0) {
									svgImage.setAbsolutePosition(85, 45);
								}
								districtIndex=-1;
							} else if("Education".equals(subSectorName.trim())) {
								if(districtIndex==0) {
									svgImage.setAbsolutePosition(85, 50);
								}else if(districtIndex == 1) {
									svgImage.setAbsolutePosition(445, 50);
								}else if(districtIndex == 2) {
									svgImage.setAbsolutePosition(85, 280);
								}else if(districtIndex == 3) {
									svgImage.setAbsolutePosition(445, 280);
								}else if(districtIndex == 4) {
									svgImage.setAbsolutePosition(85, 70);
								}else if(districtIndex == 5) {
									svgImage.setAbsolutePosition(445, 70);
								}
							}else if("Protection".equals(subSectorName.trim())){
								if(districtIndex==0) {
									svgImage.setAbsolutePosition(85, 280);
								}else if(districtIndex == 1) {
									svgImage.setAbsolutePosition(445, 280);
								}else if(districtIndex == 2) {
									svgImage.setAbsolutePosition(85, 70);
								}else if(districtIndex == 3) {
									svgImage.setAbsolutePosition(445, 70);
								}else if(districtIndex == 4) {
									svgImage.setAbsolutePosition(85, 315);
								}else if(districtIndex == 5) {
									svgImage.setAbsolutePosition(445, 315);
								}
							}
							svgImage.scalePercent(70);
							document.add(svgImage);
							if(subSectorName.trim().equals("Survival")) {
								document.newPage();
								document.add(Chunk.NEWLINE);
							}else if("Protection".equals(subSectorName.trim()) && districtIndex == 3) {
								document.newPage();
								document.add(Chunk.NEWLINE);
							}else if("Education".equals(subSectorName.trim()) && districtIndex == 1) {
								document.newPage();
								document.add(Chunk.NEWLINE);
							}
							districtIndex++;
						}
					}
				}
			}
			document.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outputPathPdf;
	}
	
	@Override
	public String snapShotViewExcel(SnapSortModel snapSortModel, HttpServletResponse response,
			HttpServletRequest request) {
		
		/**
		 * Fetching latest 4 time periods in descending order
		 */
		List<UtTimeperiod> utTimeperiodList = utTimeperiodRepository.findByPeriodicityPeriodicityIdDESC(1);
		
		Integer latestTimePeriod = utTimeperiodList.get(0).getTimePeriod_NId();
		
		Collections.reverse(utTimeperiodList);
		Map<Integer, String> utTimeperiods = new LinkedHashMap<>();
		for (UtTimeperiod utTimeperiod : utTimeperiodList) {
			utTimeperiods.put(utTimeperiod.getTimePeriod_NId(), utTimeperiod.getShortName());
		}
		Map<Integer, String> indiQuesMap = indicatorQuestionMappingRepository.findAll()
				.stream().collect(Collectors.toMap(IndicatorQuestionMapping::getIndicator_NId, IndicatorQuestionMapping::getIndicator_Name));

		/**
		 * Contains Group_name and GroupIndicatorModel
		 */
		Map<String, GroupIndicatorModel> groupIndicatorModelsMap = indicatorGroupRepository.findAll()
				.get(0).getGroupIndicatorsJson().stream().collect(Collectors.toMap(GroupIndicatorModel::getIndicatorGroup, v->v));
		
		Integer areaId = (snapSortModel.getAreaLevelId()==1) 
							? 1 : (snapSortModel.getAreaLevelId()==2) 
									? snapSortModel.getStateId():snapSortModel.getDistrictId();
		
		List<UtData> utDataTrend = utDataReporsitory.getDataValue(new ArrayList<Integer>(utTimeperiods.keySet()), areaId);
		
		Map<Integer, String> utDataMap = new LinkedHashMap<>();
		Map<Integer, Map<Integer, String>> trendData = new LinkedHashMap<>();
		Map<Integer, String> utdata  = null;
		
		for (UtData data : utDataTrend) {
			if(!trendData.containsKey(data.getIndicator_NId())) {
				utdata  = new HashMap<>();
				if(!utdata.containsKey(data.getTimePeriod_NId().getTimePeriod_NId())) {
					utdata.put(data.getTimePeriod_NId().getTimePeriod_NId(), data.getData_Value());
					trendData.put(data.getIndicator_NId(), utdata);
				}
			} else {
				utdata = trendData.get(data.getIndicator_NId());
				if(!utdata.containsKey(data.getTimePeriod_NId().getTimePeriod_NId())) {
					utdata.put(data.getTimePeriod_NId().getTimePeriod_NId(), data.getData_Value());
				}
			}
			
			if(data.getTimePeriod_NId().getTimePeriod_NId()==latestTimePeriod.intValue()) {
				utDataMap.put(data.getIndicator_NId(), data.getData_Value());
			}
		}	
		
		String outputPathExcel=env.getProperty("output.path.excel");
		List<String> indicatorGroup = new ArrayList<>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("BBBP");
			XSSFRow row = null;
			XSSFCell cell = null;
			int rowNum = 0;
			int cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			
			XSSFCellStyle cellstyleMiddle = BBBPUtil.getStyleForLeftMiddle(workbook);
			XSSFCellStyle cellstyleMiddleSector = BBBPUtil.getStyleForLeftMiddleSector(workbook);
			XSSFCellStyle cellstyleMiddleSubSector = BBBPUtil.getStyleForLeftMiddleSubSector(workbook);
			
			BufferedImage bImage = ImageIO.read(new URL("classpath:images/header.jpg"));
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    ImageIO.write(bImage, "jpg", bos );
		    
			byte [] headerImgBytes = bos.toByteArray();
			rowNum = insertimage(rowNum, headerImgBytes, workbook, sheet);
			
			String date = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
			String path = outputPathExcel;
			
			File file = new File(path);
			if(!file.exists()){
				file.mkdirs();
			}
			outputPathExcel = path +"_"+date+".xlsx";
			rowNum+=2;
			String areaLevel = snapSortModel.getAreaLevelName().toLowerCase().equals("national")?"India":snapSortModel.getAreaLevelName();
			String selectedOption = "Level : " + areaLevel;
			if(snapSortModel.getStateId() != null) {
				selectedOption += "      State : "+snapSortModel.getStateName();
			}
			if(snapSortModel.getDistrictId() != null) {
				selectedOption += "      District : "+snapSortModel.getDistrictName();
			}
			selectedOption += "      Time Period : "+snapSortModel.getTimeperiod();
			
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(selectedOption);
			cell.setCellStyle(cellstyleMiddle);
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+1, cellNum, cellNum+12));
			rowNum+=3;
			
			Map<String, Map<String, List<String>>> svgs = snapSortModel.getSvgs();
			for(String sectorName : svgs.keySet()) {
				
				cellNum=0;
				row = sheet.createRow(rowNum);
				cell = row.createCell(cellNum);
				cell.setCellValue(sectorName);
				cell.setCellStyle(cellstyleMiddleSector);
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+1, cellNum, cellNum+12));
				
				rowNum+=3;
				
				Map<String, List<String>> subSectorMap = svgs.get(sectorName);
				
				for (String subSectorName : subSectorMap.keySet()) {
					cellNum=0;
					row = sheet.createRow(rowNum);
					cell = row.createCell(cellNum);
					cell.setCellValue(subSectorName);
					cell.setCellStyle(cellstyleMiddleSubSector);
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+1, cellNum, cellNum+12));
					
					rowNum+=3;
					
					List<String> listOfSVGS = subSectorMap.get(subSectorName);
					for (String svgString : listOfSVGS) {
						byte[] svgImageBytes = Base64.decodeBase64(((String) svgString).split(",")[1]);
						
						cellNum=0;
						row = sheet.createRow(rowNum);
						cell = row.createCell(cellNum);
						int rownum=insertimage(rowNum, svgImageBytes, workbook, sheet);
						
						setValues(rowNum, svgString.split("@_@")[0], groupIndicatorModelsMap, 
								indiQuesMap, trendData, utDataMap, sheet, workbook, 7, 
								utTimeperiods, indicatorGroup, cellstyleMiddleSector, cellstyleMiddleSubSector, snapSortModel.getAreaLevelId());
						
						rowNum+=rownum;
					}
				}
			}
			
			FileOutputStream fileOutputStream = new FileOutputStream(new File(outputPathExcel));
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			workbook.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outputPathExcel;
	}

	private void setValues(int rowNum, String groupName, Map<String, GroupIndicatorModel> groupIndicatorModelsMap,
			Map<Integer, String> indiQuesMap, Map<Integer, Map<Integer, String>> trendData,
			Map<Integer, String> utDataMap, XSSFSheet sheet, XSSFWorkbook workbook, int cellNum, 
			Map<Integer, String> utTimeperiods, List<String> indicatorgroup,
			XSSFCellStyle cellstyleMiddle, XSSFCellStyle cellstyleFont, Integer areaLevelId) {
		
		List<Integer> indicatorNids = groupIndicatorModelsMap.get(groupName.split("@")[0].toString()).getIndicatorNids();
		List<String> chartType = groupIndicatorModelsMap.get(groupName.split("@")[0].toString()).getChartType();

		XSSFRow row = null;
		XSSFCell cell = null;
		int cellTemp = cellNum;
		if((areaLevelId == 1 || areaLevelId == 2) && groupName.split("@")[0].equals("group-6")) {
			indicatorNids.add(41);
		}
		
		for(Integer indicator:indicatorNids) {
			if((chartType.contains("box") || chartType.contains("bar")) && !indicatorgroup.contains(groupName.split("@")[0])) {
				row = sheet.createRow(rowNum);
				cell = row.createCell(cellNum);
				cell.setCellValue(indiQuesMap.get(indicator));
				sheet.setColumnWidth(cellNum, 13000);
				cellNum++;
				cell = row.createCell(cellNum);
				cell.setCellValue(utDataMap.containsKey(indicator)?utDataMap.get(indicator):"N/A");
				rowNum++;
				cellNum = cellTemp;
			} else if(chartType.contains("trend") && Integer.parseInt(groupName.split("@")[1])==indicator) {
				row = sheet.createRow(rowNum);
				cell = row.createCell(cellNum);
				cell.setCellValue(indiQuesMap.get(indicator));
				sheet.setColumnWidth(cellNum, 15000);
				for(Integer tp : utTimeperiods.keySet()) {
					rowNum++;
					row = sheet.createRow(rowNum);
					cell = row.createCell(cellNum);
					cell.setCellValue(utTimeperiods.get(tp));
					cellNum++;
					cell = row.createCell(cellNum);
					cell.setCellValue(trendData.containsKey(indicator)?trendData.get(indicator).get(tp):"N/A");
					cellNum = cellTemp;
				}
			}
		}
		
		if((!indicatorgroup.contains("box") || !indicatorgroup.contains("bar"))){
			indicatorgroup.add(groupName.split("@")[0]);
		}
	}
}
