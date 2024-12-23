package diy.mqml.customstarterlayer.formats;


import diy.mqml.customstarterlayer.model.MyRetroAuditEvent;

public interface MyRetroAuditFormatStrategy {
    String format(MyRetroAuditEvent event);
    String prettyFormat(MyRetroAuditEvent event);
}
