import java.util.Scanner;

public class lastform {

	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		String input1 = console.nextLine().replace(" ", "");// The first line of the input(i delete the spaces to not to
															// have any struggle with them)
		String input2 = console.nextLine().replace(" ", "");// The second line of the input
		String input3 = console.nextLine().replace(" ", "");// The third line of the input
		String input4 = console.nextLine().replace(" ", "");// The fourth line of the input
		String name1 = inputNameCatch(input1);// Name of the variable in the first line(See inputNameCatch method)
		String name2 = inputNameCatch(input2);// Name of the variable in the second line
		String name3 = inputNameCatch(input3);// Name of the variable in the third line
		String value1 = valueCatch(input1, input1);// Value of variable one
		String value2 = valueCatch(input2, input2);// Value of variable two
		String value3 = valueCatch(input3, input3);// Value of variable three
		String variable = input4;// Just to increase legibility and to easy coding
		variable = variable.replaceAll(name1, value1);// To replace the value of the variable with its name if it exists
														// in the third line
		variable = variable.replaceAll(name2, value2);
		variable = variable.replaceAll(name3, value3);
		variable = variable.replace(";", "");// To get rid of ";"
		variable = "(" + variable + ")";
		while (variable.contains("*") || variable.contains("/") || variable.contains("+") || variable.contains("-")
				|| variable.contains("(") || variable.contains(")")) {// To repeat every action until there is nothing
																		// left to do
			if (variable.contains("(")) {// To start with the parentheses
				String inside = parentheses(variable);// inside of the innermost parentheses
				if (inside.contains("*") || inside.contains("/")) {// Sends the inside to the multiplication or division
																	// method if it contains "*" or "/"
					String result = multdiv(inside);// Gets the result
					if (result.contains("-") || result.contains("+")) {// Sends input to sumsub method after performing
																		// multiplication or division if there is any
																		// subtraction or addition
						result = sumsub(result);
					}
					variable = variable.replace("(" + inside + ")", result);// Replaces the parentheses with its result
					continue;
				} else if (inside.contains("+") || inside.contains("-")) {// Sends the inside to the addition or
																			// subtraction method if it contains "*" or
																			// "/"
					String result = sumsub(inside);
					variable = variable.replace("(" + inside + ")", result);// Replaces the parentheses with its result
					continue;
				} else {// if there is nothing to perform inside it basically deletes parentheses
					variable = variable.replace("(" + inside + ")", inside);// Replaces the parentheses with inside of
																			// it
				}
				continue;
			} else {// if there is no parentheses we do the operations again
				if (variable.contains("*") || variable.contains("/")) {// first we do the multiplication & division
					String result = multdiv(variable);
					variable = variable.replace(variable, result);// and we replace it with its result
					continue;
				} else if (variable.contains("+") || variable.contains("-")) {// then we do the subtraction & addition
					String result = sumsub(variable);
					variable = variable.replace(variable, result);// and we replace it with its result
					continue;
				}
				continue;
			}
		}
		System.out.println(variable);// After everything is done we print the result to the console
		console.close();
	}

	// This method finds the name of the variables in the first three lines
	public static String inputNameCatch(String s) {
		String name = "";// This method stores the name in here and return this
		if (s.contains("int") || s.contains("double")) {// if the variable is integer the line must contain "int" so i
														// check for it
			int position = 0;// position of "="
			for (int i = 0; i < s.length(); i++) {// to find the position i check elements of the string starting from
													// beginning
				if (s.charAt(i) == '=') {
					position = i;
					break;
				}
			}
			if (s.contains("int")) {
				s = s.replace("int", "");// to take only the name i get rid of "int"
				name = s.substring(0, position - 3);// and take the part until "=" as the name of the variable
			} else {
				s = s.replace("double", "");// to take only the name i get rid of "double"
				name = s.substring(0, position - 6);// and take the part until "=" as the name of the variable
			}
		}
		return name;

	}

	// this method finds the value of the variable in the input
	public static String valueCatch(String s, String type) {
		int equal = 0;
		int last = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '=') {// finds the index of "="
				equal = i;
			} else if (s.charAt(i) == ';') {// finds the index of ";"
				last = i;
			}
		}
		String value = s.substring(equal + 1, last);// as far as i got rid of the spaces value is the thing between "="
													// & ";"
		if (type.contains("double") && !value.contains(".")) {// if the value declared as double but like "double d= 3;"
																// i add ".0" to the end
			value += ".0";
		}
		return value;
	}

	// this method performs multiplication or division
	public static String multdiv(String s) {
		while (s.contains("*") || s.contains("/")) {// repeats the process until there is no "*" or "/"left
			String newStr1 = "";// left side of the operator
			String newStr2 = "";// right side of the operator
			int crosdiv = 0;// index of "*" or "/"
			double leftd = 0;// to store the left side value if it is double
			double rightd = 0;// to store the right side value if it is double
			int lefti = 0;// to store the left side value if it is integer
			int righti = 0;// to store the right side value if it is integer
			for (int i = 0; i < s.length(); i++) {// to find the position of the operator
				if (s.charAt(i) == '*' || s.charAt(i) == '/') {
					crosdiv = i;
					break;
				}
			}
			for (int i = crosdiv - 1; i >= 0; i--) {// to find the left side value starts from the operator and counts
													// backwards until another operator
				if (s.charAt(i) <= '9' && s.charAt(i) >= '0' || s.charAt(i) == '.') {
					newStr1 = s.charAt(i) + newStr1;
				} else {
					break;
				}
			}
			for (int i = crosdiv + 1; i < s.length(); i++) {// to find the right side value starts from the operator and
															// counts forward until another operator
				if ((s.charAt(i) <= '9' && s.charAt(i) >= '0') || s.charAt(i) == '.') {
					newStr2 += s.charAt(i);
				} else {
					break;
				}
			}
			if (newStr1.contains(".") || newStr2.contains(".")) {// if at least one of the two sides contains double
																	// value parses the strings to double
				leftd = Double.parseDouble(newStr1);
				rightd = Double.parseDouble(newStr2);
			} else {// if two sides are integers parses strings to integer
				lefti = Integer.parseInt(newStr1);
				righti = Integer.parseInt(newStr2);
			}
			if (s.charAt(crosdiv) == '*') {// if we need to perform multiplication
				if (newStr1.contains(".") || newStr2.contains(".")) {// if at least one of the two sides contains "."
																		// takes the double values and multiples them
					String cros = Double.toString(leftd * rightd);
					s = s.replace(newStr1 + s.charAt(crosdiv) + newStr2, cros);// then replaces the result back
				} else {// if two sides are integers takes the integer values and multiples them
					String cros = Integer.toString(lefti * righti);
					s = s.replace(newStr1 + s.charAt(crosdiv) + newStr2, cros);// then replaces the result back
				}

			} else if (s.charAt(crosdiv) == '/') {// if we need to perform division
				if (newStr1.contains(".") || newStr2.contains(".")) {// if at least one of the two sides contains "."
																		// takes the double values and divides them
					String div = Double.toString(leftd / rightd);
					s = s.replace(newStr1 + s.charAt(crosdiv) + newStr2, div);// then replaces the result back
				} else {// if two sides are integers takes the integer values and makes integer division
					String div = Integer.toString(lefti / righti);
					s = s.replace(newStr1 + s.charAt(crosdiv) + newStr2, div);// then replaces the result back
				}
			}
		}
		return s;
	}

	public static String sumsub(String s) {
		while (s.contains("+") || s.contains("-")) {
			String newStr1 = "";
			String newStr2 = "";
			int sumsub = 0;
			double leftd = 0;
			double rightd = 0;
			int lefti = 0;
			int righti = 0;
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == '+' || s.charAt(i) == '-') {
					sumsub = i;
					break;
				} else {
					newStr1 += s.charAt(i);
				}
			}
			for (int i = sumsub + 1; i < s.length(); i++) {
				if ((s.charAt(i) <= '9' && s.charAt(i) >= '0') || s.charAt(i) == '.') {
					newStr2 += s.charAt(i);
				} else {
					break;
				}
			}
			if (newStr1.contains(".") || newStr2.contains(".")) {
				leftd = Double.parseDouble(newStr1);
				rightd = Double.parseDouble(newStr2);
			} else {
				lefti = Integer.parseInt(newStr1);
				righti = Integer.parseInt(newStr2);
			}

			if (s.charAt(sumsub) == '+') {
				if (newStr1.contains(".") || newStr2.contains(".")) {
					String plus = Double.toString(leftd + rightd);
					s = s.replace(newStr1 + s.charAt(sumsub) + newStr2, plus);
				} else {
					String plus = Integer.toString(lefti + righti);
					s = s.replace(newStr1 + s.charAt(sumsub) + newStr2, plus);
				}

			} else if (s.charAt(sumsub) == '-') {
				if (newStr1.contains(".") || newStr2.contains(".")) {
					String minus = Double.toString(leftd - rightd);
					s = s.replace(newStr1 + s.charAt(sumsub) + newStr2, minus);
				} else {
					String minus = Integer.toString(lefti - righti);
					s = s.replace(newStr1 + s.charAt(sumsub) + newStr2, minus);
				}
			}
		}
		return s;
	}

	public static String parentheses(String s) {
		int first = 0;
		int second = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				first = i;
				continue;
			} else if (s.charAt(i) == ')') {
				second = i;
				break;
			}
		}
		return s.substring(first + 1, second);
	}
}
