call mvn -B -s settings.xml -DskipTests=true clean package
call java  -DDATABASE_URL="postgres://postgres:123@localhost:5432/clinic-db" -jar target/dependency/webapp-runner.jar target/*.war