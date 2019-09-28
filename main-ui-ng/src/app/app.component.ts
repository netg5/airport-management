import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: [
    '../bootstrap/bootstrap.min.css',
    '../bootstrap/bootstrap-grid.min.css',
    '../bootstrap/bootstrap-reboot.min.css',
    'app.component.css'
  ]
})
export class AppComponent implements OnInit {
  title = 'airport-management DEV';

  ngOnInit(): void {
    window.document.title = this.title;
  }
}
