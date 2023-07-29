package org.example;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class Main {

    public static void main(String[] args) throws NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, MalformedObjectNameException {
        ObjectName objectName = new ObjectName("com.baeldung.tutorial:type=basic,name=game");
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(new Object(), objectName);
    }
}