package nl.markv.silk.flatdag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import nl.markv.silk.pojos.v0_1_0.ForeignKey;
import nl.markv.silk.pojos.v0_1_0.Table;

import static nl.markv.silk.flatdag.Flattener.dependencyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FlattenerTest {

	@SuppressWarnings("unchecked")
	private <E> List<E> listOf(E... items) {
		List<E> li = new ArrayList<>();
		Collections.addAll(li, items);
		return li;
	}

	@Test
	void testFlatDiamond() {
		//   A
		//   B
		//  C D
		//   E
		//  F G
		Table a = new Table("A", null, null, null, null, listOf(), null, null, null);
		ForeignKey ra = new ForeignKey(null, a.name, null);
		Table b = new Table("B", null, null, null, null, listOf(ra), null, null, null);
		ForeignKey rb = new ForeignKey(null, b.name, null);
		Table c = new Table("C", null, null, null, null, listOf(rb), null, null, null);
		Table d = new Table("D", null, null, null, null, listOf(rb), null, null, null);
		ForeignKey rc = new ForeignKey(null, c.name, null);
		ForeignKey rd = new ForeignKey(null, d.name, null);
		Table e = new Table("E", null, null, null, null, listOf(rc, rd), null, null, null);
		ForeignKey re = new ForeignKey(null, e.name, null);
		Table f = new Table("F", null, null, null, null, listOf(re), null, null, null);
		Table g = new Table("G", null, null, null, null, listOf(re), null, null, null);

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
		//  CDEF H I
		Table a = new Table("A", null, null, null, null, listOf(), null, null, null);
		Table b = new Table("B", null, null, null, null, listOf(), null, null, null);
		ForeignKey ra = new ForeignKey(null, a.name, null);
		ForeignKey rb = new ForeignKey(null, b.name, null);
		Table c = new Table("C", null, null, null, null, listOf(ra, rb), null, null, null);
		Table d = new Table("D", null, null, null, null, listOf(rb, rb), null, null, null);
		Table e = new Table("E", null, null, null, null, listOf(ra, rb), null, null, null);
		Table f = new Table("F", null, null, null, null, listOf(ra, rb), null, null, null);
		Table g = new Table("G", null, null, null, null, listOf(), null, null, null);
		ForeignKey rg = new ForeignKey(null, g.name, null);
		Table h = new Table("H", null, null, null, null, listOf(rg), null, null, null);
		Table i = new Table("I", null, null, null, null, listOf(), null, null, null);

		List<Table> flat1 = dependencyOrder(listOf(a, b, c, d, e, f, g, h, i).iterator());
		assertEquals(listOf(f, e, d, c, b, a, h, g, i), flat1);

		List<Table> flat2 = dependencyOrder(listOf(i, h, g, f, e, d, c, b, a).iterator());
		assertEquals(listOf(i, h, g, f, e, d, c, b, a), flat2);

		List<Table> flat3 = dependencyOrder(listOf(d, h, f, e, a, c, i, g, b).iterator());
		assertEquals(listOf(i, h, g, f, e, d, c, b, a), flat3);
	}
}