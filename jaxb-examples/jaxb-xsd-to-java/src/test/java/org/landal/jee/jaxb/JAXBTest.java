package org.landal.jee.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

public class JAXBTest {

	@Test
	public void testMarshall() throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance("org.landal.jee.jaxb");
		ObjectFactory factory = new ObjectFactory();
		Newsletter newsletter = factory.createNewsletter();		
		newsletter.setComment("comment");
		SectionType section = factory.createSectionType();		
		section.setTitle("section1");		
		newsletter.setSection1(section);
		
		section = factory.createSectionType();		
		section.setTitle("section2");		
		newsletter.setSection2(section);		

		JAXBElement<Newsletter> element = factory
				.createWebwriterNewsletter(newsletter);
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(element, System.out);
	}

}
