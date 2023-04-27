package com.ander.VitoCar.Repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ander.VitoCar.model.User;


@Repository
public interface UserRepositorio extends CrudRepository<User,Integer> {
	// find all users from the database
	List<User> findAll();
	Optional<User> findByMail(String mail);
}
