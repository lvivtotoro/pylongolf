package org.midnightas.pylongolf;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("unused")
public class PylonGolf {

	public static final void main(String[] args) throws Exception {
		String content = new String(Files.readAllBytes(Paths.get(new File(args[0]).toURI()))) + " ";
		List<Object> stack = new ArrayList<Object>();
		Scanner scanner = new Scanner(new UnClosableDecorator(System.in));
		boolean whileStatement = false;
		int whileIndex = 0;
		int selectedIndex = 0;
		HashMap<String, Object> vars = new HashMap<String, Object>();
		for (int l = 0; l < content.length(); l++) {
			char c = content.charAt(l);
			if (isNumber(c)) {
				String number = "";
				for (int l0 = l; l0 < content.length(); l0++) {
					if (isNumber(content.charAt(l0))) {
						number += content.charAt(l0);
					} else {
						l = l0 - 1;
						stack.add(Double.parseDouble(number));
						break;
					}
				}
			} else if (c == ';') {
				System.out.println(Arrays.toString(stack.toArray()) + Arrays.toString(vars.values().toArray()));
			} else if (c == '+') {
				Object lis = stack.remove(stack.size() - 1);
				Object lis2 = stack.remove(stack.size() - 1);
				if (lis instanceof Number && lis2 instanceof Number)
					stack.add(((Number) lis).doubleValue() + ((Number) lis2).doubleValue());
				else
					stack.add(lis.toString() + lis2.toString());
			} else if (c == '-') {
				stack.add(((Number) stack.remove(stack.size() - 2)).doubleValue()
						- ((Number) stack.remove(stack.size() - 1)).doubleValue());
			} else if (c == '*') {
				stack.add(((Number) stack.remove(stack.size() - 1)).doubleValue()
						* ((Number) stack.remove(stack.size() - 1)).doubleValue());
			} else if (c == '/') {
				stack.add(((Number) stack.remove(stack.size() - 2)).doubleValue()
						/ ((Number) stack.remove(stack.size() - 1)).doubleValue());
			} else if (c == '"') {
				String string = "\"";
				for (int l0 = l + 1; l0 < content.length(); l0++) {
					string += content.charAt(l0);
					if (content.charAt(l0) == '"') {
						stack.add(string.substring(1, string.length() - 1));
						l = l0;
						break;
					}
				}
			} else if (c == '>') {
				whileStatement = true;
				whileIndex = l;
			} else if (c == '<') {
				l = whileIndex;
			} else if (c == '!') {
				break;
			} else if (c == '.') {
				stack = new ArrayList<Object>();
			} else if (c == '@') {
				whileStatement = false;
			} else if (c == '[') {
				stack.add(selectedIndex, Integer.parseInt(stack.remove(selectedIndex) + ""));
			} else if (c == '_') {
				stack.add(parse(scanner.nextLine()));
			} else if (c == '\'') {
				selectedIndex = stack.size() == 0 ? 0 : stack.size() - 1;
			} else if (c == '|') {
				String string = (String) stack.get(selectedIndex);
				Object[] newArray = (Object[]) string.split(content.charAt(l + 1) + "");
				stack.add(newArray);
				l += newArray.length;
			} else if (c == ':') {
				String var = content.charAt(l + 1) + "";
				vars.remove(var);
				vars.put(var, stack.remove(selectedIndex));
				l++;
			} else if (c == 'ƀ') {

			} else if (c == ')') {
				System.out.println(stack.get(selectedIndex).toString());
			} else if (c == '~') {
				for (Object obj : stack)
					if (obj instanceof Double && Math.floor((double) obj) == (double) obj) {
						System.out.print(((Double) obj).intValue());
					} else
						System.out.print(obj);
				System.out.println();
			} else if (c >= 'A' && c <= 'Z') {
				stack.add(vars.get(c + ""));
			} else if (c == '}') {
				stack.add(new Date() {
					private static final long serialVersionUID = 1L;

					public String toString() {
						return getHours() + ":" + getMinutes() + ":" + getSeconds();
					}
				});
			} else if (c == 'r') {
				stack.add(new Random().nextInt((Integer) stack.remove(stack.size() - 1)));
				selectedIndex = stack.size() - 1;
			} else if (c == '=') {
				Object item = stack.get(selectedIndex);
				if (item instanceof Double) {
					Double dbl = (Double) item;
					String number = "";
					for (int l0 = l; l0 < content.length(); l0++) {
						if (isNumber(content.charAt(l0))) {
							number += content.charAt(l0);
						} else {
							l = l0;
							if (dbl.compareTo(Double.valueOf(number)) == 0) {
								// TODO: Fix this
							}
							break;
						}
					}
				}
			} else if (c == 'w') {
				Object item = stack.get(selectedIndex);
				if (item instanceof Double) {
					Thread.sleep(((Double) item).longValue());
				}
			} else if (c == 'p') {
				Desktop.getDesktop().browse(new URI("http://" + (String) stack.get(selectedIndex)));
			}
		}
		scanner.close();
	}

	public static Object parse(String string) {
		try {
			return Double.parseDouble(string);
		} catch (NumberFormatException e) {
			return string;
		}
	}

	public static boolean isNumber(char c) {
		return c >= '0' && c <= '9';
	}

}
