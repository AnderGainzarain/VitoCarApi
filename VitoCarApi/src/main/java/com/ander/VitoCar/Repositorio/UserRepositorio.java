package com.ander.VitoCar.Repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ander.VitoCar.model.User;


@Repository
public interface UserRepositorio extends CrudRepository<User,Integer> {
	// find all users from the database
	List<User> findAll();
}
