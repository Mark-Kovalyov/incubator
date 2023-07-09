package mayton;

import java.io.*;
import java.net.InetAddress;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.*;
import org.xbill.DNS.Record;

public class DnsDemo {

    static Logger logger = LoggerFactory.getLogger("dns-demo");

    static String reverseIp(String ip) {
        String[] o = ip.split(Pattern.quote("."));
        return o[3] + "." + o[2] + "." + o[1] + "." + o[0];
    }

    static Name tryToGetPtrRecord(Message message, int i) {
        if (i > 3 || i == Section.QUESTION) return null;
        for (Record rec : message.getSection(i)) {
            if (rec instanceof PTRRecord) {
                PTRRecord ptrRecord = (PTRRecord) rec;
                return ptrRecord.getTarget();
            }
        }
        return null;
    }

    static Optional<Name> tryToGetPtr(Message resp) {
        for(int i = 0; i < 4 ; i++) {
            Name res = tryToGetPtrRecord(resp, i);
            if (res != null) return Optional.of(res);
        }
        return Optional.empty();
    }


    static Optional<InetAddress> tryToGetARecord(Message message, int i) {
        if (i > 3 || i == Section.QUESTION) return Optional.empty();
        for (Record rec : message.getSection(i)) {
            if (rec instanceof ARecord) {
                ARecord arec = (ARecord) rec;
                InetAddress addr = arec.getAddress();
                return Optional.of(addr);
            }
        }
        return Optional.empty();
    }

    public static Optional<String> resolve(String input, String dns) throws IOException {
        Resolver resolver = new SimpleResolver(dns);
        resolver.setPort(53);
        resolver.setTCP(false);
        resolver.setTimeout(Duration.ofSeconds(15));
        Name in = Name.fromString(input + ".in-addr.arpa.");
        Message resp = resolver.send(
                Message.newQuery(Record.newRecord(
                        in,
                        Type.PTR,
                        DClass.IN))
        );
        Optional<Name> name = tryToGetPtr(resp);
        return name.isPresent() ? Optional.of(name.get().toString()) : Optional.empty();
    }




}
