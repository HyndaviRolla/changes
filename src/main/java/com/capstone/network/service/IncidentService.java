package com.capstone.network.service; 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.capstone.network.entities.Incident;
import com.capstone.network.repository.IncidentRepository;


@Service
public class IncidentService {
	@Autowired
	private IncidentRepository incidentRepository;
	@Autowired
	private EmailServiceImpl emailService;

	public Incident saveIncident(Incident incident) {
		return incidentRepository.save(incident);
	} 

	public List<Incident> getIncidentsByAssignmentGroup(String assignmentGroup) {

		return incidentRepository.findByAssignmentGroup(assignmentGroup);
	}  
	public Optional<Incident> findById(Long id) {
		return incidentRepository.findById(id);
	} 
	public List<Incident> getIncidentsByUser(String userName) {
		return incidentRepository.findByUser(userName);
	}

	public List<Incident> getForwardedIncidents(String group) {
		return incidentRepository.findByForwardedToAndForwardedIsTrue(group);
	} 
	public List<Incident> getHighPriorityHighSeverityIncidents() { 
		return incidentRepository.findByPriorityAndSeverity("high", "high");
	}

	public List<Incident> getOtherIncidents() { 
		return incidentRepository.findByPriorityNotAndSeverityNot("high"," high");
	} 

	public void updateIncidentResolution(Long incidentId, String resolution) throws NotFoundException {
		Optional<Incident> optionalIncident = incidentRepository.findById(incidentId);

		optionalIncident.ifPresent( incident-> {
			incident.setResolution(resolution);
			incident.setStatus("Incident Resolved");
			incidentRepository.save(incident);
			emailService.sendResolutionMail(incidentId,incident.getUserEmailId(), resolution);
		});

		if (optionalIncident.isEmpty()) {
			throw new NotFoundException();
		}
	}

//	private void sendResolutionEmail(Long incidentId,String userEmail, String resolution) {
//		emailService.sendSimpleMail(userEmail, resolution);
//	}

} 



