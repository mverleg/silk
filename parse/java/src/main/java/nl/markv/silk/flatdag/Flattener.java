package nl.markv.silk.flatdag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import nl.markv.silk.types.ForeignKey;
import nl.markv.silk.types.Table;

public class Flattener {

	/**
	 * Attempt to put the columns in dependency order, so that each column only has FK relations
	 * to earlier ones. This property will only hold if references are non-cyclic.
	 * In the case of multiple solutions or cycles, the order is arbitrary but stable.
	 */
	@Nonnull
	@CheckReturnValue
	public static List<Table> dependencyOrder(@Nonnull Iterator<Table> originalColumns) {

		// Collect the list in reverse order, because it's faster to grow that direction.
		List<Table> reverseList = new ArrayList<>();
		// Create a to-do map, which as two purposes:
		// - Serve as a lookup of not-yet-seen items.
		// - Connect referred tables to their name.
		Map<String, Table> todos = new HashMap<>();
		while (originalColumns.hasNext()) {
			Table table = originalColumns.next();
			todos.put(table.name, table);
		}

		// Iterate over all the items, skipping known ones.
		// This finds disconnected groups, and fixes bad starting points.
		while (!todos.isEmpty()) {
			String tableName = todos.keySet().iterator().next();
			recursivelyAddDependencies(reverseList, todos, tableName);
		}

		Collections.reverse(reverseList);
		return reverseList;
	}

	private static void recursivelyAddDependencies(@Nonnull List<Table> reverseList, @Nonnull Map<String, Table> todos, @Nonnull String currentTableName) {
		if (!todos.containsKey(currentTableName)) {
			// This might be a cycle, but might also just be next loop item that's also a dependency.
			return;
		}
		Table currentTable = todos.remove(currentTableName);
		for (ForeignKey referenceTable : currentTable.references) {
			recursivelyAddDependencies(reverseList, todos, referenceTable.targetTable.name);
		}
		reverseList.add(currentTable);
	}
}
