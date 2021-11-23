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

	@Query("SELECT `id`, `name`, `extention`, `path`, `position`, `content` FROM `files` WHERE path = :name ORDER BY `position`")
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
			 + "	AND path = :path"
			 )
	List<FileEntity> getFileByPathAndName(
			@Param("name") String name,
			@Param("path") String path			
		);
	
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
			 + "	path LIKE CONCAT(:parentPath, '%')"
			 + "	AND extention = :extention"
			 )
	List<FileEntity> getFilesByParentPathExtention(
			@Param("parentPath") String parentPath,
			@Param("extention") String extention
			);
	
	@Modifying
	@Query("DELETE FROM `files` WHERE `path` LIKE CONCAT(:path, '%')")
	void deleteAllChildrensOfPath(@Param("path") String path);

	@Modifying
	@Query("INSERT INTO `files` (`name`, `extention`, `path`, `position`, `content`) SELECT `name`, `extention`, :destinationPath, `position`, `content` FROM files WHERE `path` = :sourcePath AND `name` = :sourceName;")
	void copyFile(@Param("sourcePath") String sourcePath, @Param("sourceName") String sourceName, @Param("destinationPath") String destinationPath);

	@Modifying
	@Query("INSERT INTO `files` (`name`, `extention`, `path`, `position`, `content`) SELECT `name`, `extention`, REPLACE(`path`, :sourcePath, :destinationPath) , `position`, `content` FROM files WHERE `path` LIKE CONCAT(:sourcePath, '%')")
	void copyAll(@Param("sourcePath") String sourcePath, @Param("destinationPath") String destinationPath);

	@Modifying
	@Query("UPDATE `files` SET `path`= :destinationPath WHERE `path` = :sourcePath AND `name` = :sourceName;")
	void renMoveFile(@Param("sourcePath") String sourcePath, @Param("sourceName") String sourceName, @Param("destinationPath") String destinationPath);

	@Modifying
	@Query("UPDATE `files` SET `path` = REPLACE(`path`, :sourcePath, :destinationPath) WHERE `path` LIKE CONCAT(:sourcePath, '%')")
	void renMoveAll(@Param("sourcePath") String sourcePath, @Param("destinationPath") String destinationPath);

	
	
}


