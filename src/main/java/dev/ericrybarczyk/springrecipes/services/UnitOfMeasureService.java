package dev.ericrybarczyk.springrecipes.services;

import dev.ericrybarczyk.springrecipes.commands.UnitOfMeasureCommand;
import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUnitsOfMeasure();

}
