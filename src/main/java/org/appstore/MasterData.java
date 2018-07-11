package org.appstore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "master_data", catalog = "appstore")
public class MasterData {

	@Id
	@Column(name = "id")
	private String id;
	private String bundleID;
	private String companyName;
	private String appName;
	private String appType;
	private String appTypeDisplayName;
	
	public MasterData() {

	}
	
	public MasterData(String id, String bundleID, String companyName, String appName, String appType,
			String appTypeDisplayName) {
		super();
		this.id = id;
		this.bundleID = bundleID;
		this.companyName = companyName;
		this.appName = appName;
		this.appType = appType;
		this.appTypeDisplayName = appTypeDisplayName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBundleID() {
		return bundleID;
	}
	public void setBundleID(String bundleID) {
		this.bundleID = bundleID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getAppTypeDisplayName() {
		return appTypeDisplayName;
	}
	public void setAppTypeDisplayName(String appTypeDisplayName) {
		this.appTypeDisplayName = appTypeDisplayName;
	}
}
