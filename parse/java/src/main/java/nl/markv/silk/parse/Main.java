package nl.markv.silk.parse;

import java.net.URL;

import nl.markv.silk.example.Examples;
import nl.markv.silk.pojos.v0_2_0.Table;

public class Main {

	public static void main(String[] args) {
		for (URL examplePth : new Examples().urls()) {
			SilkParser parser = new Jackson2SilkParser();
			SilkDb silk = parser.parse(examplePth);
			System.out.println("Silk: " + silk.name() + " (" + silk.version() + ")");
			for (Table table : silk.tables()) {
				System.out.println(GsonSilkParser.gson.toJson(table));
			}
		}
	}
}
