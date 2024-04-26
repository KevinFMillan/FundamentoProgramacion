package news;

import java.io.*;
import java.util.*;

public class GenerateInfoFiles {

	public static void CreateSalesManInfoFile(int salesmanCount) { //crea el .csv con los nombres de los vendedores
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

			for (int i = 0; i < salesmanCount; i++) {
				
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
		System.out.println("Creacion de archivo 'Vendedores.csv' Exitoso");
	}

	public static void CreateProductsFile(int productsCount) { // crea el .csv con los productos
		Random rand = new Random();

		String[] names = { "Lapiz", "Corrector", "Maleta", "Borrador", "Pegante", "Camisa", "Pantalon", "Gorrar",
				"Zapatos", "Medias", "Bufanda", "Abrigo", "Chaleco", "Casco", "Jarra", "Mesa", "Silla",
				"Destornillador", "Impresora", "Control remoto", "Monitor", "Regla", "Funda celular", "Cuaderno",
				"Sombrilla", "Espejo", "Antena", "Teclado", "Mouse", "Padmouse", "Celular", "Cargador", "Atomizador",
				"Camara", "Vaso", "Colador", "Exprimidor", "Licuadora", "Rodillo", "Almohada" };
		int ID = 1;
		int precioBase = 1000; //este valor se va multiplicar con un numero aleatorio > 0 para definir su precio
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		try {
			FileWriter doc = new FileWriter("Productos.csv");

			for (int i = 0; i < productsCount; i++) {

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
		System.out.println("Creacion de archivo 'Productos.csv' Exitoso");
	}

	public static void CreateSalesMenFiles() { // crea el .csv de ventas para el vendedor pasado en el parametro
		
		Random rand = new Random();
		String lineVendedores, lineProductos = null;
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		
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
				
				nameDoc = "Ventas "+infoVendedores[2]+" "+infoVendedores[3]+" "+infoVendedores[1]+".csv";
				
				FileWriter docVentas = new FileWriter(nameDoc);
				docVentas.append(infoVendedores[0]+";"+infoVendedores[1] + "\n");
				
				for (int i = 0; i < rand.nextInt(5,20); i++) {
					indice = IDs.get(rand.nextInt(0,cantProductos-1));
					cant = rand.nextInt(1,50);
					docVentas.append(String.valueOf(indice) + ";" + String.valueOf(cant) + "\n");
				}
				docVentas.flush();
				docVentas.close();
				System.out.println("Creacion de archivo '"+nameDoc+"' Exitoso");
			}
			
			docVendedores.close();
			docProductos.close();

		} catch (IOException e) {
			System.out.println("Error en Ventas " + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
	    CreateSalesManInfoFile(5);
	    CreateProductsFile(10);
	    CreateSalesMenFiles(); // Genera un .csv por cada vendedor con el informe de las ventas
	}
	
}

