package com.github.artemdvn.webpublisher.services;

import java.util.List;

import com.github.artemdvn.webpublisher.model.App;
import com.github.artemdvn.webpublisher.model.User;

public interface AppService {
	
	public void create(App app);

	public App get(Integer appId);

	public List<App> getAll();
	
	public List<App> getByUser(User user);

	public void update(App app);

	public void delete(App app);

	public void delete(Integer appId);

}
