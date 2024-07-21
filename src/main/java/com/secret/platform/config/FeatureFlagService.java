package com.secret.platform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlagService implements FeatureFlagServiceInterface {

    @Value("${feature.holdingDrawer.enabled}")
    private boolean holdingDrawerEnabled;

    @Override
    public boolean isHoldingDrawerEnabled() {
        return holdingDrawerEnabled;
    }
}
