package diy.mqml.customstarterlayer.formats;


import diy.mqml.customstarterlayer.model.MyRetroAuditEvent;

public class TextOutputFormatStrategy implements MyRetroAuditFormatStrategy {
    @Override
    public String format(MyRetroAuditEvent event) {
        return event.toString();
    }

    @Override
    public String prettyFormat(MyRetroAuditEvent event) {
        return "\n\n" + event.toString() + "\n";
    }
}
