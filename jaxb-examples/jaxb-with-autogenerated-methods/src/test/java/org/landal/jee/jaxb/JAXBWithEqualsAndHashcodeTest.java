package org.landal.jee.jaxb;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

public class JAXBWithEqualsAndHashcodeTest {

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
	
	@Test
	public void testEquals() {
		
		ObjectFactory factory = new ObjectFactory();
		Newsletter newsletter = factory.createNewsletter();		
		newsletter.setComment("comment");
		SectionType section = factory.createSectionType();		
		section.setTitle("section1");		
		newsletter.setSection1(section);
		
		section = factory.createSectionType();		
		section.setTitle("section2");		
		newsletter.setSection2(section);		

		Object cloned = newsletter.clone();
		
		assertEquals(newsletter, cloned);
	}
	
	@Test
	public void testNotEquals() {
		
		ObjectFactory factory = new ObjectFactory();
		Newsletter newsletter = factory.createNewsletter();		
		newsletter.setComment("comment");
		SectionType section = factory.createSectionType();		
		section.setTitle("section1");		
		newsletter.setSection1(section);
		
		section = factory.createSectionType();		
		section.setTitle("section2");		
		newsletter.setSection2(section);		

		Newsletter cloned = (Newsletter) newsletter.clone();		
		cloned.setSection2(null);
		
		assertThat(cloned, not(equalTo(newsletter)));
	}

}
