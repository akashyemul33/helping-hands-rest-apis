package com.ayprojects.helpinghands.api;

import com.ayprojects.helpinghands.api.behaviours.StrategyUpdateBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class UpdateStrategyFactory {
    private Map<StrategyName, StrategyUpdateBehaviour<?>> updateStrategies;

    @Autowired
    public UpdateStrategyFactory(Set<StrategyUpdateBehaviour<?>> strategySet) {
        createStrategy(strategySet);
    }

    public StrategyUpdateBehaviour<?> findStrategy(StrategyName strategyName) {
        LOGGER.info("StrategyUpdateBehaviour->strategyName" + updateStrategies.get(strategyName).getStrategyName());
        return updateStrategies.get(strategyName);
    }

    private void createStrategy(Set<StrategyUpdateBehaviour<?>> strategySet) {
        updateStrategies = new HashMap<StrategyName, StrategyUpdateBehaviour<?>>();
        strategySet.forEach(
                strategy -> {
                    LOGGER.info("strategyName=>" + strategy.getStrategyName());
                    updateStrategies.put(strategy.getStrategyName(), strategy);
                });
    }
}
