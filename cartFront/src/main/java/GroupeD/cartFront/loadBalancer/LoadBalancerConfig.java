package GroupeD.cartFront.loadBalancer;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class LoadBalancerConfig {

    @Bean
    public ReactorLoadBalancer<ServiceInstance> loadBalancer(Environment environment, LoadBalancerClientFactory factory){
        String name = environment.getProperty("loadbalancer.client.name");
        return new RandomLoadBalancer(factory.getLazyProvider(name, ServiceInstanceListSupplier.class));
    }
}
