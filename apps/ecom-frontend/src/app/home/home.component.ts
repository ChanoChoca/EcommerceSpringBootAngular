import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FeaturedComponent } from './featured/featured.component';

@Component({
  selector: 'home',
  imports: [CommonModule, RouterLink, FeaturedComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {}
