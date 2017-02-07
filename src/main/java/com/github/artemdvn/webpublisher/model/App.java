package com.github.artemdvn.webpublisher.model;

import java.util.List;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "App")
@Table(name = "apps")
public class App {

	private Integer id;
	private String name;
	private AppType type;
	private List<ContentType> contentTypes;
	private User user;
	
	public App() {		
	}

	@Id
	@Column(name = "app_id")
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	@NotEmpty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated(EnumType.STRING)
	public AppType getType() {
		return type;
	}

	public void setType(AppType type) {
		this.type = type;
	}

	@ElementCollection(targetClass = ContentType.class)
	@JoinTable(name = "apps_content_types", joinColumns = @JoinColumn(name = "app_id"))
	@Column(name = "content_type")
	@Enumerated(EnumType.STRING)
	public List<ContentType> getContentTypes() {
		return contentTypes;
	}

	public void setContentTypes(List<ContentType> contentTypes) {
		this.contentTypes = contentTypes;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		App other = (App) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "App [id=" + id + ", name=" + name + ", type=" + type + ", user=" + user + "]";
	}

}
