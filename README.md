mvn clean package && java -jar target/test-1.0-SNAPSHOT.jar

curl -X PUT http://localhost:8080/bloom?url=https://norvig.com/big.txt

curl 'http://localhost:8080/bloom?url=https://norvig.com/big.txt&contains=guns'