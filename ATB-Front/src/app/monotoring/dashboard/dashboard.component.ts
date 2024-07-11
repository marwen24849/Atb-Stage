import { Component, OnInit } from '@angular/core';
import { DashboardService } from '../dashboard.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Chart } from 'chart.js/auto';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  tracesList: any = [];
  selectedTrace: any;
  systemHealth: any={
    "status": '',
    "components":{
    "diskSpace": {
      "status": '',
      "details": {
        "total": 0,
        "free": 0,
        "threshold": 0,
        "path": ''
      }
    }
  }
  }
    
    
    ;
  systemCPU: any={
    measurements :[]
  };
  processUptime: any;
  http200Traces: any = [];
  http400Traces: any = [];
  http404Traces: any = [];
  http500Traces: any = [];
  httpDefaultTraces: any = [];
  timeStamp: number=0;

  startIndex: number = 0;
  endIndex: number = 10;
  totalPages: number = 0;
  currentPage: number = 0;

  responseChartData: number[] = [];
  responseChartLabels: string[] = ['200 Response', '404 Response', '400 Response', '500 Response'];


  constructor(private dashboardService: DashboardService) {
    
  }

  ngOnInit(): void {
    this.dashboardService.getHttpTraces().subscribe(
      (response: any) => {
      
        
        this.processTraces(response.exchanges);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    
    this.getCpuUsage();
    this.getSystemHealth();
    this.getProcessUpTime(true);
    
   
    console.log(this.responseChartData);
   
  }
  
  updateResponseChartData(): void {
    this.responseChartData = [
      this.http200Traces.length,
      this.http404Traces.length,
      this.http400Traces.length,
      this.http500Traces.length
    ];
  
   
  }


  createChart(): void {
    const ctx = document.getElementById('myChart') as HTMLCanvasElement;
    const myChart = new Chart(ctx, {
      type: 'doughnut',
  data: {
    datasets: [
      {
        data: this.responseChartData,
        backgroundColor: [
          'rgb(75, 192, 192)',
          'rgb(255, 159, 64)',
          'rgb(54, 162, 235)',
          'rgb(255, 99, 132)',
          
          
        ],
      },
    ],
    labels: this.responseChartLabels,
  },
  
  
  
    });
  }
  

  

  private getCpuUsage(): void {
    this.dashboardService.getSystemCPU().subscribe(
      (response: any) => {
        
        this.systemCPU = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  private getSystemHealth(): void {
    this.dashboardService.getSystemHealth().subscribe(
      (response: any) => {
       
        this.systemHealth = response;
        this.systemHealth.components.diskSpace.details.free = this.formatBytes(this.systemHealth.components.diskSpace.details.free);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  private getProcessUpTime(isUpdateTime: boolean): void {
    this.dashboardService.getProcessUptime().subscribe(
      (response: any) => {
       
        this.timeStamp = Math.round(response.measurements[0].value);
        this.processUptime = this.formateUptime(this.timeStamp);
        if (isUpdateTime) {
          this.updateTime();
        }

      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
  private getTraces(): void {
    this.dashboardService.getHttpTraces().subscribe(
      (response: any) => {
      
        this.processTraces(response.traces);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  private processTraces(traces: any): void {
    this.tracesList = traces;
    
    this.tracesList.forEach((trace: { response: { status: any; }; }) => {
     
      switch (trace.response.status) {
        case 200:
          this.http200Traces.push(trace);
          break;
        case 400:
          this.http400Traces.push(trace);
          break;
        case 500:
          this.http500Traces.push(trace); // Correction ici
          break;
        case 404:
          this.http404Traces.push(trace);
          break;
        default:
          this.httpDefaultTraces.push(trace);
          break;
      }
    });
    this.calculateTotalPages();
    this.calculateCurrentPage();
    this.updateResponseChartData();
    this.createChart()
}


  onRefreshData() {
    this.http200Traces = [];
    this.http400Traces = [];
    this.http404Traces = [];
    this.http500Traces = [];
    this.getTraces();
    this.getCpuUsage();
    this.getSystemHealth();
    this.getProcessUpTime(false);
  }

  onSelectTrace(trace: any) {
    this.selectedTrace = trace;
    var btn = document.getElementById('trace-modal');
    if(btn != null)
      btn.click();

  }

  formatBytes(bytes: any): string {
    if (bytes === 0) {
      return "0 Bytes";
    }
    const k = 1024;
    const dm = 2 < 0 ? 0 : 2;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
  }

  formateUptime(timestamp: number): string {
    const hours = Math.floor(timestamp / 60 / 60);
    const minutes = Math.floor(timestamp / 60) - (hours * 60);
    const seconds = timestamp % 60;
    return hours.toString().padStart(2, '0') + 'h' +
      minutes.toString().padStart(2, '0') + 'm' + seconds.toString().padStart(2, '0') + 's';
  }

   
  updateTime(): void {
    setInterval(() => {
      this.processUptime = this.formateUptime(this.timeStamp + 1);
      this.timeStamp++;
    }, 1000);
  }

  private formatDate(date: Date): string {
    const dd = date.getDate();
    const mm = date.getMonth() + 1;
    const year = date.getFullYear();
    if (dd < 10) {
      const day = `0${dd}`;
    }
    if (mm < 10) {
      const month = `0${mm}`;
    }
    return `${mm}/${dd}/${year}`;
  }


  getItems(): any[] {
    return this.tracesList.slice(this.startIndex, this.endIndex);
  }

 
  nextPage() {
    this.startIndex += 10;
    this.endIndex += 10;
    this.calculateCurrentPage();
    
  }

  prevPage() {
    this.startIndex -= 10;
    this.endIndex -= 10;
    this.calculateCurrentPage();
  }

  calculateTotalPages() {
    this.totalPages = Math.ceil(this.tracesList.length / 10);
  }

  calculateCurrentPage() {
    const elementsPerPage = 10; 
    this.currentPage = Math.min(Math.ceil(this.endIndex / elementsPerPage), Math.ceil(this.tracesList.length / elementsPerPage));
  }

  


}
