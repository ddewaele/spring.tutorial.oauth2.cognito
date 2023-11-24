## Introduction

Simple oAuth2 client application to test the Cognito oAuth2 provider.

Uses the following URL structure 

```
cognito-idp.{region}.amazonaws.com/{pool_id}/.well-known/openid-configuration
```

For example :
https://cognito-idp.eu-central-1.amazonaws.com/eu-central-1_laH88DAFr/.well-known/openid-configuration


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

## JWT token

```
{
  "at_hash": "gnD1Pjr7Y370xCgETeVdDQ",
  "sub": "cae80ea7-bd42-4058-9bf1-d8509e0f77ff",
  "iss": "https://cognito-idp.eu-central-1.amazonaws.com/eu-central-1_laH88DAFr",
  "cognito:username": "cae80ea7-bd42-4058-9bf1-d8509e0f77ff",
  "nonce": "Zph6OIsn-KozdVxfab73ud2gzzkw_wt0MOBzPX8MUdE",
  "origin_jti": "298bf55d-74d5-48bc-a76e-e75ece0bf9c0",
  "aud": "4ieujcvgvbvnh20o8akiaqdtsa",
  "token_use": "id",
  "auth_time": 1680729385,
  "exp": 1680732985,
  "iat": 1680729385,
  "jti": "371512b8-dc66-4a89-9789-886072553fdc",
  "email": "your@email.com"
}
```