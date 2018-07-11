package org.appstore.service;

import java.util.ArrayList;
import java.util.List;

import org.appstore.App;
import org.appstore.AppRepository;
import org.appstore.MasterData;
import org.appstore.MasterDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService {
	
	@Autowired
	private AppRepository appRepository;
	
	@Autowired
	private MasterDataRepository masterDataRepository;

	public List<App> getAllApps() {
		List<App> apps = new ArrayList<App>();
		appRepository.findAll().forEach(apps::add);
		return apps;
	}
	
	public App getApp(String id) {
		return appRepository.findOne(id);
	}
	
	public void addApp(App app) {
		appRepository.save(app);
	}
	
	public void updateApp(App app, String id) {
		appRepository.save(app);
	}
	
	public void deleteApp(String id) {
		appRepository.delete(id);
	}
	
	public List<MasterData> getAllMasterData() {
		List<MasterData> masterDatas = new ArrayList<MasterData>();
		masterDataRepository.findAll().forEach(masterDatas::add);
		return masterDatas;
	}
	
	public MasterData getMasterData(String appType) {
		return masterDataRepository.findByAppType(appType).get(0);
	}
}
