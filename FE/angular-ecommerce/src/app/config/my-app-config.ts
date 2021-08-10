// export interface MyAppConfig {
// }

export default {

    oidc: {
        clientId: '0oa1fm73gegGWzrQ65d7', //public id of client app
    /*issuer of tokens */    issuer: 'https://dev-57464947.okta.com/oauth2/default', // URL when authorizing with okta Authorization Server
        redirectUri: 'https://localhost:4200/login/callback', // send them here after user logins
        scopes: ['openid', 'profile', 'email'] // scopes provide acces to info about the user(openid: required for authentication requests)
    }

}
