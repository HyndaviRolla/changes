package com.capstone.network.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.capstone.network.DTO.ForwardingRequest;
import com.capstone.network.entities.Component;
import com.capstone.network.entities.Incident;
import com.capstone.network.entities.NetworkElement;
import com.capstone.network.service.ComponentService;
import com.capstone.network.service.IncidentService;
import com.capstone.network.service.NetworkElementService;
import com.capstone.network.service.UserService; 
@RestController
@RequestMapping("/api/incidents")
@CrossOrigin  
public class IncidentController {

	@Autowired IncidentService incidentService;

	@Autowired ComponentService componentService;

	@Autowired UserService userService;


	@Autowired NetworkElementService networkElementService;


	@PostMapping
	public ResponseEntity<Incident> createIncident(@RequestBody Incident incident) {
		try {

			if (incident == null || incident.getNetworkElement() == null || incident.getComponent() == null) {
				return ResponseEntity.badRequest().build();
			} 
			String issueType = incident.getIssueType();
			String assignmentGroup = determineAssignmentGroup(issueType);
			incident.setAssignmentGroup(assignmentGroup);
			incident.setForwarded(false);
			incident.setStatus("Assigned To "+assignmentGroup);
			incident.setPriority(incident.getPriority());
			incident.setSeverity(incident.getSeverity());
			incident.setUser(incident.getUser());
			incident.setUserEmailId(incident.getUserEmailId());
			LocalDateTime now = LocalDateTime.now(); 
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = now.format(formatter);

			incident.setCreatedTime(formattedDateTime);
			Incident savedIncident = incidentService.saveIncident(incident);


			return ResponseEntity.ok(savedIncident);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	} 

	private String determineAssignmentGroup(String issueType) {

		if ("hardware".equalsIgnoreCase(issueType)) {
			return "HardwareGroup";
		} else if ("software".equalsIgnoreCase(issueType)) {
			return "SoftwareGroup";
		}
		else if("Connectivity Support".equalsIgnoreCase(issueType))
			return "Connectivity Issues";
		else if ("Security Issues".equalsIgnoreCase(issueType))
			return "Security Operations Center";
		else {
			return "DefaultGroup";
		}

	}
	@GetMapping("/network-elements")
	public ResponseEntity<List<NetworkElement>> getNetworkElements(@RequestParam String search) {
		try { 
			List<NetworkElement> networkElements = networkElementService.searchNetworkElements(search);

			return ResponseEntity.ok(networkElements);
		} catch (Exception e) { 
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	} 
	
	@GetMapping("/components/{networkElementId}")
	public ResponseEntity<List<Component>> getComponents(@PathVariable Long networkElementId) {
		try { 
			List<Component> components = componentService.getComponentsByNetworkElement(networkElementId);

			return ResponseEntity.ok(components);
		} catch (Exception e) { 
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	} 
	@GetMapping("/assigned-group/{assignmentGroup}")
	public ResponseEntity<List<Incident>> getIncidentsByAssignmentGroup(@PathVariable String assignmentGroup) {
		try {
			List<Incident> incidents = incidentService.getIncidentsByAssignmentGroup(assignmentGroup);
			return ResponseEntity.ok(incidents);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	} 
	@GetMapping("/user/{userName}")
	public ResponseEntity<List<Incident>> getIncidentsByUserName(@PathVariable String userName) {
		try {
			List<Incident> incidents = incidentService.getIncidentsByUser(userName);
			return ResponseEntity.ok(incidents);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	@PostMapping("/forward/{incidentId}")
	public ResponseEntity<Incident> forwardIncident(
			@PathVariable Long incidentId,
			@RequestBody ForwardingRequest forwardingRequest) {
		try {
			Incident originalIncident = incidentService.findById(incidentId)
					.orElseThrow(() -> new RuntimeException("Incident not found"));
			originalIncident.setForwardingmessage(forwardingRequest.getMessage());
			originalIncident.setForwardedTo(forwardingRequest.getTargetGroup());
			originalIncident.setForwarded(true);
			originalIncident.setStatus("Forwaded To "+forwardingRequest.getTargetGroup());
			originalIncident = incidentService.saveIncident(originalIncident);

			return ResponseEntity.ok(originalIncident);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	@GetMapping("/forwarded/{group}")
	public ResponseEntity<List<Incident>> getForwardedIncidents(@PathVariable String group) {
		try {
			List<Incident> forwardedIncidents = incidentService.getForwardedIncidents(group);
			return ResponseEntity.ok(forwardedIncidents);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/high-priority-high-severity")
	public ResponseEntity<List<Incident>> getHighPriorityHighSeverityIncidents() {
		try {
			List<Incident> highPriorityHighSeverityIncidents = incidentService.getHighPriorityHighSeverityIncidents();
			return ResponseEntity.ok(highPriorityHighSeverityIncidents);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/other-incidents")
	public ResponseEntity<List<Incident>> getOtherIncidents() {
		try {
			List<Incident> otherIncidents = incidentService.getOtherIncidents();
			return ResponseEntity.ok(otherIncidents);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	} 

	@PutMapping("/incidents/{id}/resolution")
	public ResponseEntity<Void> updateIncidentResolution(@PathVariable Long id,@RequestBody ResolutionRequest resolutionRequest) {
		try {
			incidentService.updateIncidentResolution(id, resolutionRequest.getResolution());

			return ResponseEntity.ok().build();
		} 
		catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
		
}

