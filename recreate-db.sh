POSTGRES_CONTAINER_ID=$(docker container ls | grep postgres | awk -F' ' '{print $1}')
docker exec -i $POSTGRES_CONTAINER_ID psql -U postgres << eof
  DROP DATABASE one WITH (FORCE); DROP DATABASE two WITH (FORCE);
  CREATE DATABASE one; CREATE DATABASE two;
eof
