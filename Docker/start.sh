nginx -t
service nginx restart
nginx -s reload
cd /project/server/target
java -jar server-1.0-SNAPSHOT.jar
