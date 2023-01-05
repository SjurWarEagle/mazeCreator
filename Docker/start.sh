nginx -t
/etc/init.d/nginx restart
nginx
#service nginx restart
cd /project/server/target
echo "Starting app"
java -jar server-1.0-SNAPSHOT.jar
