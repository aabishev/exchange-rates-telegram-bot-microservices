package kz.example.microservice.exchangeratestelegrambotapp.repository;

import kz.example.microservice.exchangeratestelegrambotapp.entity.ChatId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatIdRepository extends JpaRepository<ChatId, UUID> {

}
