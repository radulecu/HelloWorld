package ro.rasel.java.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.util.Objects;
import java.util.StringJoiner;

public class JacksonDatabindComposition {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final Obj obj = new Obj(1, "s", 2);

        final String s = OBJECT_MAPPER.writeValueAsString(obj);
        System.out.println(s);
        final Obj objDeserialized = OBJECT_MAPPER.readValue(s, Obj.class);
        System.out.println(objDeserialized);
        final ObjRequest objRequestDeserialized = OBJECT_MAPPER.readValue(s, ObjRequest.class);
        System.out.println(objRequestDeserialized);
        System.out.println(objRequestDeserialized.getObj());
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
