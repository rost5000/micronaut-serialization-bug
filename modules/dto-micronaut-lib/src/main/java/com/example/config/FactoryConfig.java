package com.example.config;

import com.example2.dtos.Request;
import com.example2.dtos.RequestType1;
import com.example2.dtos.RequestWrapper;
import com.example2.dtos.commons.OutputSettings;
import io.micronaut.context.annotation.Factory;
import io.micronaut.serde.annotation.SerdeImport;
import com.example2.dtos.RequestUndefined;

@Factory
@SerdeImport.Repeated({
        @SerdeImport(RequestUndefined.class),
        @SerdeImport(Request.class),
        @SerdeImport(RequestType1.class),
        @SerdeImport(RequestWrapper.class),
        @SerdeImport(OutputSettings.class)
})
public class FactoryConfig {
}
