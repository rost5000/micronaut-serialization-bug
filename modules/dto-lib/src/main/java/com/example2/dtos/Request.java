package com.example2.dtos;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        defaultImpl = RequestUndefined.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(RequestType1.class),
        @JsonSubTypes.Type(RequestUndefined.class)
})
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public sealed abstract class Request permits RequestUndefined, RequestType1 {
    public static final String JSON_VALUE1 = "value1";
    public static final String JSON_VALUE2 = "value2";
    public static final String JSON_START_DATE = "start_date";
    public static final String JSON_END_DATE = "end_date";
    @Getter(onMethod_ = @JsonGetter(JSON_VALUE1))
    @Setter(onMethod_ = @JsonSetter(JSON_VALUE1))
    String value1;
    @Setter(onMethod_ = @JsonSetter(JSON_VALUE2))
    @Getter(onMethod_ = @JsonGetter(JSON_VALUE2))
    String value2;
    @Setter(onMethod_ = @JsonSetter(JSON_START_DATE))
    @Getter(onMethod_ = @JsonGetter(JSON_START_DATE))
    LocalDate startDate;
    @Setter(onMethod_ = @JsonSetter(JSON_END_DATE))
    @Getter(onMethod_ = @JsonGetter(JSON_END_DATE))
    LocalDate endDate;

    protected Request(@JsonProperty(JSON_VALUE1) String value1, @JsonProperty(JSON_VALUE2) String value2) {
        this.value1 = value1;
        this.value2 = value2;
    }
}
