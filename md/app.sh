#!/bin/bash

if [ "$1" = "start" ] ; then
        echo "-------------------------------------------------------------------"
        echo "   Start Server"
        echo "-------------------------------------------------------------------"

  # back ground
  nohup java -jar why-trip-0.0.1-SNAPSHOT.jar 1 > trip.out 2 > trip.err &

elif [ "$1" = "stop" ] ; then
        echo "-------------------------------------------------------------------"
        echo "   STOP Server"
        echo "-------------------------------------------------------------------"
        pid=`ps -ef | grep 'why-trip-0.0.1-SNAPSHOT.jar' | grep -v 'grep' | awk '{print $2}'`

  kill -9 $pid

else
        echo "Usage: ./app.sh ( start | stop )"
        exit 1
fi