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

    public static Optional<String> resolve2(String input, String dns) throws IOException {
        Resolver resolver = new SimpleResolver(dns);
        resolver.setPort(53);
        //resolver.setTimeout(Duration.ofMillis(3000));
        resolver.setTCP(false);

        //Name tsnArpa = Name.fromString("85.240.137.195.in-addr.arpa.");

        Name in = Name.fromString(input + ".in-addr.arpa.");

        Message resp = resolver.send(
                Message.newQuery(Record.newRecord(
                        in,
                        Type.PTR,
                        DClass.IN))
        );

        //logger.info(resp.toString());

        //logger.info("============================================================================================");

        //logger.info("rcode  = {}", resp.getRcode());
        //logger.info("header = {}", resp.getHeader());
        Header header = resp.getHeader();
        /*logger.info("  header:flags = {}",header.printFlags());
        logger.info("  header:rcode = {}",header.getRcode());
        logger.info("  header:id    = {}",header.getID());
        logger.info("opt    = {}", resp.getOPT());
        logger.info("TSIG   = {}", resp.getTSIG());*/
        //ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //HexDump.dump(resp.toWire(), 0L, bos, 0);

        //bos.flush();
        /*logger.info("wire   = \n{}", bos.toString());
        logger.info("resolver = {}", resp.getResolver());*/
        //int count = resp.getHeader().getCount(0);

        //logger.info("count 0 = {}", count);


        /*Optional<Optional<InetAddress>> address = IntStream.range(0, 4)
                .mapToObj(i -> tryToGetARecord(resp, i))
                .filter(x -> x.isPresent())
                .findFirst();

        if (address.isPresent()) {
            logger.info("Resolved domain name into IP {}", address.get().get());

        }*/

        Optional<Name> name = tryToGetPtr(resp);

        return name.isPresent() ? Optional.of(name.get().toString()) : Optional.empty();
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

    public static void main(String[] args) throws IOException, InterruptedException {
        Random random = new Random();
        for(int i = 1; i < 250 ; i++) {
            int r1 = 1 + random.nextInt(254);
            int r2 = 1 + random.nextInt(254);
            int r3 = 1 + random.nextInt(254);
            int r4 = 1 + random.nextInt(254);
            String input = String.format("%d.%d.%d.%d", r1,r2,r3,r4);
            try {
                Optional<String> res = resolve(reverseIp(input), "8.8.8.8");
                logger.info("{} :: {}", input, res.isPresent() ? res.get() : "[None]");
            } catch (IOException ex) {
                logger.warn("IO ex : {}", ex.getMessage());
            }

            //Thread.sleep(250L + random.nextInt(250));
        }
    }


}
