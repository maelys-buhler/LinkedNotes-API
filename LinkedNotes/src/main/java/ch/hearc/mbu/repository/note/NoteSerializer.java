package ch.hearc.mbu.repository.note;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class NoteSerializer extends StdSerializer<Note>{
    public NoteSerializer() {
        this(null);
    }

    public NoteSerializer(Class<Note> t) {
        super(t);
    }

    @Override
    public void serialize(Note note, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", note.getId());
        jsonGenerator.writeStringField("title", note.getTitle());
        jsonGenerator.writeStringField("content", note.getContent());
        jsonGenerator.writeEndObject();
    }
}
