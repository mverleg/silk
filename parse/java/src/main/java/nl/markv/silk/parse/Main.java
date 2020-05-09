package nl.markv.silk.parse;

import java.net.URL;

import nl.markv.silk.example.Examples;
import nl.markv.silk.serialize.Jackson2SilkSerializer;
import nl.markv.silk.types.SilkSchema;
import nl.markv.silk.types.Table;

public class Main {

	public static void main(String[] args) {
		for (URL examplePth : new Examples().urls()) {
			SilkParser parser = new Jackson2SilkParser();
			SilkSchema silk = parser.parse(examplePth);
			System.out.println("Silk: " + silk.name() + " (" + silk.silkVersion + ")");
			for (Table table : silk.tables()) {
				System.out.println(Jackson2SilkSerializer.toJson(table));
			}
		}
	}
}
