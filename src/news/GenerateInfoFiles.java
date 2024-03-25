package news;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateInfoFiles {

	public static void GenerarVendedores(int cantVendedores) {
		Random rand = new Random();

		String[] names = { "Kevin", "Andres", "Juan", "Valentina", "Camila", "Natalia", "Jorge", "Roberto", "Maria",
				"Marcos", "Carlos", "Andrea", "Claudia" };
		String[] apellidos = { "Millan", "Alfonso", "Lara", "Perez", "Roa", "Rios", "Paez", "Diaz", "Costa", "Pan",
				"Gil", "Sanz", "Rosa", "Roca", "Leon", "Leal" };
		int numeroDocumento = 47436100;
		String tipoDocumento = "C.C";
		String name, apellido = "";

		try {
			
			FileWriter doc = new FileWriter("vendedores.csv");

			for (int i = 0; i < cantVendedores; i++) {

				name = names[rand.nextInt(0, names.length - 1)];

				apellido = apellidos[rand.nextInt(0, names.length - 1)];

				doc.write(
						tipoDocumento + ";" + String.valueOf(numeroDocumento + i) + ";" + name + ";" + apellido + "\n");

			}
			doc.flush();
			doc.close();

		} catch (IOException e) {
			System.out.println("Error en Vendedores " + e.getMessage());
		}
	}

	public static void GenerarProductos() {
		
	}

	public static void main(String[] args) {

		GenerarVendedores(10);

		GenerarProductos();

	}
}
