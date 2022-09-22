package ro.rasel.java.methodreferance;

import java.util.function.Predicate;

public class PredUse {
	public static void main(String[] args) {
		Predicate<String> predContains = "I am going to write OCP8 exam"::contains;
		checkString(predContains, "OCPJP");
	}

	static void checkString(Predicate<String> predicate, String str) {
		System.out.println(predicate.test(str) ? "contains" : "doesn't contain");
	}
}