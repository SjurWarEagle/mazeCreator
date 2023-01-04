nginx -t
service nginx restart
cd /project/server/target
echo "Starting app"
java -jar server-1.0-SNAPSHOT.jar
