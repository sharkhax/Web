package com.drobot.web.model.service;

import java.util.Map;
import java.util.Optional;

public interface Creatable<T> {
    Optional<T> create(Map<String, String> fields);
}
