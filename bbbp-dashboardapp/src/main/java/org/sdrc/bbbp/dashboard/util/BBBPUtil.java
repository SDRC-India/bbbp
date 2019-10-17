package org.sdrc.bbbp.dashboard.util;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

@Component
public class BBBPUtil {

	public static PdfPCell getPdfPCell(String Str) {

		PdfPCell cell = new PdfPCell(new Phrase(Str));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setMinimumHeight(20f);

		return cell;
	}

	public static XSSFCellStyle getStyleForLeftMiddle(XSSFWorkbook workbook) {

		XSSFCellStyle styleForLeftMiddle = workbook.createCellStyle();
		styleForLeftMiddle.setVerticalAlignment(VerticalAlignment.CENTER);
		styleForLeftMiddle.setAlignment(HorizontalAlignment.CENTER);
//		styleForLeftMiddle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//		styleForLeftMiddle.setFillForegroundColor(new XSSFColor(new java.awt.Color(248, 181, 32)));
		styleForLeftMiddle.setFont(getStyleForFont(workbook).getFont());
		return styleForLeftMiddle;
	}
	
	public static XSSFCellStyle getStyleForLeftMiddleSector(XSSFWorkbook workbook) {

		XSSFCellStyle styleForLeftMiddle = workbook.createCellStyle();
		styleForLeftMiddle.setVerticalAlignment(VerticalAlignment.CENTER);
		styleForLeftMiddle.setAlignment(HorizontalAlignment.CENTER);
		styleForLeftMiddle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleForLeftMiddle.setFillForegroundColor(new XSSFColor(new java.awt.Color(85, 145, 197)));
		styleForLeftMiddle.setFont(getStyleForFont(workbook).getFont());
		return styleForLeftMiddle;
	}

	public static XSSFCellStyle getStyleForLeftMiddleSubSector(XSSFWorkbook workbook) {

		XSSFCellStyle styleForLeftMiddle = workbook.createCellStyle();
		styleForLeftMiddle.setVerticalAlignment(VerticalAlignment.CENTER);
		styleForLeftMiddle.setAlignment(HorizontalAlignment.CENTER);
		styleForLeftMiddle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleForLeftMiddle.setFillForegroundColor(new XSSFColor(new java.awt.Color(194, 206, 232)));
		styleForLeftMiddle.setFont(getStyleForFont(workbook).getFont());
		return styleForLeftMiddle;
	}


	public static XSSFCellStyle getStyleForFont(XSSFWorkbook workbook) {

		// Create a new font and alter it.
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 15);
		font.setBold(true);

		XSSFCellStyle tyleForWrapFont = workbook.createCellStyle();
		tyleForWrapFont.setFont(font);

		return tyleForWrapFont;
	}
}
