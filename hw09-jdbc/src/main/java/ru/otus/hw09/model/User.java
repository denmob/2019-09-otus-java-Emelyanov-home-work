package ru.otus.hw09.model;

import java.util.Objects;

public class User {

	@Id
	private Long id;
	
	private String name;
	
	private int age;
	
	public User() {}
	
	public User(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "User: id = " + id
				+ ", name = " + name
				+ ", age = " + age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User that = (User) o;
		return id == that.id &&
				age == that.age &&
				Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, age, name);
	}
	
}
