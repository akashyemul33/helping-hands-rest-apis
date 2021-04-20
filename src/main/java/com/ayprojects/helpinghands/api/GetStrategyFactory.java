package com.ayprojects.helpinghands.api;

import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class GetStrategyFactory {
    private Map<StrategyName, StrategyGetBehaviour<?>> getStrategies;

    @Autowired
    public GetStrategyFactory(Set<StrategyGetBehaviour<?>> strategySet) {
        createStrategy(strategySet);
    }

    public StrategyGetBehaviour<?> findStrategy(StrategyName strategyName) {
        LOGGER.info("StrategyGetBehaviour->strategyName" + getStrategies.get(strategyName).getStrategyName());
        return getStrategies.get(strategyName);
    }

    private void createStrategy(Set<StrategyGetBehaviour<?>> strategySet) {
        getStrategies = new HashMap<StrategyName, StrategyGetBehaviour<?>>();
        strategySet.forEach(
                strategy -> {
                    LOGGER.info("strategyName=>" + strategy.getStrategyName());
                    getStrategies.put(strategy.getStrategyName(), strategy);
                });
    }
}
