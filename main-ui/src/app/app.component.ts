import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: [
    '../assets/css/bootstrap.min.css',
    '../assets/css/bootstrap-grid.min.css',
    '../assets/css/bootstrap-reboot.min.css',
    'app.component.css'
  ]
})
export class AppComponent implements OnInit {
  title = 'airport-management DEV';

  ngOnInit(): void {
    window.document.title = this.title;
  }
}
