package models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.constants.KonstanteKursaUValuti;
import play.db.jpa.Model;
@Table(
		uniqueConstraints={
			@UniqueConstraint(columnNames={"osnovnaValuta_id", "premaValuti_id", "kursnaLista_id"})
		}
	)
@Entity
public class KursUValuti extends Model {

	@Column(precision=KonstanteKursaUValuti.BROJ_ZNACAJNIH_CIFARA_KUPOVNI, scale=KonstanteKursaUValuti.PRECIZNOST_KUPOVNI, nullable=false)
	public BigDecimal kupovni;
	@Column(precision=KonstanteKursaUValuti.BROJ_ZNACAJNIH_CIFARA_SREDNJI, scale=KonstanteKursaUValuti.PRECIZNOST_SREDNJI, nullable=false)
	public BigDecimal srednji;
	@Column(precision=KonstanteKursaUValuti.BROJ_ZNACAJNIH_CIFARA_PRODAJNI, scale=KonstanteKursaUValuti.PRECIZNOST_PRODAJNI, nullable=false)
	public BigDecimal prodajni;
	
	@ManyToOne(optional=false)
	public Valuta osnovnaValuta;
	
	@ManyToOne(optional=false)
	public Valuta premaValuti;
	

	@ManyToOne(optional=false)
	public KursnaLista kursnaLista;

	
	public KursUValuti() {
		// TODO Auto-generated constructor stub
	}
	
	public KursUValuti(BigDecimal kupovni, BigDecimal srednji, BigDecimal prodajni) {
		super();
		this.kupovni = kupovni;
		this.srednji = srednji;
		this.prodajni = prodajni;
	}
}
