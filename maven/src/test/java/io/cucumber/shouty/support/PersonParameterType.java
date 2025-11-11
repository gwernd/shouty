package io.cucumber.shouty.support;

import io.cucumber.java.ParameterType;
import io.cucumber.shouty.Person;
import io.cucumber.shouty.support.ShoutyWorld;

public class PersonParameterType {
    private final ShoutyWorld world;

    public PersonParameterType(ShoutyWorld world){
        this.world = world;
    }
    @ParameterType("Lucy|Sean")
    public Person person(String name){
        if (this.world.people.containsKey(name)){
            return this.world.people.get(name);
        }
        Person person = new Person(name, this.world.network, 0);
        world.people.put(name, person);
        return person;
    }

}
