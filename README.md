## Introduction

Simple oAuth2 client application to test the Cognito oAuth2 provider using the authorization code grant.

Uses the following URL structure 

```
cognito-idp.{region}.amazonaws.com/{pool_id}/.well-known/openid-configuration
```

For example :
https://cognito-idp.eu-central-1.amazonaws.com/eu-central-1_7s0YJek0R/.well-known/openid-configuration


## Configuration

```
spring:
  security:
    oauth2:
      client:
        registration:
          cognito:
            clientId: xxx
            clientSecret: xxx
            scope: openid
            redirect-uri: http://localhost:8080/login/oauth2/code/cognito
            clientName: spring-boot-cognito-oauth2
        provider:
          cognito:
            issuerUri: https://cognito-idp.eu-central-1.amazonaws.com/eu-central-1_laH88DAFr
            user-name-attribute: cognito:username

```

## JWT token ID Token

```
{
  "at_hash": "E3ve07PY8tRkR3WHZuiOIg",
  "sub": "87071b8c-61aa-454b-8892-65547685bdd8",
  "iss": "https://cognito-idp.eu-central-1.amazonaws.com/eu-central-1_7s0YJek0R",
  "cognito:username": "87071b8c-61aa-454b-8892-65547685bdd8",
  "origin_jti": "8aee795f-c950-4153-a015-f3abb2dd2f80",
  "aud": "2igeae1dud2fqj8eh6j1i4npib",
  "token_use": "id",
  "auth_time": 1700906803,
  "exp": 1700910403,
  "iat": 1700906803,
  "jti": "01a4d806-5d5b-4779-beee-24948e05297b",
  "email": "ddewaele@gmail.com"
}
```

## JWT Access Token

```
{
  "sub": "87071b8c-61aa-454b-8892-65547685bdd8",
  "iss": "https://cognito-idp.eu-central-1.amazonaws.com/eu-central-1_7s0YJek0R",
  "version": 2,
  "client_id": "2igeae1dud2fqj8eh6j1i4npib",
  "origin_jti": "63d284ff-fe6b-4991-8a04-d70660d2cf36",
  "token_use": "access",
  "scope": "openid profile email",
  "auth_time": 1700906661,
  "exp": 1700910261,
  "iat": 1700906661,
  "jti": "476b4d5a-950f-4afd-97fe-60d0848f67a3",
  "username": "87071b8c-61aa-454b-8892-65547685bdd8"
}
```