package com.example;

import static com.thoughtworks.gauge.datastore.DataStoreFactory.getScenarioDataStore;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;

import com.thoughtworks.gauge.Step;

public class StepImplementation {

    private static final String DATASTORE_KEY = "count";
    private HashSet<Character> vowels;

    @Step("Vowels in English language are <vowelString>.")
    public void setLanguageVowels(String vowelString) {
        vowels = new HashSet<>();
        for (char ch : vowelString.toCharArray()) {
            vowels.add(ch);
        }
    }

    private int countVowels(String word) {
        int count = 0;
        for (char ch : word.toCharArray()) {
            if (vowels.contains(ch)) {
                count++;
            }
        }
        return count;
    }

	@Step("set expected vowel count in scenario datastore <Vowel Count>.")
	public void setExpectedVowelCount(int count) throws InterruptedException {
		getScenarioDataStore().put(DATASTORE_KEY, count);
	}

	@Step("<Word> should have expected vowel count.")
	public void shouldHaveExpectedVowelCount(String word){
        int actualCount = countVowels(word);
        assertThat(getScenarioDataStore().get(DATASTORE_KEY)).isEqualTo(actualCount);
	}
}
