package com.redhat.dm7.oopath;

import java.util.HashSet;
import java.util.Set;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.redhat.dm7.oopath.model.Person;
import com.redhat.dm7.oopath.model.Pet;

public class Pets {

	public static void main(String[] args) {

		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.newKieClasspathContainer();

		KieSession kieSession = kieContainer.newKieSession();

		Person james = new Person("James", 71);
		Person duncan = new Person("Duncan", 38);
		Person alan = new Person("Alan", 36);
		Person jason = new Person("Jason", 7);
		Person sinead = new Person("Sinead", 3);
		Person jack = new Person("Jack", 4);
		Person amy = new Person("Amy", 2);

		james.addChild(duncan);
		james.addChild(alan);
		duncan.addChild(jason);
		duncan.addChild(sinead);
		alan.addChild(jack);
		alan.addChild(amy);

		Set<Pet> amysPets = new HashSet<Pet>();

		Pet snowBall = new Pet(Pet.SPECIES.RABBIT, "snowBall");
		Pet fatDog = new Pet(Pet.SPECIES.DOG, "chubby");

		amysPets.add(snowBall);
		amysPets.add(fatDog);

		amy.setPet(amysPets);

		Set<Pet> jackPets = new HashSet<Pet>();

//		Pet fluffyCat = new Pet(Pet.SPECIES.CAT, "fluffy");
//		Pet goldFish = new Pet(Pet.SPECIES.FISH, "goldy");
//		Pet bunny = new Pet(Pet.SPECIES.RABBIT, "nibbles");
//
//
//		jackPets.add(fluffyCat);
//		jackPets.add(goldFish);
//		jackPets.add(bunny);
//		jack.setPet(jackPets);
		
		kieSession.insert(james);
		kieSession.insert(duncan);
		kieSession.insert(alan);
		kieSession.insert(jason);
		kieSession.insert(sinead);
		kieSession.insert(jack);
		kieSession.insert(amy);

		kieSession.fireAllRules();

		kieSession.dispose();
	}

}
