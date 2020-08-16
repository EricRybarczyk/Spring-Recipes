package dev.ericrybarczyk.springrecipes.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {

    private Category category;

    @Before
    public void setup() {
        category = new Category();
    }

    @Test
    public void getId() {
        final Long ID = 4L;
        category.setId(ID);
        assertEquals(ID, category.getId());
    }

    @Test
    public void getDescription() {
        final String DESCRIPTION = "category description";
        category.setDescription(DESCRIPTION);
        assertEquals(DESCRIPTION, category.getDescription());
    }

    @Test
    public void getRecipes() {

    }
}