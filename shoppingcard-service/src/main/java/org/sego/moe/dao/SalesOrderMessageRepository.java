package org.sego.moe.dao;

import org.sego.moe.commons.model.CardEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderMessageRepository extends CrudRepository<CardEvent, String> {

}
