FROM komu/tomcat8-java8

MAINTAINER juha.komulainen@evident.fi

EXPOSE 8080

ENV TOMCAT_VERSION 8.0.12

ENV LANG en_US.UTF-8
ENV CATALINA_HOME /opt/tomcat
ENV PATH $PATH:$CATALINA_HOME/bin

COPY . /opt/tomcat/webapps

# Start Tomcat
CMD ["/opt/tomcat/bin/catalina.sh", "run"]