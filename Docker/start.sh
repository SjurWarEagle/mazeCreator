nginx -t
service nginx restart
nginx -s reload
cd /project/server/target
java -jar mazeCreator-1.0-SNAPSHOT.jar
