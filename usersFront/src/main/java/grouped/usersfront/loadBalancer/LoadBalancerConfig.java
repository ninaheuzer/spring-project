package grouped.usersfront.loadBalancer;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class LoadBalancerConfig {

    @Bean
    public ReactorLoadBalancer<ServiceInstance> loadBalancer(Environment env, LoadBalancerClientFactory factory) {
        String name = env.getProperty("loadbalancer.client.name");
        return new RandomLoadBalancer(factory.getLazyProvider(name, ServiceInstanceListSupplier.class));
    }
}
