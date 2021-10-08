package com.crud.app.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
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
@Join(path = "/", to = "/item-form.jsf")
 
public class ItemController {

	@Autowired
	private ItemDao itemDao;

	private Item item;

	private List<Item> itemsList;

	 

	@PostConstruct
	public void init() {
		item = new Item();
		loadData();
	}

	 
	@Deferred
	@RequestAction
	@IgnorePostback
	public void loadData() {
		itemsList = itemDao.findAll();
	}

	public String save() {

		if (item.getNome() == null || item.getNome().trim() == "") {
			Messages.generateMessageInfo(FacesMessage.SEVERITY_ERROR, "Product Name is Empty.",
					"please type product name.");
			return null;
		} else {
			 
			try {
				itemDao.saveOrUpdate(item);
				item = new Item();
				loadData();
			} catch (Exception e) {
				Messages.generateMessageInfo(FacesMessage.SEVERITY_ERROR, "Error.", e.getMessage());
			}

			return "/item-form.xhtml?faces-redirect=true";
		}

	}

 

	public void deleteItem(Item item) {
		try {
			itemDao.delete(item);
			loadData();
		} catch (Exception e) {
			Messages.generateMessageInfo(FacesMessage.SEVERITY_ERROR, "Error.", e.getMessage());
		}
	}

 
	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		FacesContext context = FacesContext.getCurrentInstance();
		if (newValue != null && !newValue.equals(oldValue)) {
			Item item = context.getApplication().evaluateExpressionGet(context, "#{item}", Item.class);
			itemDao.saveOrUpdate(item);
			Messages.generateMessageInfo(FacesMessage.SEVERITY_INFO, "Product is updated",
					"Old: " + oldValue + ",  New :" + newValue);
		}
	}

	public Item getItem() {
		return item;
	}

	public List<Item> getItemsList() {
		return itemsList;
	}

	 

}
