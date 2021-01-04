# 1. Generate JSON files
java -jar "/app/test.jar"  1 "jdbc:sqlserver://gm-core-db-sql-stage.everymatrix.local;databasename=" "cm" "cmapp" "cmapp123"  "SELECT * FROM dbo.cmCasinoFavoriteGame " "/app/data"

# 2. Post data to Player-api
java -jar "/app/test.jar"  2  "/app/data" "2228" "https://kavbet-api.stage.norway.everymatrix.com/v1/player"