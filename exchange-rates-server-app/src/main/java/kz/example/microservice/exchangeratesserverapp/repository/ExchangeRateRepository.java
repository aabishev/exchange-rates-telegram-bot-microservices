package kz.example.microservice.exchangeratesserverapp.repository;

import kz.example.microservice.exchangeratesserverapp.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {
    List<ExchangeRate> getExchangeRateHistoriesByCurrencyOrderByExchangeDate(String currency);
}
