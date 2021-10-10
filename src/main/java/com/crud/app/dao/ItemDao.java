package com.crud.app.dao;

 
import org.springframework.stereotype.Component;

import com.crud.app.model.Item;

@Component
public interface ItemDao extends GenericDao<Item, Integer> {
 
}
