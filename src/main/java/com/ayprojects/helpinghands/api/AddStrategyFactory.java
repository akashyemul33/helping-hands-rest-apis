package com.ayprojects.helpinghands.api;

import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class AddStrategyFactory {
    private Map<StrategyName, StrategyAddBehaviour<?>> addStrategies;

    @Autowired
    public AddStrategyFactory(Set<StrategyAddBehaviour<?>> strategySet) {
        createStrategy(strategySet);
    }

    public StrategyAddBehaviour<?> findStrategy(StrategyName strategyName) {
        LOGGER.info("StrategyAddBehaviour->strategyName" + addStrategies.get(strategyName).getStrategyName());
        return addStrategies.get(strategyName);
    }

    private void createStrategy(Set<StrategyAddBehaviour<?>> strategySet) {
        addStrategies = new HashMap<StrategyName, StrategyAddBehaviour<?>>();
        strategySet.forEach(
                strategy -> {
                    LOGGER.info("strategyName=>" + strategy.getStrategyName());
                    addStrategies.put(strategy.getStrategyName(), strategy);
                });
    }
}
