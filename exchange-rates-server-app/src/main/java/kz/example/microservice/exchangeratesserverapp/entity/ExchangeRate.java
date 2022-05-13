package kz.example.microservice.exchangeratesserverapp.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "exchange_rates")
@Getter
@Setter
@ToString
public class ExchangeRate {
    @Id
    @GeneratedValue
    private UUID id;
    private Date exchangeDate;
    private Double rateValue;
    private String currency;
}
