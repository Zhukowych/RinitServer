package com.rinit.debugger.server.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rinit.debugger.server.entity.FileEntity;

@Repository
public interface FileRepository extends CrudRepository<FileEntity, Long>{

	@Query("SELECT `id`, `name`, `extention`, `path`, `position`, `content` FROM `files` WHERE path = :name")
	List<FileEntity> getFilesByPath(@Param("name") String name);
	
	@Query("SELECT `id`, `name`, `extention`, `path`, `position`, `content` FROM `files` WHERE path = :name AND extention = :extention")
	List<FileEntity> getFilesByPathExtention(@Param("name") String name, @Param("extention") String extention);

	@Query("SELECT "
		 + "	`id`,"
		 + "	`name`,"
		 + "	`extention`,"
		 + "	`path`,"
		 + "	`position`,"
		 + "	`content` "
		 + "FROM "
		 + "	`files`"
		 + "WHERE "
		 + "	name = :name"
		 + "	AND extention = :extention"
		 + "	AND path = :path"
		 )
	List<FileEntity> getFilesByNameExtentionPath(
			@Param("name") String name,
			@Param("extention") String extention,
			@Param("path") String path
		);
	
	@Modifying
	@Query("DELETE FROM `files` WHERE path LIKE ':path%'")
	void deleteAllChildrensOfPath(@Param("path") String path);
}
