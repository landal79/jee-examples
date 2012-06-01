package org.landal.jee.jaxb.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JAXBCustomDateAdapter extends XmlAdapter<String, Date> {

	private static final String DATE_FORMAT = "dd-MM-yyyy";

	@Override
	public String marshal(Date date) throws Exception {
		return new SimpleDateFormat(DATE_FORMAT).format(date);
	}

	@Override
	public Date unmarshal(String date) throws Exception {
		return new SimpleDateFormat(DATE_FORMAT).parse(date);
	}

}
