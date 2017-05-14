project_home=/home/ubuntu
project_name=cityCommerce
cd $project_home/$project_name
pid=`ps -ef |grep $project_name |grep -v "grep" |awk '{print $2}' `
if [ $pid ]; then
    echo "${project_name}  is  running  and pid=$pid"
    kill -9 $pid
fi
java -version
java -jar $project_home/$project_name/target/$project_name.jar --spring.profiles.active=prod > /dev/null 2>&1 &