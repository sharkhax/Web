package com.drobot.web.model.creator;

import java.util.Map;
import java.util.Optional;

public interface Creator<T> {
    Optional<T> create(Map<String, String> fields);
}
