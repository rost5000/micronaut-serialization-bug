# Introduction

This project represents the bug which tracked in build time with micronaut serialization and lombok

## Project structure

The project contains the following modules:
1. [dto-lib](./modules/dto-lib) contains only models annotated Jackson.
2. [dto-micronaut-lib](./modules/dto-micronaut-lib) contains micronaut library setup for generating proxy classes for serde serialization
3. [dto-application](./modules/dto-application) simulate the final application. That module consumes `dto-micronaut-library`
4. Tests for check serialization and deserialization located [here](./modules/dto-application/src/test/java/com/example/MicronautSerializerJsonTypeInfoTest.java)

## Main idea

Assume that I have thirdparty library that generates only Jackson annotation, I cannot influence to this lib. I try to import and configure that as micronaut library dependency.

The library represents that we can receive a several types of requests or undefined request with common parameters, so, in this project, we can see:

1. [`Request`](./modules/dto-lib/src/main/java/com/example2/dtos/Request.java) contains the common properties that can be use in all requests. Some of the properties can be nullable
2. [`RequestType1`](./modules/dto-lib/src/main/java/com/example2/dtos/RequestType1.java) when we receive some request with defined parameters
3. [`RequestUndefined`](./modules/dto-lib/src/main/java/com/example2/dtos/RequestUndefined.java) when we receive undefined type of the request with unspecified parameters
## Bug reproducing

Run the following command:
```shell
./gradlew clean build
```

You can receive the following exception:
```text
Task :dto-application:test FAILED

MicronautSerializerJsonTypeInfoTest > Test try to initialize java object map to json, then revert it FAILED
    io.micronaut.serde.exceptions.SerdeException at MicronautSerializerJsonTypeInfoTest.java:45
        Caused by: java.lang.ClassCastException at MicronautSerializerJsonTypeInfoTest.java:45

MicronautSerializerJsonTypeInfoTest > Try to initialize Unknown request from json in resource classpath FAILED
    io.micronaut.serde.exceptions.SerdeException at MicronautSerializerJsonTypeInfoTest.java:52
        Caused by: java.lang.ArrayIndexOutOfBoundsException at MicronautSerializerJsonTypeInfoTest.java:52
```

Full stack trace:
```text
io.micronaut.serde.exceptions.SerdeException: Error decoding property [Request request] of type [class com.example2.dtos.RequestWrapper]: class com.example2.dtos.commons.OutputSettings cannot be cast to class java.lang.String (com.example2.dtos.commons.OutputSettings is in unnamed module of loader 'app'; java.lang.String is in module java.base of loader 'bootstrap')
	at app//io.micronaut.serde.support.deserializers.DeserBean$DerProperty.deserializeAndSetConstructorValue(DeserBean.java:857)
	at app//io.micronaut.serde.support.deserializers.SimpleRecordLikeObjectDeserializer.deserialize(SimpleRecordLikeObjectDeserializer.java:68)
	at app//io.micronaut.serde.support.deserializers.SimpleRecordLikeObjectDeserializer.deserializeNullable(SimpleRecordLikeObjectDeserializer.java:102)
	at app//io.micronaut.serde.jackson.JacksonJsonMapper.readValue0(JacksonJsonMapper.java:177)
	at app//io.micronaut.serde.jackson.JacksonJsonMapper.readValue(JacksonJsonMapper.java:169)
	at app//io.micronaut.serde.jackson.JacksonJsonMapper.readValue(JacksonJsonMapper.java:215)
	at app//io.micronaut.json.JsonMapper.readValue(JsonMapper.java:141)
	at app//io.micronaut.json.JsonMapper.readValue(JsonMapper.java:168)
	at app//com.example.MicronautSerializerJsonTypeInfoTest.doTest1(MicronautSerializerJsonTypeInfoTest.java:45)
	at java.base@21/java.lang.reflect.Method.invoke(Method.java:580)
	at app//io.micronaut.test.extensions.junit5.MicronautJunit5Extension$2.proceed(MicronautJunit5Extension.java:142)
	at app//io.micronaut.test.extensions.AbstractMicronautExtension.interceptEach(AbstractMicronautExtension.java:157)
	at app//io.micronaut.test.extensions.AbstractMicronautExtension.interceptTest(AbstractMicronautExtension.java:114)
	at app//io.micronaut.test.extensions.junit5.MicronautJunit5Extension.interceptTestMethod(MicronautJunit5Extension.java:129)
	at java.base@21/java.util.ArrayList.forEach(ArrayList.java:1596)
	at java.base@21/java.util.ArrayList.forEach(ArrayList.java:1596)
Caused by: java.lang.ClassCastException: class com.example2.dtos.commons.OutputSettings cannot be cast to class java.lang.String (com.example2.dtos.commons.OutputSettings is in unnamed module of loader 'app'; java.lang.String is in module java.base of loader 'bootstrap')
	at com.example.config.$com_example2_dtos_RequestType1$Introspection.dispatchOne(Unknown Source)
	at io.micronaut.inject.beans.AbstractInitializableBeanIntrospection$BeanPropertyImpl.setUnsafe(AbstractInitializableBeanIntrospection.java:877)
	at io.micronaut.serde.support.deserializers.DeserBean$DerProperty.set(DeserBean.java:832)
	at io.micronaut.serde.support.deserializers.SpecificObjectDeserializer$CachedPropertiesValuesDeserializer.injectProperties(SpecificObjectDeserializer.java:343)
	at io.micronaut.serde.support.deserializers.SpecificObjectDeserializer$ArgsConstructorBeanDeserializer.provideInstance(SpecificObjectDeserializer.java:773)
	at io.micronaut.serde.support.deserializers.SpecificObjectDeserializer.deserialize(SpecificObjectDeserializer.java:102)
	at io.micronaut.serde.support.deserializers.SpecificObjectDeserializer.deserialize(SpecificObjectDeserializer.java:66)
	at io.micronaut.serde.support.deserializers.SubtypedPropertyObjectDeserializer.deserialize(SubtypedPropertyObjectDeserializer.java:66)
	at io.micronaut.serde.Deserializer.deserializeNullable(Deserializer.java:85)
	at io.micronaut.serde.support.deserializers.DeserBean$DerProperty.deserializeAndSetConstructorValue(DeserBean.java:838)
	... 15 more
```

Seems that the error is in the generated proxy classes. The Micronaut try to instantiate the wrong type to the field.

