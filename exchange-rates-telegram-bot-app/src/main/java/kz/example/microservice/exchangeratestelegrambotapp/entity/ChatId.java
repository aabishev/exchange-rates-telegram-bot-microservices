package kz.example.microservice.exchangeratestelegrambotapp.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users_chat_id")
@Getter
@Setter
@ToString
public class ChatId {
    @Id
    @GeneratedValue
    UUID id;
    Long userChatId;
}
