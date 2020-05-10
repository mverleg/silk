package nl.markv.silk.flatdag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.markv.silk.types.ForeignKey;
import nl.markv.silk.types.Table;

import static nl.markv.silk.flatdag.Flattener.dependencyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FlattenerTest {

	@SuppressWarnings("unchecked")
	private <E> List<E> listOf(E... items) {
		List<E> li = new ArrayList<>();
		Collections.addAll(li, items);
		return li;
	}

	@Nonnull
	private ForeignKey fk(@Nonnull String name) {
		ForeignKey fk = new ForeignKey();
		fk.name = name;
		return fk;
	}

	@Nonnull
	private Table table(@Nonnull String fromTable, @Nonnull List<ForeignKey> referToTable) {
		Table fk = new Table();
		fk.name = fromTable;
		fk.references = referToTable;
		return fk;
	}

	@Test
	void testLinear() {
		// A -> D -> C -> E -> B
		Table b = table("B", listOf());
		Table e = table("E", listOf(fk(b.name)));
		Table c = table("C", listOf(fk(e.name)));
		Table d = table("D", listOf(fk(c.name)));
		Table a = table("A", listOf(fk(d.name)));

		List<Table> flat1 = dependencyOrder(listOf(a, b, c, d, e).iterator());
		assertEquals(listOf(b, e, c, d, a), flat1);

		List<Table> flat2 = dependencyOrder(listOf(e, d, c, b, a).iterator());
		assertEquals(listOf(b, e, c, d, a), flat2);

		List<Table> flat3 = dependencyOrder(listOf(b, e, c, d, a).iterator());
		assertEquals(listOf(b, e, c, d, a), flat3);

		List<Table> flat4 = dependencyOrder(listOf(e, d, c, e, b).iterator());
		assertEquals(listOf(b, e, c, d, a), flat4);
	}

	@Test
	void testFlatDiamond() {
		//   A
		//   B
		//  C D
		//   E
		//  F G
		Table a = table("A", listOf());
		ForeignKey ra = fk(a.name);
		Table b = table("B", listOf(ra));
		ForeignKey rb = fk(b.name);
		Table c = table("C", listOf(rb));
		Table d = table("D", listOf(rb));
		ForeignKey rc = fk(c.name);
		ForeignKey rd = fk(d.name);
		Table e = table("E", listOf(rc, rd));
		ForeignKey re = fk(e.name);
		Table f = table("F", listOf(re));
		Table g = table("G", listOf(re));

		List<Table> flat1 = dependencyOrder(listOf(a, b, c, d, e, f, g).iterator());
		assertEquals(listOf(g, f, e, d, c, b, a), flat1);

		List<Table> flat2 = dependencyOrder(listOf(g, c, a, d, e, f, b).iterator());
		assertEquals(listOf(g, f, e, d, c, b, a), flat2);

		List<Table> flat3 = dependencyOrder(listOf(g, f, e, d, c, b, a).iterator());
		assertEquals(listOf(g, f, e, d, c, b, a), flat3);

		List<Table> flat4 = dependencyOrder(listOf(f, c, g, a, d, b, e).iterator());
		assertEquals(listOf(g, f, e, d, c, b, a), flat4);
	}

	@Test
	void testIndependent() {
		//   AB  G
		//  FEDC H I
		Table a = table("A", listOf());
		Table b = table("B", listOf());
		ForeignKey ra = fk(a.name);
		ForeignKey rb = fk(b.name);
		Table f = table("F", listOf(ra, rb));
		Table e = table("E", listOf(rb, rb));
		Table d = table("D", listOf(ra, rb));
		Table c = table("C", listOf(ra, rb));
		Table g = table("G", listOf());
		ForeignKey rg = fk(g.name);
		Table h = table("H", listOf(rg));
		Table i = table("I", listOf());

		List<Table> flat1 = dependencyOrder(listOf(a, b, c, d, e, f, g, h, i).iterator());
		assertEquals(listOf(f, e, d, c, b, a, h, g, i), flat1);

		List<Table> flat2 = dependencyOrder(listOf(i, h, g, f, e, d, c, b, a).iterator());
		assertEquals(listOf(i, h, g, f, e, d, c, b, a), flat2);

		List<Table> flat3 = dependencyOrder(listOf(d, h, f, e, a, c, i, g, b).iterator());
		assertEquals(listOf(i, h, g, f, e, d, c, b, a), flat3);
	}

	@DisplayName("For the case of cyclic dependencies, test that the algorithm terminates and contains all the nodes exactly once (order is arbitrary)")
	@Test
	void testCycle() {
		// A -> D -> C -> B |
		// ^----------------<
		Table b = table("B", listOf());
		Table c = table("C", listOf(fk(b.name)));
		Table d = table("D", listOf(fk(c.name)));
		Table a = table("A", listOf(fk(d.name)));
		b.references.add(fk(a.name));

		List<Table> flat1 = dependencyOrder(listOf(a, b, c, d).iterator());
		assertEquals(new HashSet<>(listOf(a, b, c, d)), new HashSet<>(flat1));
	}
}
