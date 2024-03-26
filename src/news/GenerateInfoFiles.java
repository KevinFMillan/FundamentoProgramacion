package news;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GenerateInfoFiles {

	public static void GenerarVendedores(int cantVendedores) {
		Random rand = new Random();

		String[] names = { "Kevin", "Andres", "Juan", "Valentina", "Camila", "Natalia", "Jorge", "Roberto", "Maria",
				"Marcos", "Carlos", "Andrea", "Claudia" };
		String[] apellidos = { "Millan", "Alfonso", "Lara", "Perez", "Roa", "Rios", "Paez", "Diaz", "Costa", "Pan",
				"Gil", "Sanz", "Rosa", "Roca", "Leon", "Leal" };
		int numeroDocumento = 47436100;
		String tipoDocumento = "C.C";
		String name, apellido ;

		try {
			FileWriter doc = new FileWriter("Vendedores.csv");

			for (int i = 0; i < cantVendedores; i++) {
				
				name = names[rand.nextInt(0, names.length - 1)] + " " + names[rand.nextInt(0, names.length - 1)];
				apellido = apellidos[rand.nextInt(0, names.length - 1)] + " " + apellidos[rand.nextInt(0, names.length - 1)];

				doc.write(
						tipoDocumento + ";" + String.valueOf(numeroDocumento + i) + ";" + name + ";" + apellido + "\n");

			}
			doc.flush();
			doc.close();

		} catch (IOException e) {
			System.out.println("Error en Vendedores " + e.getMessage());
		}
	}

	public static void GenerarProductos(int cantProductos) {
		Random rand = new Random();

		String[] names = { "Lapiz", "Corrector", "Maleta", "Borrador", "Pegante", "Camisa", "Pantalon", "Gorrar",
				"Zapatos", "Medias", "Bufanda", "Abrigo", "Chaleco", "Casco", "Jarra", "Mesa", "Silla",
				"Destornillador", "Impresora", "Control remoto", "Monitor", "Regla", "Funda celular", "Cuaderno",
				"Sombrilla", "Espejo", "Antena", "Teclado", "Mouse", "Padmouse", "Celular", "Cargador", "Atomizador",
				"Camara", "Vaso", "Colador", "Exprimidor", "Licuadora", "Rodillo", "Almohada" };
		int ID = 1;
		int precioBase = 100;
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		try {
			FileWriter doc = new FileWriter("Productos.csv");

			for (int i = 0; i < cantProductos; i++) {

				String name = names[rand.nextInt(0,names.length)];

				int Precio = precioBase * rand.nextInt(1, 1000);

				doc.write(String.valueOf(ID + i) + ";" + name + ";" + String.valueOf(Precio) + "\n");
				IDs.add(ID + i);

			}
			doc.flush();
			doc.close();

		} catch (IOException e) {
			System.out.println("Error en Productos " + e.getMessage());
		}
	}

	public static void GenerarInfoVentas() {
		
		Random rand = new Random();
		String lineVendedores, lineProductos = null;
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		ArrayList<Integer> repetidos = new ArrayList<Integer>();
		
		try {
			
			String nameDoc;
			
			FileReader docVendedores = new FileReader("Vendedores.csv");
			BufferedReader leerVendedores = new BufferedReader(docVendedores);

			FileReader docProductos = new FileReader("Productos.csv");
			BufferedReader leerProductos = new BufferedReader(docProductos);

			while ((lineProductos = leerProductos.readLine()) != null) {
				String[] inforProductos = lineProductos.split(";");
				IDs.add(Integer.valueOf(inforProductos[0]));
			}
			int cantProductos = IDs.size();
			int indice, cant;
			
			while ((lineVendedores = leerVendedores.readLine()) != null) {
				
				String[] infoVendedores = lineVendedores.split(";");
				
				nameDoc = "Ventas "+infoVendedores[2]+infoVendedores[3]+infoVendedores[1]+".csv";
				
				FileWriter docVentas = new FileWriter(nameDoc);
				docVentas.append(infoVendedores[0]+";"+infoVendedores[1] + "\n"); //hasta aqui vamos ok
				
				for (int i = 0; i < rand.nextInt(5,20); i++) {
					indice = IDs.get(rand.nextInt(0,cantProductos-1));
					cant = rand.nextInt(1,50);
					docVentas.append(String.valueOf(indice) + ";" + String.valueOf(cant) + "\n");
				}
				docVentas.flush();
				docVentas.close();
			}
			
			docVendedores.close();
			docProductos.close();

		} catch (IOException e) {
			System.out.println("Error en Ventas " + e.getMessage());
		}
	}

	public static void main(String[] args) {

		GenerarVendedores(15);

		GenerarProductos(30);

		GenerarInfoVentas();
	}
}
