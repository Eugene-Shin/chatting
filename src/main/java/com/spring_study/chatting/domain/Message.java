package com.spring_study.chatting.domain;

import com.spring_study.chatting.domain.type.MessageStatus;
import com.spring_study.chatting.dto.request.MessageUpdate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "message",
        indexes = {
                @Index(
                        name = "idx_message_chat_id_id",
                        columnList = "chat_id, id"
                )
        }
)
@Getter
@NoArgsConstructor
@DynamicUpdate
public class Message {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="chat_id")
    @NotNull
    private Chat chat;

    @Column(name="created_at")
    @NotNull
    private LocalDateTime created_at;

    @Column(name="data")
    @NotBlank
    private String data;

    @Column(name="status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @ManyToOne
    @JoinColumn(name="sender")
    @NotNull
    private User sender;

    @Column(name="is_delete")
    private Boolean isDelete;

    @Builder
    public Message(Long id, Chat chat_id, LocalDateTime created_at, String data, MessageStatus status,User sender) {
        this.id = id;
        this.chat = chat_id;
        this.created_at = created_at;
        this.data = data;
        this.status = status;
        this.sender=sender;
        this.isDelete=false;
    }

    public void update(MessageUpdate messageUpdate){
        if(messageUpdate.data()!=null) this.data=messageUpdate.data();
        if(messageUpdate.messageStatus()!=null)this.status=messageUpdate.messageStatus();
    }

    public void delete(){
        this.isDelete=true;
    }
}
