call java -Dhttp.proxyHost=10.0.0.100 -Dhttp.proxyPort=8800 -Dspring.profiles.active=prod -jar registry\target\registry.jar
call java -Dhttp.proxyHost=10.0.0.100 -Dhttp.proxyPort=8801 -Dspring.profiles.active=prod -jar config\target\config.jar
call java -Dhttp.proxyHost=10.0.0.100 -Dhttp.proxyPort=8802 -Dspring.profiles.active=prod -jar gateway\target\gateway.jar
call java -Dhttp.proxyHost=10.0.0.100 -Dhttp.proxyPort=8803 -Dspring.profiles.active=prod -jar auth\target\auth.jar
call java -Dhttp.proxyHost=10.0.0.100 -Dhttp.proxyPort=8804 -Dspring.profiles.active=prod -jar manager\target\manager.jar
pause