package mayton;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Properties;

public class WithJndi {

    public static void main(String[] args) throws Exception {
        Properties env = new Properties();

        env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.dns.DnsContextFactory");

        env.setProperty(Context.PROVIDER_URL,
                "dns://8.8.8.8");

        DirContext context = new InitialDirContext(env);

        Attributes list = context.getAttributes(
                "stackoverflow.com",
                new String[] { "A" }
        );

        NamingEnumeration<? extends Attribute> records = list.getAll();
        while (records.hasMore()) {
            Attribute record = records.next();
            String name = record.get().toString();
            System.out.println(name);
        }
    }

}
