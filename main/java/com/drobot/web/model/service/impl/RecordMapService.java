package com.drobot.web.model.service.impl;

import com.drobot.web.model.service.MapService;

import java.util.Map;

public enum RecordMapService implements MapService {

    INSTANCE;

    @Override
    public boolean isMapValid(Map<String, String> fields) {
        return false;
    }
}
