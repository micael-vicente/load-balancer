package pt.mv.lb;

import pt.mv.lb.exception.BalancerAtCapacityException;
import pt.mv.lb.exception.ServerAlreadyRegisteredException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class LoadBalancer {

    protected final List<Server> servers;
    private final transient Object lock = new Object();
    private final int maxCapacity;

    public LoadBalancer(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.servers = new ArrayList<>();
    }

    public boolean addServer(Server server) {
        synchronized (lock) {
            validateCanRegister(server);

            return servers.add(server);
        }
    }

    public abstract Optional<Server> getServer();

    private void validateCanRegister(Server server) {
        if(server == null || server.address() == null) {
            throw new IllegalArgumentException("Server#address cannot be null");
        }

        if(servers.contains(server)) {
            String message = "Server with address '%s' already registered";
            throw new ServerAlreadyRegisteredException(String.format(message, server.address()));
        }

        if(servers.size() >= maxCapacity) {
            throw new BalancerAtCapacityException("Load balancer is at full capacity. Max: " + maxCapacity);
        }
    }

}
