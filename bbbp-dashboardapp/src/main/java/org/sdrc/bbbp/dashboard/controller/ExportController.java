package org.sdrc.bbbp.dashboard.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdrc.bbbp.dashboard.models.SnapSortModel;
import org.sdrc.bbbp.dashboard.models.ThematicViewImageModel;
import org.sdrc.bbbp.dashboard.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class ExportController {
	
	@Autowired
	ExportService exportService;
	
	@PostMapping("/exportThematicViewPDF")
	public ResponseEntity<InputStreamResource> exportThematicViewPDF(@RequestBody ThematicViewImageModel thematicViewImageModel,  
			HttpServletResponse response, HttpServletRequest request){
		
		String filePath = "";
		try {
			filePath = exportService.exportThematicViewPDF(thematicViewImageModel, response, request);
			File file = new File(filePath);

			HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.add("Content-Disposition", "attachment; filename=" + file.getName());
			InputStreamResource isr = new InputStreamResource(new FileInputStream(file));

			file.delete();
			return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@PostMapping("/exportThematicViewExcel")
	public ResponseEntity<InputStreamResource> exportThematicViewExcel(@RequestBody ThematicViewImageModel thematicViewImageModel,   
			HttpServletResponse response, HttpServletRequest request){
		
		String filePath = "";
		try {
			filePath = exportService.exportThematicViewExcel(thematicViewImageModel, response, request);
			File file = new File(filePath);

			HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.add("Content-Disposition", "attachment; filename=" + file.getName());
			InputStreamResource isr = new InputStreamResource(new FileInputStream(file));

			file.delete();
			return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@PostMapping("/snapShotViewPDF")
	public ResponseEntity<InputStreamResource> snapShotViewPDF(@RequestBody SnapSortModel snapSortModel,  
			HttpServletResponse response, HttpServletRequest request){

		String filePath = "";
		try {
			filePath = exportService.snapShotViewPDF(snapSortModel, response, request);
			File file = new File(filePath);

			HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.add("Content-Disposition", "attachment; filename=" + file.getName());
			InputStreamResource isr = new InputStreamResource(new FileInputStream(file));

			file.delete();
			return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@PostMapping("/snapShotViewExcel")
	public ResponseEntity<InputStreamResource> snapShotViewExcel(@RequestBody SnapSortModel snapSortModel,   
			HttpServletResponse response, HttpServletRequest request){
		String filePath = "";
		try {
			filePath = exportService.snapShotViewExcel(snapSortModel, response, request);
			File file = new File(filePath);

			HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.add("Content-Disposition", "attachment; filename=" + file.getName());
			InputStreamResource isr = new InputStreamResource(new FileInputStream(file));

			file.delete();
			return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}


}
