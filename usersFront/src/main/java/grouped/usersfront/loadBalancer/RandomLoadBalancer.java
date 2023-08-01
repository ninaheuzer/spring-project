package grouped.usersfront.loadBalancer;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;

public class RandomLoadBalancer implements ReactorServiceInstanceLoadBalancer {
    private final ObjectProvider<ServiceInstanceListSupplier> instancesProvider;

    public RandomLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> instancesProvider) {
        this.instancesProvider = instancesProvider;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = instancesProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get().next().map(list -> getInstanceRresponse(list));
    }

    private Response<ServiceInstance> getInstanceRresponse(List<ServiceInstance> instances) {
        if (instances.isEmpty()) return new EmptyResponse();
        ServiceInstance instance = instances.get((int) (Math.random() * (instances.size() - 1)));

        return new DefaultResponse(instance);
    }
}
