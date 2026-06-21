import { ApplicationConfig } from '@angular/core';
import { provideServerRendering } from '@angular/platform-server';
import { appConfig as clientAppConfig } from './app.config';

export const appConfig: ApplicationConfig = {
  providers: [
    ...clientAppConfig.providers,
    provideServerRendering()
  ]
};
