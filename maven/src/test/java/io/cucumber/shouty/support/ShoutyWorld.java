package io.cucumber.shouty.support;

import io.cucumber.shouty.Network;
import io.cucumber.shouty.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ShoutyWorld {
    private static final Pattern BUY_PATTERN = Pattern.compile("buy", Pattern.CASE_INSENSITIVE);
    private static final int DEFAULT_RANGE = 100;
    public Network network = new Network(DEFAULT_RANGE);
    public HashMap<String, Person> people = new HashMap<String, Person>();
    public HashMap<Person, List<String>> messagesShoutedBy = new HashMap<Person, List<String>>();
    public void shout(Person person, String message){
        person.shout(message);
        List<String> messages = messagesShoutedBy.get(person);
        Matcher matcher = BUY_PATTERN.matcher(message);
        if (messages == null){
            messages = new ArrayList<String>();
            messagesShoutedBy.put(person, messages);
        }
        if (matcher.find()){
            person.takeCredits(5);
        }
        if (message.length() > 180){
            person.takeCredits(2);
        }
        messages.add(message);
    }
}
