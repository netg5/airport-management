import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['../bootstrap/bootstrap.min.css', 'app.component.css']
})
export class AppComponent implements OnInit {
  title = 'main-ui-ng';

  ngOnInit(): void {
    window.document.title = this.title;
  }
}
