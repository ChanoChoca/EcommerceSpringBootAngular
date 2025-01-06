import { mergeApplicationConfig, ApplicationConfig } from '@angular/core';
import { provideServerRendering } from '@angular/platform-server';
import { provideServerRoutesConfig } from '@angular/ssr';
import { appConfig } from './app.config';
import { serverRoutes } from './app.routes.server';
import { UNIVERSAL_PROVIDERS } from '@ng-web-apis/universal';

const serverConfig: ApplicationConfig = {
  providers: [
    provideServerRendering(),
    // provideServerRoutesConfig(serverRoutes),
    UNIVERSAL_PROVIDERS
  ],
};

export const config = mergeApplicationConfig(appConfig, serverConfig);
