nginx -t
service nginx restart
cd /project/server/target
java -jar server-1.0-SNAPSHOT.jar
