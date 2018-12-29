# Authentication service
Service which requests, retrieves and delivers access and refresh tokens

## oAuth 2 authentication
This application uses oAuth 2 with JWT encryption.

Command to generate .jks file:
```text
keytool -genkeypair -alias jwt -keyalg RSA -keypass SECRET_KEY -keystore jwt.jks -storepass STORE_PASS
```

* `secretKey` is a password in this case.

## Setup
For setup see main README