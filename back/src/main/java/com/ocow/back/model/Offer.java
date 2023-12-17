package com.ocow.back.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
@Table(name = "OFFERS")
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@NonNull
	@ManyToOne
	@JoinColumn(name = "agency_id", referencedColumnName = "id")
	private Agency agency;
	
	@NonNull
	@ManyToOne
	@JoinColumn(name = "vehicle_category_id", referencedColumnName = "id")
	private VehicleCategory vehicleCategory;
	
	private float pricePerDay;
	
	private String currency;
	
}
