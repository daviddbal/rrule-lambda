package net.balsoftware.bean;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RRule {

	private String rruleContent;
	private String dtstartContent;
	private int maxRecurrences;
	private LocalDateTime created;
	private String ipAddress;
	
	public RRule()
	{
		super();
		created = LocalDateTime.now();
	}
	
	public RRule(String rruleContent, String dtstartContent, int maxRecurrences, String ipAddress) {
		this();
		this.rruleContent = rruleContent;
		this.dtstartContent = dtstartContent;
		this.maxRecurrences = maxRecurrences;
		this.ipAddress = ipAddress;
	}
	@Override
	public String toString() {
		return "RRule [rruleContent=" + rruleContent + ", dtstartContent=" + dtstartContent + ", maxRecurrences="
				+ maxRecurrences + ", created=" + created + ", ipAddress=" + ipAddress + "]";
	}

	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public String getRruleContent() {
		return rruleContent;
	}
	public void setRruleContent(String rruleContent) {
		this.rruleContent = rruleContent;
	}
	public String getDtstartContent() {
		return dtstartContent;
	}
	public void setDtstartContent(String dtstartContent) {
		this.dtstartContent = dtstartContent;
	}
	public int getMaxRecurrences() {
		return maxRecurrences;
	}
	public void setMaxRecurrences(int maxRecurrences) {
		this.maxRecurrences = maxRecurrences;
	}
}
