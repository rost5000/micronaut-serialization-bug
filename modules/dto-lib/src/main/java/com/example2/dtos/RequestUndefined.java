package com.example2.dtos;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;


public final class RequestUndefined extends Request {
    Map<String, Object> values = new TreeMap<>();

    @JsonCreator
    public RequestUndefined(String value1, String value2) {
        super(value1, value2);
    }

    @JsonAnyGetter
    public Map<String, Object> getValues() {
        return values;
    }

    @JsonAnySetter
    public void setValues(String key, Object value) {
        values.put(key, value);
    }

    @Override
    public String toString() {
        return "RequestUndefined{" +
                "values=" + values +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RequestUndefined that = (RequestUndefined) o;
        return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), values);
    }
}
