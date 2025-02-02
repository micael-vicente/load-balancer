package pt.mv.lb;

import java.util.Optional;
import java.util.Random;

public class RandomLoadBalancer extends LoadBalancer {

    private final Random random;

    public RandomLoadBalancer(Random random, int maxCapacity) {
        super(maxCapacity);
        this.random = random;
    }

    @Override
    public Optional<Server> getServer() {
        if(servers.isEmpty()) return Optional.empty();

        int index = random.nextInt(servers.size());

        return Optional.of(servers.get(index));
    }
}
