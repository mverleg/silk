package nl.markv.silk.flatdag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import nl.markv.silk.types.ForeignKey;
import nl.markv.silk.types.Table;

import static java.util.Collections.indexOfSubList;
import static nl.markv.silk.flatdag.Flattener.dependencyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FlattenerTest {

	@SuppressWarnings("unchecked")
	private <E> List<E> listOf(E... items) {
		List<E> li = new ArrayList<>();
		Collections.addAll(li, items);
		return li;
	}

	@Nonnull
	private ForeignKey fk(@Nonnull Table table) {
		ForeignKey fk = new ForeignKey();
		fk.targetTable = table;
		return fk;
	}

	@Nonnull
	private Table table(@Nonnull String fromTable, @Nonnull List<ForeignKey> referToTable) {
		Table fk = new Table();
		fk.name = fromTable;
		fk.references = referToTable;
		return fk;
	}

	@Nested
	class TestLinear {
		// A -> D -> C -> E -> B
		Table b = table("B", listOf());
		Table e = table("E", listOf(fk(b)));
		Table c = table("C", listOf(fk(e)));
		Table d = table("D", listOf(fk(c)));
		Table a = table("A", listOf(fk(d)));

		@Test
		void input1() {
			List<Table> flat = dependencyOrder(listOf(a, b, c, d, e).iterator());
			assertEquals(listOf(a, d, c, e, b), flat);
		}

		@Test
		void input2() {
			List<Table> flat = dependencyOrder(listOf(e, d, c, b, a).iterator());
			assertEquals(listOf(a, d, c, e, b), flat);
		}

		@Test
		void input3() {
			List<Table> flat = dependencyOrder(listOf(b, e, c, d, a).iterator());
			assertEquals(listOf(a, d, c, e, b), flat);
		}

		@Test
		void input4() {
			List<Table> flat = dependencyOrder(listOf(a, d, c, e, b).iterator());
			assertEquals(listOf(a, d, c, e, b), flat);
		}
	}

	@Nested
	class TestFlatDiamond {
		//   A
		//   B
		//  C D
		//   E
		//  F G
		Table a = table("A", listOf());
		ForeignKey ra = fk(a);
		Table b = table("B", listOf(ra));
		ForeignKey rb = fk(b);
		Table c = table("C", listOf(rb));
		Table d = table("D", listOf(rb));
		ForeignKey rc = fk(c);
		ForeignKey rd = fk(d);
		Table e = table("E", listOf(rc, rd));
		ForeignKey re = fk(e);
		Table f = table("F", listOf(re));
		Table g = table("G", listOf(re));

		@Test
		void input1() {
			List<Table> flat = dependencyOrder(listOf(a, b, c, d, e, f, g).iterator());
			assertEquals(listOf(g, f, e, d, c, b, a), flat);
		}

		@Test
		void input2() {
			List<Table> flat = dependencyOrder(listOf(g, c, a, d, e, f, b).iterator());
			assertEquals(listOf(g, f, e, d, c, b, a), flat);
		}

		@Test
		void input3() {
			List<Table> flat = dependencyOrder(listOf(g, f, e, d, c, b, a).iterator());
			assertEquals(listOf(g, f, e, d, c, b, a), flat);
		}

		@Test
		void input4() {
			List<Table> flat = dependencyOrder(listOf(f, c, g, a, d, b, e).iterator());
			assertEquals(listOf(g, f, e, d, c, b, a), flat);
		}

	}

	@Nested
	class TestDisjoint {
		//   AB  G
		//  FEDC H I
		Table a = table("A", listOf());
		Table b = table("B", listOf());
		ForeignKey ra = fk(a);
		ForeignKey rb = fk(b);
		Table f = table("F", listOf(ra, rb));
		Table e = table("E", listOf(rb, rb));
		Table d = table("D", listOf(ra, rb));
		Table c = table("C", listOf(ra, rb));
		Table g = table("G", listOf());
		ForeignKey rg = fk(g);
		Table h = table("H", listOf(rg));
		Table i = table("I", listOf());

		void validate(@Nonnull List<Table> flat) {
			// This check is too strict; A and B can switch, as can any of CDEF.
			assertNotEquals(-1, indexOfSubList(flat, listOf(f, e, d, c, b, a)));
			assertNotEquals(-1, indexOfSubList(flat, listOf(h, g)));
			assertNotEquals(-1, indexOfSubList(flat, listOf(i)));
		}

		@Test
		void input1() {
			List<Table> flat = dependencyOrder(listOf(a, b, c, d, e, f, g, h, i).iterator());
			validate(flat);
		}

		@Test
		void input2() {
			List<Table> flat = dependencyOrder(listOf(i, h, g, f, e, d, c, b, a).iterator());
			validate(flat);
		}

		@Test
		void input3() {
			List<Table> flat = dependencyOrder(listOf(d, h, f, e, a, c, i, g, b).iterator());
			validate(flat);
		}
	}

	@DisplayName("For the case of cyclic dependencies, test that the algorithm terminates and contains all the nodes exactly once (order is arbitrary)")
	@Nested
	class testCycle {
		// A -> D -> C -> B |
		// ^----------------<
		Table b = table("B", listOf());
		Table c = table("C", listOf(fk(b)));
		Table d = table("D", listOf(fk(c)));
		Table a = table("A", listOf(fk(d)));
		{ b.references.add(fk(a)); }

		@Test
		void terminates() {
			List<Table> flat1 = dependencyOrder(listOf(a, b, c, d).iterator());
			assertEquals(new HashSet<>(listOf(a, b, c, d)), new HashSet<>(flat1));
		}
	}
}
