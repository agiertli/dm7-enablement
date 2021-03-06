package com.redhat.dm7.oopath.model;

import java.util.HashSet;
import java.util.Set;

public class Person {
	
	Set<Person> children = new HashSet<>();
	
	private String name;
	
	private Set<Pet> pet = new HashSet<Pet>();
	
	private int age;
	
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public Set<Person> getChildren() {
		return children;
	}

	public void setChildren(Set<Person> children) {
		this.children = children;
	}
	
	public void addChild(Person child) {
		children.add(child);
	}
	
	public void addPet(Pet pet) {
		this.pet.add(pet);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<Pet> getPet() {
		return pet;
	}

	public void setPet(Set<Pet> pet) {
		this.pet = pet;
	}
	
}
