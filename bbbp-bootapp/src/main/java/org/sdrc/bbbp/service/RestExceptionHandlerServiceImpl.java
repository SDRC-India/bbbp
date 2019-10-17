package org.sdrc.bbbp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.sdrc.bbbp.exceptions.CustomDateParseException;
import org.springframework.stereotype.Service;

@Service
public class RestExceptionHandlerServiceImpl implements RestExceptionHandlerService {

	@Override
	public Date parseDate(String date) {
		String dateString = "121";
		try {
			return new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
		} catch (ParseException e) {
			throw new CustomDateParseException("Failed to parse date from variable 'dateString'.Passed parameter : "+dateString,e);
		}

	}

}
