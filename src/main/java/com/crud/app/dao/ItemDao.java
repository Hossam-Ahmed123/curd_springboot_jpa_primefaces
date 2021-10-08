package com.crud.app.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.crud.app.model.Item;

@Component
public interface ItemDao extends GenericDao<Item, Integer> {

	List<Item> findItemsActives();

	List<Item> findItemsCompleted();

}
