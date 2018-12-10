# Authentication service
Service which requests, retrieves and delivers access and refresh tokens

## oAuth 2 authentication
This application uses oAuth 2 with JWT encryption.
To generate .jks file `keytool -genkeypair -alias jwt -keyalg RSA -keypass secretKey -keystore jwt.jks -storepass secretKey` command should be performed.
<br/>
`secretKey` is a password in this case.