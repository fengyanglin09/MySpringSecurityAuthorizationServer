//package diy.mqml.datalayer.persistence.entity.contact;
//
//
//import diy.mqml.datalayer.persistence.entity.audit.CreateAudit;
//import diy.mqml.datalayer.persistence.entity.audit.UpdateAudit;
//import diy.mqml.datalayer.persistence.entity.user.User;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.experimental.Accessors;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.LocalDateTime;
//
//@Entity
//@EntityListeners(AuditingEntityListener.class)
//@Table(schema = "public", name = "contact")
//@Getter
//@Setter
//@Accessors(chain = true)
//@NoArgsConstructor
//public class Contact implements CreateAudit, UpdateAudit {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    private String phone;
//
//    private String email;
//
//    @ManyToOne
//    @JoinColumn(name = "created_by")
//    @CreatedBy
//    private User createdBy;
//
//    @CreatedDate
//    private LocalDateTime createdOn;
//
//    @ManyToOne
//    @JoinColumn(name = "updated_by")
//    @LastModifiedBy
//    private User updatedBy;
//
//    @LastModifiedDate
//    private LocalDateTime updatedOn;
//
//    @Version
//    private Integer version = 0;
//}
