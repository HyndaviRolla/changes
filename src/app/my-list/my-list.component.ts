import { Component } from '@angular/core';
import { IncidentService } from '../incident.service';
import { AuthService } from '../auth.service';
import { Incident } from '../incident';

@Component({
  selector: 'app-my-list',
  templateUrl: './my-list.component.html',
  styleUrls: ['./my-list.component.css']
})
export class MyListComponent {
  
  myList:Incident[]=[]
  constructor(private incidentService: IncidentService,private authService: AuthService) {}


  ngOnInit(): void { 
    this.loadIncidents();
  }


  loadIncidents() {
    const currentUser: string | null = this.authService.getCurrentUser();
    const role:string | null = this.authService.getCurrentUserRole();
    console.log(role);
    console.log(currentUser);
    this.incidentService.getIncidentsByUserName(currentUser!)
      .subscribe((data: any[]) => {
        this.myList = data;
      });
  }

}