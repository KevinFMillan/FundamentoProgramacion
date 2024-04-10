package news;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
		ArrayList<Integer> repetidos = new ArrayList<Integer>(); // se usara mas adelate
		
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

	public static void ProcesarProductos(File archivoProductos) {
		try (BufferedReader br = new BufferedReader(new FileReader(archivoProductos))){
			String linea;
			System.out.println("Información de Productos:");
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split(";");
				System.out.println("ID: " + partes[0] + ", Nombre: " + partes[1] + ", Precio: " + partes[2]);
			}
			System.out.println();
		} catch (IOException e) {
			System.err.println("Error al leer el archivo de productos: " + e.getMessage());
		}
	}
	
	public static void ProcesarVendedores(File archivoVendedores) {
		try (BufferedReader br = new BufferedReader(new FileReader(archivoVendedores))){
			String linea;
			System.out.println("Información de Vendedores:");
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split(";");
				System.out.println("Tipo: " + partes[0] + ", Número: " + partes[1] + ", Nombres: " + partes[2] + ", Apellidos: " + partes[3]);
			}
			System.out.println();
		} catch (IOException e) {
			System.err.println("Error al leer el archivo de vendedores: " + e.getMessage());
		}
	}
	
	public static void main(String[] args) {

		CreateSalesManInfoFile(5);

		CreateProductsFile(10);	

		CreateSalesMenFiles(); //Genera un .csv por cada vendedor con el informe de las ventas
		
	 String directorio = System.getProperty("user.dir"); //obtener la ruta a los archivos donde se alojan los .csv

	 File carpeta = new File(directorio);
		
		if (carpeta.exists() && carpeta.isDirectory()) { // Comprueba si la ruta es valida y si realmente existe la carpeta
			
			ProcesarProductos(new File(carpeta, "Productos.csv"));
			ProcesarVendedores(new File(carpeta, "Vendedores.csv"));
		}
		else {
			System.out.println("El directorio especificado no existe o no es un directorio válido.");
		}
	}
}
