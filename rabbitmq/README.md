# How to start

docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 -p 5672:5672 -v myvolume:/var/lib/rabbitmq rabbitmq:3-management