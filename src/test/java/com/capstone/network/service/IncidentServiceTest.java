//package com.capstone.network.service;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.capstone.network.entities.Incident;
//import com.capstone.network.repository.IncidentRepository;
//
//public class IncidentServiceTest {
//	
//
//    @Mock
//    private IncidentRepository incidentRepository;
//
//    @InjectMocks
//    private IncidentService incidentService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testSaveIncident() { 
//        Incident incidentToSave = IncidentTestData.createIncident(1L, "Group1", "High", "Critical");
//        when(incidentRepository.save(incidentToSave)).thenReturn(incidentToSave);
// 
//        Incident result = incidentService.saveIncident(incidentToSave);
// 
//        assertEquals(incidentToSave, result); 
//    }
//
//    @Test
//    public void testGetIncidentsByAssignmentGroup() { 
//        String assignmentGroup = "Group1";
//        List<Incident> mockIncidents = Arrays.asList(
//                IncidentTestData.createIncident(1L, assignmentGroup, "High", "Critical"),
//                IncidentTestData.createIncident(2L, assignmentGroup, "Medium", "High")
//        );
//        when(incidentRepository.findByAssignmentGroup(assignmentGroup)).thenReturn(mockIncidents);
// 
//        List<Incident> result = incidentService.getIncidentsByAssignmentGroup(assignmentGroup);
// 
//        assertEquals(2, result.size()); 
//    }
// 
//    
//  @Test
//    public void testFindById() { 
//        Long incidentId = 1L;
//        Incident mockIncident = IncidentTestData.createIncident(incidentId, "Group1", "High", "Critical");
//        when(incidentRepository.findById(incidentId)).thenReturn(Optional.of(mockIncident));
// 
//        Optional<Incident> result = incidentService.findById(incidentId);
// 
//        assertEquals(mockIncident, result.orElse(null)); 
//    }
//
//    @Test
//    public void testGetForwardedIncidents() { 
//        String group = "Group1";
//        List<Incident> mockIncidents = Arrays.asList(
//                IncidentTestData.createIncident(1L, "Group1", "High", "Critical"),
//                IncidentTestData.createIncident(2L, "Group1", "Medium", "High")
//        );
//        when(incidentRepository.findByForwardedToAndForwardedIsTrue(group)).thenReturn(mockIncidents);
// 
//        List<Incident> result = incidentService.getForwardedIncidents(group);
// 
//        assertEquals(2, result.size()); 
//    }
//
//    @Test
//    public void testGetHighPriorityHighSeverityIncidents() { 
//        List<Incident> mockIncidents = Arrays.asList(
//                IncidentTestData.createIncident(1L, "Group1", "High", "High"),
//                IncidentTestData.createIncident(2L, "Group2", "High", "High")
//        );
//        when(incidentRepository.findByPriorityAndSeverity("high", "high")).thenReturn(mockIncidents);
// 
//        List<Incident> result = incidentService.getHighPriorityHighSeverityIncidents();
// 
//        assertEquals(2, result.size()); 
//    }
//    
//    
//    
//    @Test
//    public void testGetOtherIncidents() { 
//        List<Incident> mockIncidents = Arrays.asList(
//                IncidentTestData.createIncident(1L, "Group1", "Medium", "Medium"),
//                IncidentTestData.createIncident(2L, "Group2", "Low", "Low")
//        );
//        when(incidentRepository.findByPriorityNotAndSeverityNot("high", "high")).thenReturn(mockIncidents);
// 
//        List<Incident> result = incidentService.getOtherIncidents();
// 
//   //   assertEquals(2, result.size());
//         
//    }
//    
// 
//}
//
