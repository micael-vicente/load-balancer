package pt.mv.lb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.mv.lb.exception.BalancerAtCapacityException;
import pt.mv.lb.exception.ServerAlreadyRegisteredException;

import java.util.Optional;
import java.util.stream.IntStream;

public class LoadBalancerTest {


    @Test
    void addServer_addressIsNull_illegalArgumentIsThrown() {
        LoadBalancer loadBalancer = new MockLoadBalancer(10);
        Server server = new Server(null);

        Assertions.assertThrows(IllegalArgumentException.class, () ->loadBalancer.addServer(server));
    }

    @Test
    void addServer_serverIsNull_illegalArgumentIsThrown() {
        LoadBalancer loadBalancer = new MockLoadBalancer(10);

        Assertions.assertThrows(IllegalArgumentException.class, () ->loadBalancer.addServer(null));
    }

    @Test
    void addServer_addressAlreadyRegistered_serverAlreadyRegisteredIsThrown() {
        LoadBalancer loadBalancer = new MockLoadBalancer(10);
        Server server = new Server("https://acme.com");

        boolean wasAdded = loadBalancer.addServer(server);

        Assertions.assertThrows(ServerAlreadyRegisteredException.class, () -> loadBalancer.addServer(server));

        Assertions.assertTrue(wasAdded);
    }

    @Test
    void addServer_addressIsFineAndCanBeAdded_returnTrue() {
        LoadBalancer loadBalancer = new MockLoadBalancer(10);
        Server server = new Server("https://acme.com");

        boolean wasAdded = loadBalancer.addServer(server);

        Assertions.assertTrue(wasAdded);
    }

    @Test
    void addServer_balancerAtCapacity_balancerAtCapacityIsThrown() {
        LoadBalancer loadBalancer = new MockLoadBalancer(10);

        IntStream.range(0, 10)
                .forEach((i) -> loadBalancer.addServer(new Server("address" + i)));

        Server server = new Server("https://acme.com");

        BalancerAtCapacityException e = Assertions
                .assertThrows(BalancerAtCapacityException.class, () -> loadBalancer.addServer(server));

        Assertions.assertNotNull(e.getMessage());
        Assertions.assertTrue(e.getMessage().contains("10"));
    }

    private static class MockLoadBalancer extends LoadBalancer {

        public MockLoadBalancer(int maxCapacity) {
            super(maxCapacity);
        }

        @Override
        public Optional<Server> getServer() {
            return Optional.empty();
        }
    }
}
