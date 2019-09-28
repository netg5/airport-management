import {NgModule} from "@angular/core";
import {HeaderComponent} from "./header";
import {FooterComponent} from "./footer";
import {RouterModule} from "@angular/router";

const DECLARATIONS = [
  HeaderComponent,
  FooterComponent
];

@NgModule({
  imports: [
    RouterModule,
    /*TODO*/
  ],
  declarations: [...DECLARATIONS],
  exports: [...DECLARATIONS]
})
export class LayoutModule {
}
