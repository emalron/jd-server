#!/bin/bash

mvn clean install
sudo cp target/jdodge.war /var/lib/tomcat8/webapps
sudo service tomcat8 restart
