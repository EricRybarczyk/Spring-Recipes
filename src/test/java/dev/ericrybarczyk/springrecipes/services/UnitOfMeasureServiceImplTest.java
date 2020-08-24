package dev.ericrybarczyk.springrecipes.services;

import dev.ericrybarczyk.springrecipes.commands.UnitOfMeasureCommand;
import dev.ericrybarczyk.springrecipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dev.ericrybarczyk.springrecipes.domain.UnitOfMeasure;
import dev.ericrybarczyk.springrecipes.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UnitOfMeasureServiceImplTest {

    private UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void listAllUnitsOfMeasure() {
        // given
        Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        unitOfMeasureSet.add(uom1);
        unitOfMeasureSet.add(uom2);
        Mockito.when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureSet);

        // when
        Set<UnitOfMeasureCommand> resultSet = unitOfMeasureService.listAllUnitsOfMeasure();

        // then
        assertEquals(2, resultSet.size());
        Mockito.verify(unitOfMeasureRepository, Mockito.times(1)).findAll();
    }

}