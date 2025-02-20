package diy.mqml.jpahibernatestudy.persistence.repo

import diy.mqml.jpahibernatestudy.persistence.entity.jpaQuery.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository : JpaRepository<Message, Long> {

}