package com.example;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
@RepositoryRestResource(collectionResourceRel = "logs", path = "logs")
public interface LogRepository extends MongoRepository<Log, String> {

	List<Log> findByLog1Msg(@Param("name") String name);

}
