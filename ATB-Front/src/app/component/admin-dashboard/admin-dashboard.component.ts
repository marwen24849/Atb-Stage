import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js/auto';
import { AdminDashboardService } from '../../service/admin-dashboard.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit {

  constructor(private adminDashboardService: AdminDashboardService) { }

  ngOnInit(): void {
    this.loadStatistics();
  }

  loadStatistics(): void {
    this.adminDashboardService.getStatistics().subscribe(data => {
      this.createCreditRequestsChart(data.creditRequestsStats);
      this.createCreditsChart(data.creditsStats);
    });
  }

  createCreditRequestsChart(data: any): void {
    new Chart('creditRequestsChart', {
      type: 'bar',
      data: {
        labels: Object.keys(data),
        datasets: [{
          label: 'Nombre de demandes de crédit',
          data: Object.values(data),
          backgroundColor: 'rgba(54, 162, 235, 0.2)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  createCreditsChart(data: any): void {
    new Chart('creditsChart', {
      type: 'line',
      data: {
        labels: Object.keys(data),
        datasets: [{
          label: 'Montant des crédits',
          data: Object.values(data),
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }
}