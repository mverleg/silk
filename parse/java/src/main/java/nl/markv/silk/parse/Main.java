package nl.markv.silk.parse;

import java.nio.file.Paths;

import nl.markv.silk.pojos.v0_1_0.Table;

public class Main {

	public static void main(String[] args) {
		SilkParser parser = new GsonSilkParser();
		SilkDb silk = parser.parse(Paths.get("..", "..", "example", "shop.json"));
		System.out.println("Silk " + silk.version());
		for (Table table : silk.tables()) {
			System.out.println(GsonSilkParser.gson.toJson(table));
		}
	}
}
