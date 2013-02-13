package com.plixee.lab.brainbox.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;

@Entity
public class Idea {
	@Id
	@GeneratedValue
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	@NotNull
	@ManyToOne
	private User author;
	@NotNull
	@Size(min = 1, max = 140)
	private String title;
	@Size(min = 1, max = 1000)
	private String description;
	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "idea_plus", uniqueConstraints = { @UniqueConstraint(columnNames = {
			"idea_id", "plus_id" }) })
	private Set<User> plus;
	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "idea_minus", uniqueConstraints = { @UniqueConstraint(columnNames = {
			"idea_id", "minus_id" }) })
	private Set<User> minus;

	public Idea() {
		this.plus = new HashSet<User>();
		this.minus = new HashSet<User>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DateTime getCreatedDate() {
		if (this.createdDate == null) {
			return null;
		} else {
			return new DateTime(this.createdDate);
		}
	}

	public void setCreatedDate(DateTime createdDate) {
		if (createdDate == null) {
			this.createdDate = null;
		} else {
			this.createdDate = createdDate.toDate();
		}
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getPlus() {
		return plus;
	}

	public void setPlus(Set<User> plus) {
		this.plus = plus;
	}

	public Set<User> getMinus() {
		return minus;
	}

	public void setMinus(Set<User> minus) {
		this.minus = minus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((minus == null) ? 0 : minus.hashCode());
		result = prime * result + ((plus == null) ? 0 : plus.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Idea other = (Idea) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (minus == null) {
			if (other.minus != null)
				return false;
		} else if (!minus.equals(other.minus))
			return false;
		if (plus == null) {
			if (other.plus != null)
				return false;
		} else if (!plus.equals(other.plus))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
