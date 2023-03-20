import { Router, NavigationStart, NavigationEnd } from '@angular/router';
import { Injectable } from '@angular/core';
import { saveNavigation } from 'event-logs';
import { errorName } from './getNameApp';

@Injectable()
export class RouterEvents {
  constructor(private router: Router) {
    this.subscribeToRouterEvents();
  }

  /**
   * Subscribes to router events to track navigation changes.
   */
  public subscribeToRouterEvents() {
    let navigationstart = '';
    let navigationend = '';
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        navigationstart = this.router.url.toString();
      }
      if (event instanceof NavigationEnd) {
        navigationend = this.router.url.toString();

        this.saveNavigation(navigationstart, navigationend);
      }
    });
  }

  /**
   * Saves the navigation start and end points.
   *
   * @param start - The starting URL of the navigation.
   * @param end - The ending URL of the navigation.
   */
  public saveNavigation(start: string, end: string) {
    let navigation = '';
    if (start === '/') {
      navigation = `{from: "${end}", to: "${end}"}`;
    } else {
      navigation = `{from: "${start}", to: "${end}"}`;
    }
    saveNavigation(navigation);
  }
}
