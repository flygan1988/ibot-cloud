package com.taiping.ibot.common.entity;

import java.util.HashMap;

public class IBotResponse extends HashMap<String, Object> {
    private static final long serialVersionUID = -8713837118340960775L;

    public IBotResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public IBotResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public IBotResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getMessage() {
        return String.valueOf(get("message"));
    }

    public Object getData() {
        return get("data");
    }
}
