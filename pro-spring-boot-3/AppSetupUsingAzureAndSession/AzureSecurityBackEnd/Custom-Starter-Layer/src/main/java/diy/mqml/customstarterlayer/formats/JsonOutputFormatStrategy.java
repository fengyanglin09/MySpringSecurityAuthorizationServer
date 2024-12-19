package diy.mqml.customstarterlayer.formats;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import diy.mqml.customstarterlayer.model.MyRetroAuditEvent;
import lombok.SneakyThrows;

public class JsonOutputFormatStrategy implements MyRetroAuditFormatStrategy {

    final ObjectMapper objectMapper = new ObjectMapper();

    public JsonOutputFormatStrategy(){
        objectMapper.registerModule(new JavaTimeModule());
    }

    @SneakyThrows
    @Override
    public String format(MyRetroAuditEvent event) {
        return objectMapper
                .writeValueAsString(event);
    }

    @SneakyThrows
    @Override
    public String prettyFormat(MyRetroAuditEvent event) {
        return "\n\n" + objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(event) + "\n";
    }
}


