package pt.mv.lb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
class RandomLoadBalancerTest {

    @Mock
    private Random mockRandom;

    @Test
    void getServer_loadBalancerIsEmpty_returnEmptyOptional() {
        LoadBalancer loadBalancer = new RandomLoadBalancer(mockRandom, 10);

        Optional<Server> server = loadBalancer.getServer();

        Assertions.assertTrue(server.isEmpty());
    }

    @Test
    void getServer_loadBalancerHas10_mockingIndex5ReturnsServerWithAddress5() {
        LoadBalancer loadBalancer = new RandomLoadBalancer(mockRandom, 10);

        IntStream.range(0, 10)
            .forEach((i) -> loadBalancer.addServer(new Server("" + i)));

        Mockito.when(mockRandom.nextInt(10)).thenReturn(5);

        Optional<Server> server = loadBalancer.getServer();

        Assertions.assertTrue(server.isPresent());
        Assertions.assertEquals("5", server.get().address());
    }


}