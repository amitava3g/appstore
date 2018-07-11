package org.appstore.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.appstore.App;
import org.appstore.MasterData;
import org.appstore.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AppstoreController {

	@Autowired
	private AppService appsService;

	@Autowired
	private Environment env;

	@RequestMapping("/apps")
	public List<App> getAllApps() {
		return appsService.getAllApps();
	}

	@RequestMapping("/apps/{id}")
	public App getApp(@PathVariable String id) {
		return appsService.getApp(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/apps")
	public void addApp(@RequestBody App app) {
		app.setId(UUID.randomUUID().toString());
		appsService.addApp(app);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/apps/{id}")
	public void addApp(@RequestBody App app, @PathVariable String id) {
		appsService.updateApp(app, id);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/apps/{id}")
	public void deleteApp(@PathVariable String id) {
		appsService.deleteApp(id);
		// Delete actual file
		try  {
			Path filePath = Paths.get(env.getProperty("file.store.location") + "/" + id + ".ipa");
			Files.delete(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public boolean loginAppstore(@RequestBody Map<String, Object> payload) {
		if (env.getProperty("appstore.username").equals((String) payload.get("username"))
				&& env.getProperty("appstore.password").equals((String) payload.get("password"))) {
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping(value = "/masterdata")
	public List<MasterData> getAllMasterData() {
		return appsService.getAllMasterData();
	}

	@RequestMapping(value = "/download/{fileID:.+}", produces = "application/x-plist")
	public String getPlistFile(@PathVariable String fileID) {
		String fileData = null;
		try {
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			File file = new File(classLoader.getResource("data/template.plist").getFile());
			// File is found
			System.out.println("File Found : " + file.exists());
			// Read File Content
			fileData = new String(Files.readAllBytes(file.toPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		App app = appsService.getApp(fileID.replace(".plist", ""));
		if (app != null) {
			fileData = fileData.replaceAll("##URL##", env.getProperty("app.url"));
			MasterData masterData = appsService.getMasterData(app.getType());
			if (masterData != null) {
				fileData = fileData.replaceAll("##COMPANY_NAME##", masterData.getCompanyName())
						.replaceAll("##APP_NAME##", masterData.getAppName())
						.replaceAll("##BUNDLE_ID##", masterData.getBundleID()).replaceAll("##APP_TYPE##", app.getType())
						.replaceAll("##ID##", fileID.replace(".plist", ""));
			}
		}
		return fileData;
	}
	
	@RequestMapping(value = "/downloadpath/{fileID:.+}", produces = "application/octet-stream")
	public ResponseEntity<InputStreamResource> getIPAFile(@PathVariable String fileID) {
		HttpHeaders headers = null;
		File file = null;
		InputStreamResource resource = null;
		try {
			file = new File(env.getProperty("file.store.location") + "/" + fileID);
	        headers = new HttpHeaders();
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	        headers.add("Pragma", "no-cache");
	        headers.add("Expires", "0");
			resource = new InputStreamResource(new FileInputStream(file));	    
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(file.length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadFile(@RequestParam("uploadfile") MultipartFile uploadfile,
			@RequestParam(value = "date") String date, @RequestParam(value = "description") String description,
			@RequestParam(value = "type") String type) {
		try {
			String filename = UUID.randomUUID().toString();
			String directory = env.getProperty("file.store.location");
			String filepath = Paths.get(directory, filename + ".ipa").toString();
			// Save the file locally
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
			stream.write(uploadfile.getBytes());
			stream.close();
			App app = new App(filename, date, description, type);
			appsService.addApp(app);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
