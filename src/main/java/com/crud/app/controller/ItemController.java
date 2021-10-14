package com.crud.app.controller;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.crud.app.dao.ItemDao;
import com.crud.app.messageUtils.Messages;
import com.crud.app.model.Item;

@Scope(value = "session")
@Component(value = "itemController")
@ELBeanName(value = "itemController")
//@Join(path = "/", to = "/item-form.jsf")
@Join(path = "/", to = "/curd.jsf")
public class ItemController {

	@Autowired
	private ItemDao itemDao;

	private Item selectedProduct;

//	private Item selectedProduct;

	private List<Item> itemsList;

	@PostConstruct
	public void init() {

		loadData();
	}

	@Deferred
	@RequestAction
	@IgnorePostback
	public void loadData() {
		itemsList = itemDao.findAll();
	}

	public void openNew() {
		this.selectedProduct = new Item();
	}

	public String save() {

		if (this.selectedProduct.getNome() == null || this.selectedProduct.getNome().trim() == "") {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please Type Product Name"));
			return null;
		} else {

			if (this.selectedProduct.getCode() == null) {
				this.selectedProduct.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
				try {
					itemDao.saveOrUpdate(this.selectedProduct);

					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Added"));
					loadData();
				} catch (Exception e) {
					Messages.generateMessageInfo(FacesMessage.SEVERITY_ERROR, "Error.", e.getMessage());
				}

			} else {
				itemDao.saveOrUpdate(this.selectedProduct);
				loadData();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Updated"));

			}
			PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
			PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
			return "true";
		}

	}

	public void deleteProduct() {
		this.itemDao.delete(this.selectedProduct);
		this.selectedProduct = null;
		loadData();
		PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Removed"));

	}

	public List<Item> getItemsList() {
		return itemsList;
	}

	public Item getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Item selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

}
