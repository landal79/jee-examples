package org.landal.jee.jaxb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.landal.jee.jaxb.serializer.JAXBCustomDateSerializer;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@XmlJavaTypeAdapter(JAXBCustomDateSerializer.class)
	private Date orderDate;

	private Customer customer;

	private String notes;

	private List<OrderItem> items;

	public void addItem(OrderItem item) {

		if (item == null) {
			throw new NullPointerException();
		}

		if (getItems() == null) {
			setItems(new ArrayList<OrderItem>());
		}

		getItems().add(item);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

}
