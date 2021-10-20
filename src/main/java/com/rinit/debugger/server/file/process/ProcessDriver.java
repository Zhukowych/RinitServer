package com.rinit.debugger.server.file.process;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.rinit.debugger.server.client.IClient;
import com.rinit.debugger.server.core.Extentions;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.IFileDriver;
import com.rinit.debugger.server.file.executable.ExecutableDriver;
import com.rinit.debugger.server.utils.XMLReader;

public class ProcessDriver implements IFileDriver, Runnable {

	private FileDTO dto;
	
	private int pid;
	private String executableName;
	private ExecutableDriver executable;
	private Thread executionThread;
	private IClient client;
	
	public ProcessDriver() {}
	
	public ProcessDriver(int pid, String executableName) {
		this.pid =  pid;
		this.executableName = executableName;
	}
	
	@Override
	public void fromDTO(FileDTO dto) {
		this.dto = dto;
		XMLReader reader = new XMLReader(dto.getContent());
		Document document = reader.getDocument();
		this.pid = Integer.parseInt(reader.getTagValue("pid", (Element)document.getElementsByTagName("process").item(0)));
		this.executableName = reader.getTagValue("executableName", (Element)document.getElementsByTagName("process").item(0));
	}

	@Override
	public FileDTO toDTO() {
		FileDTO dto = FileDTO.builder()
				.name(String.valueOf(this.pid))
				.path("/run/proc/")
				.extention(Extentions.PROCESS)
				.content(this.getContent())
				.build();
		return dto;
	}

	@Override
	public String getContent() {
		StringBuilder xml = new StringBuilder("");
		xml.append("<process>");
		xml.append(String.format("<pid>%d</pid>", this.pid));
		xml.append(String.format("<executableName>%s</executableName>", this.executableName));
		xml.append("</process>");
		return xml.toString();
	}
	
	public int getPid() {
		return this.pid;
	}
	
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public String getExecutableName() {
		return this.executableName;
	}
	
	public void setExecutableName(String executableName) {
		this.executableName = executableName;
	}
	
	public ExecutableDriver getExecutable() {
		return this.executable;
	}
	
	public void setExecutable(ExecutableDriver executable) {
		this.executable = executable;
		this.executableName = this.executable.getName();
	}
	
	public void setClient(IClient client) {
		this.client = client;
	}

	public void start() {	
		this.executionThread = new Thread(this);
		this.executionThread.start();
	}
	
	@Override
	public void run() {
		this.executable.setClient(this.client);
		this.executable.run();
	}
	
}
