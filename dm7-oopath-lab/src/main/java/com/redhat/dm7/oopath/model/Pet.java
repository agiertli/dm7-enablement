package com.redhat.dm7.oopath.model;

public class Pet {

	public enum SPECIES {
		DOG, RABBIT, FISH, CAT
	}

	private SPECIES species;

	private String name;

	public Pet(SPECIES species, String name) {

		this.species = species;
		this.name = name;
	}

	public SPECIES getSpecies() {
		return species;
	}

	public void setSpecies(SPECIES species) {
		this.species = species;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
