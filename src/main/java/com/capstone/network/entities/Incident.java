package com.capstone.network.entities;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Incident {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "network_element_id")
	private NetworkElement networkElement;

	@ManyToOne
	@JoinColumn(name = "component_id")  
	private Component component;

	private String user;

	private String description; 

	private String issueType;

	private String severity;

	private String priority;

	private String createdTime; 

	private String resolution;

	private String userEmailId;

	public String getUserEmailId() {
		return userEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	private String assignmentGroup;

	private String forwardingmessage;

	private String forwardedTo;

	private boolean forwarded;

	private String Status;

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getForwardedTo() {
		return forwardedTo;
	}

	public void setForwardedTo(String forwardedTo) {
		this.forwardedTo = forwardedTo;
	}

	public String getForwardingmessage() {
		return forwardingmessage;
	}

	public void setForwardingmessage(String forwardingmessage) {
		this.forwardingmessage = forwardingmessage;
	}

	public boolean isForwarded() {
		return forwarded;
	}

	public void setForwarded(boolean forwarded) {
		this.forwarded = forwarded;
	}

	public String getAssignmentGroup() {
		return assignmentGroup;
	}

	public void setAssignmentGroup(String assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NetworkElement getNetworkElement() {
		return networkElement;
	}

	public void setNetworkElement(NetworkElement networkElement) {
		this.networkElement = networkElement;
	}

	public Component getComponent() {
		return component;
	}


	public void setComponent(Component component) {
		this.component = component;
	}

	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
