package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Empty {
	public static void main (String[] args) {
		try {
			String fileName = "bin/data/BaseData0";

			// delete and re-create file :
			File aFile = new File(fileName);
			aFile.delete();
			aFile.createNewFile();

			// write into file :

			try {

				BufferedWriter toFile = new BufferedWriter(new FileWriter(
						fileName));

				for (short i = 0; i < 300; i++) {
					for (short j = 0; j < 300; j++) {
							toFile.write("Grass");
							toFile.newLine();
					}
				}
				

				toFile.close();

				// MainProg.alert("Data Saved");
			} catch (IOException e) {
				System.out.println(e);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
