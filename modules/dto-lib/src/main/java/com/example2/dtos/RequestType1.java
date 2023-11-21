package com.example2.dtos;

import com.example2.dtos.commons.OutputSettings;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class RequestType1 extends Request {
    public static final String JSON_OUTPUT_SETTINGS = "output_settings";

    @Setter(onMethod_ = @JsonSetter(JSON_OUTPUT_SETTINGS))
    @Getter(onMethod_ = @JsonGetter(JSON_OUTPUT_SETTINGS))
    OutputSettings settings;

    @JsonCreator
    public RequestType1(
            @JsonProperty(JSON_VALUE1) String value1,
            @JsonProperty(JSON_VALUE1) String value2) {
        super(value1, value2);
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RequestType1(
            @JsonProperty(JSON_VALUE1) String value1,
            @JsonProperty(JSON_VALUE1) String value2,
            OutputSettings settings
    ) {
        this(value1, value2);
        this.settings = settings;
    }
}
