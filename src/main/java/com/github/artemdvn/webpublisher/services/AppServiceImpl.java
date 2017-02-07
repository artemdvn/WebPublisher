package com.github.artemdvn.webpublisher.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.artemdvn.webpublisher.dao.AppDao;
import com.github.artemdvn.webpublisher.model.App;
import com.github.artemdvn.webpublisher.model.User;

@Service
@Transactional
public class AppServiceImpl implements AppService {
	
	@Autowired
    private AppDao appDao;
	
	private AppServiceImpl() {
	}

	@Override
	public void create(App app) {
		appDao.create(app);
	}

	@Transactional(readOnly = true)
	@Override
	public App get(Integer appId) {
		return appDao.get(appId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<App> getAll() {
		return appDao.getAll();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<App> getByUser(User user) {
		return appDao.getByUser(user);
	}

	@Override
	public void update(App app) {
		appDao.update(app);
	}

	@Override
	public void delete(App app) {
		appDao.delete(app);
	}

	@Override
	public void delete(Integer appId) {
		appDao.delete(appId);
	}

}
