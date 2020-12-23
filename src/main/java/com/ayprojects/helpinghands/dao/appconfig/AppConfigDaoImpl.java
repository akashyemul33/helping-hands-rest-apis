package com.ayprojects.helpinghands.dao.appconfig;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.repositories.AppConfigRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AppConfigDaoImpl implements AppConfigDao {
    @Autowired
    AppConfigRepository appConfigRepository;


    @Override
    public DhAppConfig addAppConfig(DhAppConfig dhAppConfig) {
        return appConfigRepository.save(dhAppConfig);
    }

    @Override
    public Optional<DhAppConfig> getActiveAppConfig() {
        return appConfigRepository.findByStatus(AppConstants.STATUS_ACTIVE);
    }
}
