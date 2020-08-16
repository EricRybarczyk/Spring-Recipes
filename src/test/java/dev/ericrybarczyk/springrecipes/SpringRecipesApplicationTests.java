package dev.ericrybarczyk.springrecipes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/*
	Note: I adjusted this to JUnit 4 because the tutorial course starts with JUnit 4 before migrating later to Junit 5
	If I migrate everything to Junit 5 later, I should add back the exclusion to the JUnit dependency in the POM.xml
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRecipesApplicationTests {

	@Test
	public void contextLoads() {
	}

}
