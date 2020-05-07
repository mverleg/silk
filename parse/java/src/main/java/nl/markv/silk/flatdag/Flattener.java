package nl.markv.silk.flatdag;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import nl.markv.silk.pojos.v0_1_0.ForeignKey;
import nl.markv.silk.pojos.v0_1_0.Table;

public class Flattener {

	/**
	 * Attempt to put the columns in dependency order, so that each column only has FK relations
	 * to earlier ones. This property will only hold if references are non-cyclic.
	 */
	@Nonnull
	public static List<Table> dependencyOrder(@Nonnull Iterator<Table> originalColumns) {

		// Add all the nodes to a to-do list.
		Map<String, Node> todo = new HashMap<>();
		while (originalColumns.hasNext()) {
			Table col = originalColumns.next();
			todo.put(col.name, new Node(col));
		}

		while (!todo.isEmpty()) {
			Node tree = todo.values().iterator().next();
			for (ForeignKey ref : tree.table.references) {
				if (todo.containsKey(ref.table)) {
					
				} else {
					// There is a cycle; skip this item
				}
			}
		}

	}
}
