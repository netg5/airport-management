import {Component, OnInit} from "@angular/core";

@Component({
  selector: 'app-footer',
  styleUrls: ['footer.css'],
  templateUrl: './footer.component.html',
})
export class FooterComponent implements OnInit {

  private year: number;

  constructor() {
    this.year = new Date().getFullYear();
  }

  ngOnInit(): void {
  }
}
