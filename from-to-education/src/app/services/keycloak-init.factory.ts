import {KeycloakService} from 'keycloak-angular';
import {environment} from '../../environments/environment';

export function initializeKeycloak(
  keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: environment.keycloakUrl,
        realm: environment.keycloak_realm,
        clientId: environment.keycloak_client_id,
      },
      initOptions: {
        onLoad: 'check-sso',
       // onLoad: 'login-required',
        checkLoginIframe: false,
      },
      bearerExcludedUrls: []
    });
}
