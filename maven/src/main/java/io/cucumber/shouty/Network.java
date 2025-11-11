package io.cucumber.shouty;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Network {
    private final List<Person> people = new ArrayList<>();
    private final int range;
    private static final Pattern BUY_PATTERN = Pattern.compile("\\bbuy\\b", Pattern.CASE_INSENSITIVE);

    public Network(int range) {
        this.range = range;
    }

    public void subscribe(Person p) {
        people.add(p);
    }

    public void broadcast(String message, int shouterLocation) {
        Person shouter = null;
        for (Person p : people) {
            if (p.getLocation() == shouterLocation) {
                shouter = p;
                break;
            }
        }
        boolean shortEnough = message.length() <= 180;
        if (shouter != null) {
            deductCredits(shortEnough, message, shouter);
        }
        for (Person listener : people) {
            boolean withinRange = Math.abs(listener.getLocation() - shouterLocation) <= range;
            if (withinRange && shortEnough) {
                listener.hear(message);
            }
        }
    }

    private void deductCredits(boolean shortEnough, String message, Person shouter) {
        if (!shortEnough) {
            shouter.setCredits(shouter.getCredits() - 2);
        }
        Matcher matcher = BUY_PATTERN.matcher(message);
        while (matcher.find()) {
            shouter.setCredits(shouter.getCredits() - 5);
        }
    }
}