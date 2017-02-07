package com.github.artemdvn.webpublisher.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.artemdvn.webpublisher.model.User;
import com.github.artemdvn.webpublisher.model.UserRole;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
    private SessionFactory sf;
	
	private UserDaoImpl() {
	}

	@Override
	public void create(User user) {
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public User get(Integer userId) {
		Session session = sf.openSession();
		User user = session.get(User.class, userId);
		session.close();
		return user;
	}
	
	@Override
	public List<User> getAll() {
		Session session = sf.openSession();
		List<User> users = session.createQuery("from User").getResultList();
		session.close();
		return users;
	}

	@Override
	public List<User> getByRole(String role) {
		Session session = sf.openSession();
		Query query = session.createQuery("from User where role = :role");
		query.setParameter("role", UserRole.valueOf(role));
		List<User> users = query.getResultList();
		session.close();
		return users;
	}
	
	@Override
	public User getByName(String name) {
		Session session = sf.openSession();
		Query query = session.createQuery("from User where name = :name");
		query.setParameter("name", name);
		List<User> users = query.getResultList();
		session.close();
		
		if (users.isEmpty()){
			return null;
		}
		
		return users.get(0);
	}

	@Override
	public void update(User user) {
		Session session = sf.openSession();
		session.beginTransaction();
		session.update(user);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void delete(User user) {
		Session session = sf.openSession();
		session.beginTransaction();
		session.delete(user);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void delete(Integer userId) {
		Session session = sf.openSession();
		session.beginTransaction();
		Query query = session.createQuery("delete User where id = :id");
		query.setParameter("id", userId);
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

}
