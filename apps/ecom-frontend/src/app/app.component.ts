import { Component, inject, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FaIconLibrary, FaConfig } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from './shared/font-awesome-icons';
import { FooterComponent } from './layout/footer/footer.component';
import { NavbarComponentComponent } from './layout/navbar/navbar.component';

@Component({
  imports: [RouterModule, FooterComponent, NavbarComponentComponent],
  selector: 'ecom-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
  private faIconLibrary = inject(FaIconLibrary);
  private faConfig = inject(FaConfig);

  ngOnInit(): void {
    this.initFontAwesome();
    throw new Error('Method not implemented.');
  }

  private initFontAwesome() {
    this.faConfig.defaultPrefix = 'far';
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }
}
