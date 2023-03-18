FROM   registry.access.redhat.com/ubi8/ubi:8.0

# command line options to pass to the JVM
ENV	  JAVA_OPTIONS -Xmx512m

WORKDIR /opt/app-root/bin/

# Install the Java runtime, create a user for running the app, and set permissions
RUN   yum install -y --disableplugin=subscription-manager java-1.8.0-openjdk-headless && \
      yum clean all --disableplugin=subscription-manager -y && \      
      mkdir -p /opt/app-root/bin

# Copy the runnable fat JAR to the container.
ADD   target/*.jar /opt/app-root/bin/app.jar
ADD   target/classes/*.properties /opt/app-root/bin/

RUN   chgrp -R 0 /opt/app-root && \
      chmod -R g+rwX /opt/app-root

USER  1001

# Run the fat JAR
CMD java -jar /opt/app-root/bin/app.jar