package mayton.ip2loc;


import java.util.Optional;

public interface Ipv4locationService extends Countable {

    Optional<Ipv4Loc> resolve(String ipv4);

}
