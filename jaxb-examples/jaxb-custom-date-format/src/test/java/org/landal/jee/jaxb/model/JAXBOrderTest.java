package org.landal.jee.jaxb.model;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;
import org.landal.jee.jaxb.model.Customer;
import org.landal.jee.jaxb.model.Order;
import org.landal.jee.jaxb.model.OrderItem;

public class JAXBOrderTest {

	@Test
	public void testMashalling() {

		Order order = new Order();
		order.setId(1L);
		order.setNotes("notes");
		order.setOrderDate(new Date());

		OrderItem item = new OrderItem();
		item.setProduct("product1");
		order.addItem(item);

		item = new OrderItem();
		item.setProduct("product2");
		order.addItem(item);

		Customer customer = new Customer();
		customer.setId(100L);
		customer.setName("alex");
		customer.setAge(29);
		order.setCustomer(customer);

		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			File file = new File("target/orders.xml");
			jaxbMarshaller.marshal(order, file);
			jaxbMarshaller.marshal(order, System.out);

		} catch (JAXBException e) {
			fail();
			e.printStackTrace();
		}

	}

	@Test
	public void testUnmarshilling() {
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();			
			Order order = (Order) jaxbUnmarshaller.unmarshal(this.getClass()
					.getClassLoader().getResourceAsStream("test-orders.xml"));
			System.out.println(order);

		} catch (JAXBException e) {
			fail();
			e.printStackTrace();
		}
	}
}
