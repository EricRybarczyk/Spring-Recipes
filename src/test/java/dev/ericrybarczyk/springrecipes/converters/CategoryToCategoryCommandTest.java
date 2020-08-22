package dev.ericrybarczyk.springrecipes.converters;

import dev.ericrybarczyk.springrecipes.commands.CategoryCommand;
import dev.ericrybarczyk.springrecipes.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "description";

    private CategoryToCategoryCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryToCategoryCommand();
    }
    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    public void testConvert() {
        // given
        Category category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);

        // when
        CategoryCommand result = converter.convert(category);

        // then
        assertNotNull(result);
        assertEquals(ID, result.getId());
        assertEquals(DESCRIPTION, result.getDescription());
    }

}