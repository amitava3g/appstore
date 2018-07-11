package org.appstore;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterDataRepository extends JpaRepository<MasterData, String> {
	
	List<MasterData> findByAppType(String appType);
	
}
