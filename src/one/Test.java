package one;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FileOutputStream out = new FileOutputStream("theTime");
			ObjectOutputStream sout = new ObjectOutputStream(out);
			sout.writeObject("Today ");
			sout.writeObject(new Thing(10, 0, 0, 0, null));
			sout.flush();
			FileInputStream in = new FileInputStream("theTime");
			ObjectInputStream sin = new ObjectInputStream(in);
			String today = (String)sin.readObject();
			Thing thing = (Thing)sin.readObject();
			System.out.println(today+thing.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
