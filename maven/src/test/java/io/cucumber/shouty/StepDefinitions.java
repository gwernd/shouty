package io.cucumber.shouty;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.shouty.support.ShoutyWorld;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {

    private final ShoutyWorld world;
    private Map<String, Person> people;
    private Map<String, String> messageFrom;

    public StepDefinitions(ShoutyWorld world) {
        this.world = world;
    }

    @Before
    public void setup(){
        people = new HashMap<>();
        messageFrom = new HashMap<>();
    }

    @Given("the range is {int}")
    public void the_range_is(int range) {
        world.network = new Network(range);
    }

    @Given("{word} is located at {int}")
    public void person_is_located_at(String name, Integer location){
        Person person = new Person(name, world.network, location);
        people.put(name, person);
    }

    @Given("{word} has bought {int} credits")
    public void person_has_bought_credits(String name, int credits) {
        people.get(name).setCredits(credits);
    }

    @When("{word} shouts")
    public void person_shouts(String name) {
        Person person = people.get(name);
        person.shout("Hello, world");
        messageFrom.put(name, "Hello, world");
    }

    @When("{word} shouts {string}")
    public void person_shouts_message(String name, String message) {
        world.shout(people.get(name), message);
        messageFrom.put(name, message);
    }

    @When("{word} shouts {int} over-long messages")
    public void person_shouts_over_long_messages(String name, int count) {
        String baseMessage = "A message that is 181 characters long ";
        String overlongMessage = baseMessage + "x".repeat(181 - baseMessage.length());
        for (int i = 0; i < count; i++) {
            people.get(name).shout(overlongMessage);
        }
        messageFrom.put(name, overlongMessage);
    }

    @When("{word} shouts {int} messages containing the word {string}")
    public void person_shouts_messages_containing_the_word(String name, int count, String word) {
        String msg = "a message containing the word " + word;
        for (int i = 0; i < count; i++) {
            people.get(name).shout(msg);
        }
        messageFrom.put(name, msg);
    }

    @When("{word} shouts the following message")
    public void person_shouts_the_following_message(String name, String docString) {
        people.get(name).shout(docString);
        messageFrom.put(name, docString);
    }

    @Then("{word} should hear a shout")
    public void person_should_hear_a_shout(String listener) {
        assertEquals(1, people.get(listener).getMessagesHeard().size());
    }

    @Then("{word} should not hear a shout")
    public void person_should_not_hear_a_shout(String listener) {
        assertEquals(0, people.get(listener).getMessagesHeard().size());
    }

    @Then("{word} should hear {word}'s message")
    public void listener_should_hear_shouter_message(String listener, String shouter) {
        assertEquals(Collections.singletonList(messageFrom.get(shouter)), people.get(listener).getMessagesHeard());
    }

    @Then("{word} hears the following messages:")
    public void person_hears_the_following_messages(String listener, DataTable expectedMessages) {
        List<List<String>> actualMessages = new ArrayList<>();
        List<String> heard = people.get(listener).getMessagesHeard();
        for (String message : heard) {
            actualMessages.add(Collections.singletonList(message));
        }
        expectedMessages.diff(DataTable.create(actualMessages));
    }

    @Then("{word} should have {int} credits left")
    public void person_should_have_credits_left(String name, int credits) {
        assertEquals(credits, people.get(name).getCredits());
    }
}