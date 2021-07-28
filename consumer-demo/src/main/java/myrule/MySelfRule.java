package myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 程刘德
 * @version 1.0
 * @Description TODO
 * @date 2021/1/25
 * 修改默认的路由规则 ， 放在启动类同级包下，会对所有的都有效，不能达到定制的作用
 */
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule() {
//        new RoundRobinRule(); //轮询
//        new RetryRule();
//        new WeightedResponseTimeRule();
//        new BestAvailableRule();
//        new AvailabilityFilteringRule();
//        new ZoneAvoidanceRule();
        return  new RandomRule(); //随机
    }

}
