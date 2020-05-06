package nl.markv.silk.parse;

import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {
		SilkParser parser = new GsonSilkParser();
		SilkDb silk = parser.parse(Paths.get("..", "..", "example", "shop.json"));
		System.out.println(GsonSilkParser.gson.toJson(silk));
	}
}
