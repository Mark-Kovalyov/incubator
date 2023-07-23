package mayton.ip2loc;

import mayton.network.blacklist.EmuleGuarding;

import java.util.Optional;

public class UDPRec {

    public String ts;
    public String ip;
    public int port;
    public int local_port;
    public Optional<Ipv4Loc> ipv4LocOpt = Optional.empty();
    public Optional<EmuleGuarding> emuleGuardingOpt = Optional.empty();

    @Override
    public String toString() {
        return "UDPRec{" +
                "ts='" + ts + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", local_port=" + local_port +
                ", ipv4Loc=" + ipv4LocOpt.toString() +
                ", emuleGuarding=" + emuleGuardingOpt.toString() +
                '}';
    }
}
