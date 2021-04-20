package com.ayprojects.helpinghands.dao.appconfig;

import com.ayprojects.helpinghands.models.DhAppConfig;

import java.util.Optional;

public interface AppConfigDao {
    DhAppConfig addAppConfig(DhAppConfig dhAppConfig);
    Optional<DhAppConfig> getActiveAppConfig();
}
