## Introduction

This is a sample Spring Boot application demonstrating how to create OAuth2-protected endpoints using Spring Security. 
The application includes several endpoints with different access restrictions and uses Amazon Cognito as an OAuth2 provider for authentication and authorization.
Uses the oAuth2 Authorization Code Grant type for authentication.

Exposes some endpoints where authorization is enforced using roles

Here's a Markdown table for the provided endpoint descriptions:

| Endpoint        | Description                                        | Method | Access                    | Example                          |
|-----------------|----------------------------------------------------|--------|---------------------------|----------------------------------|
| `/public`       | Public endpoint accessible to all users.         | GET    | No authentication required | [http://localhost:8080/public](http://localhost:8080/public)             |
| `/authenticated`| Authenticated endpoint displaying the user's principal. | GET | Requires authentication  | [http://localhost:8080/authenticated](http://localhost:8080/authenticated) |
| `/employee`     | Employee-only endpoint displaying the user's principal. | GET | Requires "EMPLOYEE" role | [http://localhost:8080/employee](http://localhost:8080/employee)       |
| `/admin`        | Admin-only endpoint displaying the user's principal.    | GET | Requires "ADMIN" role    | [http://localhost:8080/admin](http://localhost:8080/admin)             |

You can copy and paste this Markdown table into your README.md file to provide a clear overview of the endpoints in your Spring Boot application.




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

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details.
