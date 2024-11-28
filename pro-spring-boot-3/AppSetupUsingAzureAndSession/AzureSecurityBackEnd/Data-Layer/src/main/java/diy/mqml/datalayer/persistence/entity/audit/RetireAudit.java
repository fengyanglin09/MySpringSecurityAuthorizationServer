package diy.mqml.datalayer.persistence.entity.audit;


import diy.mqml.datalayer.persistence.entity.user.User;

import java.time.LocalDateTime;

public interface RetireAudit {

    User getRetiredBy();

    LocalDateTime getRetiredOn();
}
