
package com.rinit.debugger.server.file.pfille;

import org.w3c.dom.Document;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.IFileDriver;
import com.rinit.debugger.server.utils.XMLReader;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PhysicalFileDriver implements IFileDriver{
	
	private String name;
	private String filePath;
	
	private static final String EXTENTION = "physical_file";
	private static final String FILE_SYSTEM_DIR = "/physical_files/";
	
	@Override
	public void fromDTO(FileDTO dto) {
		PhysicalFileImporter importer = new PhysicalFileImporter(dto.getContent(), this);
		importer.parse();
		this.name = dto.getName(); 
	}
	
	@Override
	public FileDTO toDTO() {
		FileDTO fileDTO = FileDTO.builder()
								 .name(this.name)
								 .extention(EXTENTION)
								 .path(FILE_SYSTEM_DIR)
								 .position(0)
								 .content(this.getContent())
								 .build();
		return fileDTO;
	}
	
	public String getContent() {
		StringBuilder xmlBuilder = new StringBuilder("");
		xmlBuilder.append("<physicalFile>");
		xmlBuilder.append(String.format("<physicalLocation>%s</physicalLocation>", this.filePath));
		xmlBuilder.append("</physicalFile>");
		return xmlBuilder.toString();
	}
	
	public void fromContent(String content) {
		XMLReader reader = new XMLReader(content);
		Document document = reader.getDocument();
		this.filePath = reader.getTagValue("physicalLocation", document.getDocumentElement());
	}
}
