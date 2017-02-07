package com.github.artemdvn.webpublisher.services;

import java.util.List;

import com.github.artemdvn.webpublisher.model.User;

public interface UserService {
	
	public void create(User user);

	public User get(Integer userId);
	
	public List<User> getByRole(String role);
	
	public User getByName(String name);

	public List<User> getAll();

	public void update(User user);

	public void delete(User user);

	public void delete(Integer userId);

}
