package ro.rasel.java.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.impl.StringArraySerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.util.Objects;
import java.util.StringJoiner;

public class JacksonDatabindCustomAnnotation {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setAnnotationIntrospector(new IgnoreIntrospector());

        OBJECT_MAPPER.setVisibility(OBJECT_MAPPER.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

        OBJECT_MAPPER.setVisibility(OBJECT_MAPPER.getDeserializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));


        final Obj obj = new Obj(1, "s", 2);

        final String s = OBJECT_MAPPER.writeValueAsString(obj);
        System.out.println(s);
        final Obj objDeserialized = OBJECT_MAPPER.readValue(s, Obj.class);
        System.out.println(objDeserialized);
        final ObjRequest objRequestDeserialized = OBJECT_MAPPER.readValue(s, ObjRequest.class);
        System.out.println(objRequestDeserialized);
        System.out.println(objRequestDeserialized.getObj());
    }

    public static class IgnoreIntrospector extends JacksonAnnotationIntrospector {

        @Override
        public Object findSerializationContentConverter(AnnotatedMember a) {
            return super.findSerializationContentConverter(a);
        }

        @Override
        public Object findSerializer(Annotated am) {
            Object serializer = super.findSerializer(am);
            if (StringArraySerializer.class.equals(serializer)) {
                return null;
            }
            return serializer;
        }


        @Override
        public Object findSerializationConverter(Annotated m) {
            if (_findAnnotation(m, MyInclude.class)!=null){
                return new StdConverter() {
                    @Override
                    public Object convert(Object o) {
                        return "*"+o;
                    }
                };
            }

            return super.findSerializationConverter(m);
        }

        @Override
        public boolean hasIgnoreMarker(AnnotatedMember m) {
            final boolean b = m instanceof AnnotatedField && _findAnnotation(m, MyInclude.class) == null;
            if (b){
                return true;
            }
            return b;
        }
    }

    static class Obj implements IObj {
        private final Integer id;
        private final String s;
        private final int i;

        @ConstructorProperties({"id", "s", "i"})
        Obj(int id, String s, int i) {
            this.id = id;
            this.s = s;
            this.i = i;
        }

        public Obj(String s, int i) {
            this.id = null;
            this.s = s;
            this.i = i;
        }

        @MyInclude
        public int getId() {
            return id;
        }

        @Override
        public String getS() {
            return s;
        }

        @Override
        public int getI() {
            return i;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Obj obj = (Obj) o;
            return i == obj.i &&
                    Objects.equals(id, obj.id) &&
                    Objects.equals(s, obj.s);
        }

        @Override
        public int hashCode() {
            return Objects.hash(s, i);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Obj.class.getSimpleName() + "[", "]")
                    .add("id=" + id)
                    .add("s='" + s + "'")
                    .add("i=" + i)
                    .toString();
        }
    }

    //@JsonIgnoreProperties(ignoreUnknown = true)
    static class ObjRequest implements IObj {
        private final Obj obj;

        @ConstructorProperties({"s", "i"})
        ObjRequest(String s, int i) {
            this.obj = new Obj(s, i);
        }

        @Override
        public String getS() {
            return obj.getS();
        }

        @Override
        public int getI() {
            return obj.getI();
        }

        public Obj getObj() {
            return obj;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ObjRequest that = (ObjRequest) o;
            return Objects.equals(obj, that.obj);
        }

        @Override
        public int hashCode() {
            return Objects.hash(obj);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", ObjRequest.class.getSimpleName() + "[", "]")
                    .add("obj=" + obj)
                    .toString();
        }
    }

    public interface IObj {
        @JsonProperty("string")
        String getS();

        int getI();
    }
}
