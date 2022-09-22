package ro.rasel.java.jackson;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonProperty("_id")
public @interface MyInclude {
}
