package com.github.artemdvn.webpublisher.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.artemdvn.webpublisher.model.App;
import com.github.artemdvn.webpublisher.model.User;

@Repository
public class AppDaoImpl implements AppDao {
	
	@Autowired
    private SessionFactory sf;
	
	private AppDaoImpl() {
	}

	@Override
	public void create(App app) {
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(app);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public App get(Integer appId) {
		Session session = sf.openSession();
		App app = session.get(App.class, appId);
		session.close();
		return app;
	}

	@Override
	public List<App> getAll() {
		Session session = sf.openSession();
		List<App> apps = session.createQuery("from App").getResultList();
		session.close();
		return apps;
	}
	
	@Override
	public List<App> getByUser(User user) {
		Session session = sf.openSession();
		Query query = session.createQuery("from App where user = :user");
		query.setParameter("user", user);
		List<App> apps = query.getResultList();
		session.close();
		return apps;
	}

	@Override
	public void update(App app) {
		Session session = sf.openSession();
		session.beginTransaction();
		session.update(app);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void delete(App app) {
		Session session = sf.openSession();
		session.beginTransaction();
		session.delete(app);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void delete(Integer appId) {
		Session session = sf.openSession();
		session.beginTransaction();
		Query query = session.createQuery("delete App where id = :id");
		query.setParameter("id", appId);
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

}
