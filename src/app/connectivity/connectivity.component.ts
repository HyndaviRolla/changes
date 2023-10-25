 
import { Component } from '@angular/core';
import { IncidentService } from '../incident.service';
import { Incident } from '../incident';
import { ForwardingRequest } from '../forwadingrequest';

@Component({
  selector: 'app-connectivity',
  templateUrl: './connectivity.component.html',
  styleUrls: ['./connectivity.component.css']
})

export class ConnectivityComponent {
  
  showIncidentList: boolean = false;
  showForwardedList: boolean = false;
  showHighPriorityList: boolean = false;
  showOtherList: boolean = false;

  toggleIncidentList() {
    this.showIncidentList = !this.showIncidentList;
  }

  toggleForwardedList() {
    this.showForwardedList = !this.showForwardedList;
  }
  buttonClicked = true;
 
    
  toggleHighPriorityList() {
    this.showHighPriorityList = !this.showHighPriorityList;
    this.buttonClicked = !this.buttonClicked;
  
  } 
  

  incidents: Incident[] = [];
  forwardedIncidents: Incident[] = [];
  availableGroups: string[] = [];
  selectedGroup: string | null = null;
  forwardingMessage: string = '';
  highPriorityHighSeverityIncidents: Incident[] = [];
  

  constructor(private incidentService: IncidentService) { }

  ngOnInit() {
    this.fetchIncidents('Connectivity Issues');
    this.fetchForwardedIncidents('Connectivity Issues');
    this.loadAvailableGroups();
    this.loadIncidents();
  }

  loadIncidents() {
    this.incidentService.getHighPriorityHighSeverityIncidents().subscribe(
      incidents => {
        this.highPriorityHighSeverityIncidents = incidents;
      },
      error => {
        console.error('Error loading high priority high severity incidents', error);
      }
    );

    
  }

  forwardIncident(incident: Incident) {
    if (!this.selectedGroup) {
      console.error('Please select a group to forward the incident.');
      return;
    }

    if (!this.forwardingMessage) {
      console.error('Please enter a forwarding message.');
      return;
    }

    const forwardingRequest: ForwardingRequest = {
      targetGroup: this.selectedGroup,
      message: this.forwardingMessage,
    };

    this.incidentService.forwardIncident(incident.id!, forwardingRequest).subscribe(
      (forwardedIncidents) => {
        console.log(
          `Incident forwarded successfully to ${this.selectedGroup} group`
        );
        this.fetchIncidents('Connectivity Issues');
      },
      (error) => {
        console.error('Error forwarding incident:', error);
      }
    );
  }

  fetchForwardedIncidents(group: string) {

    this.incidentService.getForwardedIncidents(group).subscribe(
      (forwardedIncidents) => {
        this.forwardedIncidents = forwardedIncidents;

      },
    );
  }
  submitResolution(incident: Incident) {
    if (incident.id !== null) {
    this.incidentService.updateIncidentResolution(incident.id, incident.resolution).subscribe(
      () => {
        console.log('Resolution submitted successfully');
        // You can optionally reload the incidents after submitting resolution
        this.fetchIncidents('HardwareGroup');
      },
      error => {
        console.error('Error submitting resolution', error);
      }
    );
  }
}

  fetchIncidents(group: string) {
    this.incidentService.getIncidentsByAssignmentGroup(group).subscribe(
      (incidents) => {
        this.incidents = incidents;

      },
      (error) => {
        console.error(`Error fetching incidents for ${group} group`, error);
      }
    );
  }

  private loadAvailableGroups() {
    this.availableGroups = ['SoftwareGroup', 'HardwareGroup', 'Security Operations Center'];
  }
}