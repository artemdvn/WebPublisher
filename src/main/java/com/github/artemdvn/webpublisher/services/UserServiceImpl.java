package com.github.artemdvn.webpublisher.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.artemdvn.webpublisher.dao.UserDao;
import com.github.artemdvn.webpublisher.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserDao userDao;
	
	public UserServiceImpl() {
	}

	@Override
	public void create(User user) {
		userDao.create(user);
	}

	@Transactional(readOnly = true)
	@Override
	public User get(Integer userId) {
		if (userId == null) {
			return null;
		}
		return userDao.get(userId);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<User> getByRole(String role) {
		return userDao.getByRole(role);
	}
	
	@Transactional(readOnly = true)
	@Override
	public User getByName(String name) {
		return userDao.getByName(name);
	}

	@Transactional(readOnly = true)
	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void delete(User user) {
		userDao.delete(user);
	}

	@Override
	public void delete(Integer userId) {
		userDao.delete(userId);
	}

}
