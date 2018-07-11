package org.appstore;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<App, String> {//extends CrudRepository<App, String> {
	
	
}
