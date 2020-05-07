package nl.markv.silk.flatdag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import nl.markv.silk.pojos.v0_1_0.Table;

public class Node {

	public final Table table;
	public final List<Node> older = new ArrayList<>();
	public final List<Node> newer = new ArrayList<>();

	public Node(@Nonnull Table table) {
		this.table = table;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Node node = (Node) o;
		return Objects.equals(table.name, node.table.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(table.name);
	}
}
