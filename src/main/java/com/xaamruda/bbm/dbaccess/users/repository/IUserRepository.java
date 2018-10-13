package com.xaamruda.bbm.dbaccess.users.repository;

import java.util.List;
// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.xaamruda.bbm.users.model.User;

public interface IUserRepository extends CrudRepository<User, String> {
	// public interface IUserRepository extends JpaRepository<User, String> {

	public List<User> findByMail(String mail);

}
