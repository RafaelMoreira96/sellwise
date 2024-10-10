import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import localePt from '@angular/common/locales/pt';

import { AppModule } from './app/app.module';
import { registerLocaleData } from '@angular/common';

registerLocaleData(localePt);

platformBrowserDynamic().bootstrapModule(AppModule, {
  ngZoneEventCoalescing: true
})
  .catch(err => console.error(err));
