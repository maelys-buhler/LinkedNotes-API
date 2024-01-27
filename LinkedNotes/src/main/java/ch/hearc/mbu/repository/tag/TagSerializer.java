package ch.hearc.mbu.repository.tag;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class TagSerializer extends StdSerializer<Tag> {
    public TagSerializer() {
        this(null);
    }

    public TagSerializer(Class<Tag> t) {
        super(t);
    }

    @Override
    public void serialize(Tag tag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", tag.getId());
        jsonGenerator.writeStringField("name", tag.getName());
        jsonGenerator.writeEndObject();
    }

}
