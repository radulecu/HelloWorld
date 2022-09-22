package ro.rasel.java.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class JsonIncludeNonDefaults {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper =  new ObjectMapper();
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
        System.out.println(objectMapper.writer().writeValueAsString(new A(0,"")));
        System.out.println(objectMapper.writer().writeValueAsString(new A(1,"value")));
        System.out.println(objectMapper.writer().writeValueAsString(new A(2,"default")));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_DEFAULT) // uncomment for version with annotation
    private static class A{
        private int i=2;
        private String s="default";
    }
}
