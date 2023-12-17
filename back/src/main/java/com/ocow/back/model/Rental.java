package com.ocow.back.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

@Data
@Entity
@Table(name = "RENTALS")
public class Rental {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(name = "start_day")
	private LocalDate startDay;
	
	@Column(name = "day_nb")
	private Integer dayNb;
	
	@NonNull
	@OneToOne
	@JoinColumn(name = "offer_id", referencedColumnName = "id")
	private Offer offer;
	
	@NonNull
	@ManyToOne
	@JoinColumn(name = "client_user_id", referencedColumnName = "id")
	private ClientUser clientUser;
	
	@NonNull
	@ManyToOne
	@JoinColumn(name = "agency_id_end", referencedColumnName = "id")
	public Agency agencyEnd;
	
	@Column(name = "rental_price")
	private Integer rentalPrice;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
    @Column(name = "updated_at")
	private LocalDateTime updatedAt;
}
