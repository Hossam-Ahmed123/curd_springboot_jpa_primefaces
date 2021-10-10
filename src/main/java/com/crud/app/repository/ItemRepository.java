package com.crud.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.app.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{

}
