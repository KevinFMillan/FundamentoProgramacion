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
	    CreateSalesMenFiles(); // Genera un .csv por cada vendedor con el informe de las ventas

	    // Genera el reporte de ventas de los vendedores
	    generateSalesReport(new File("Vendedores.csv"), new File("Productos.csv"));
	    
	    // Genera el reporte de productos vendidos
	    generateProductSalesReport();
	    
	    String directorio = System.getProperty("user.dir"); // Obtener la ruta a los archivos donde se alojan los .csv
	    File carpeta = new File(directorio);
	    
	    if (carpeta.exists() && carpeta.isDirectory()) { // Comprobar si la ruta es válida y si realmente existe la carpeta
	        ProcesarProductos(new File(carpeta, "Productos.csv"));
	        ProcesarVendedores(new File(carpeta, "Vendedores.csv"));
	    } else {
	        System.out.println("El directorio especificado no existe o no es un directorio válido.");
	    }
	}
	

    // Función para generar el archivo de reporte de ventas de los vendedores
	public static void generateSalesReport(File archivoVendedores, File archivoProductos) {
	    // Mapa para almacenar el valor total de ventas por vendedor
	    Map<String, Double> ventasPorVendedor = new HashMap<>();

	    // Leer la información de los productos y almacenar el precio de cada uno en un mapa
	    Map<String, Double> preciosProductos = new HashMap<>();
	    try (BufferedReader br = new BufferedReader(new FileReader(archivoProductos))) {
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            String[] partes = linea.split(";");
	            preciosProductos.put(partes[1], Double.parseDouble(partes[2]));
	        }
	    } catch (IOException e) {
	        System.err.println("Error al leer el archivo de productos: " + e.getMessage());
	        return;
	    }

	    // Leer la información de las ventas de los vendedores y calcular el valor total de ventas por vendedor
	    try (BufferedReader br = new BufferedReader(new FileReader(archivoVendedores))) {
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            String[] partes = linea.split(";");
	            double totalVentas = 0;
	            String nombreArchivoVentas = "Ventas " + partes[2] + " " + partes[3] + " " + partes[1] + ".csv";
	            File archivoVentas = new File(nombreArchivoVentas);
	            try (BufferedReader brVentas = new BufferedReader(new FileReader(archivoVentas))) {
	                String lineaVenta;
	                while ((lineaVenta = brVentas.readLine()) != null) {
	                    String[] partesVenta = lineaVenta.split(";");
	                    String nombreProducto = partesVenta[0];
	                    if (preciosProductos.containsKey(nombreProducto)) {
	                        double precioProducto = preciosProductos.get(nombreProducto);
	                        int cantidad = Integer.parseInt(partesVenta[1]);
	                        totalVentas += precioProducto * cantidad;
	                    }
	                }
	            } catch (IOException e) {
	                System.err.println("Error al leer el archivo de ventas '" + nombreArchivoVentas + "': " + e.getMessage());
	            }
	            ventasPorVendedor.put(partes[2] + " " + partes[3], totalVentas);
	        }
	    } catch (IOException e) {
	        System.err.println("Error al leer el archivo de vendedores: " + e.getMessage());
	        return;
	    }

	    // Ordenar los vendedores por el valor total de ventas (de mayor a menor)
	    List<Map.Entry<String, Double>> listaVendedores = new ArrayList<>(ventasPorVendedor.entrySet());
	    listaVendedores.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

	    // Escribir la información de los vendedores en un archivo CSV
	    try (FileWriter writer = new FileWriter("Reporte_Ventas_Vendedores.csv")) {
	        for (Map.Entry<String, Double> vendedor : listaVendedores) {
	            writer.write(vendedor.getKey() + ";" + vendedor.getValue() + "\n");
	        }
	        System.out.println("Creación de archivo 'Reporte_Ventas_Vendedores.csv' exitoso");
	    } catch (IOException e) {
	        System.err.println("Error al escribir el archivo de reporte de ventas de vendedores: " + e.getMessage());
	    }
	}

    // Función para generar el archivo de reporte de productos vendidos por cantidad
	public static void generateProductSalesReport() {
	    // Mapa para almacenar la cantidad total vendida de cada producto
	    Map<String, Integer> ventasPorProducto = new HashMap<>();

	    // Mapa para almacenar el precio de cada producto
	    Map<String, Double> preciosProductos = new HashMap<>();

	    // Leer los archivos de ventas de los vendedores y calcular la cantidad total vendida de cada producto
	    try {
	        File carpeta = new File(System.getProperty("user.dir"));
	        File[] archivosVentas = carpeta.listFiles((dir, name) -> name.startsWith("Ventas"));

	        if (archivosVentas != null) {
	            for (File archivo : archivosVentas) {
	                try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
	                    String linea;
	                    while ((linea = br.readLine()) != null) {
	                        String[] partes = linea.split(";");
	                        String nombreProducto = partes[0];
	                        int cantidad = Integer.parseInt(partes[1]);
	                        ventasPorProducto.put(nombreProducto, ventasPorProducto.getOrDefault(nombreProducto, 0) + cantidad);
	                    }
	                }
	            }
	        }
	    } catch (IOException e) {
	        System.err.println("Error al leer los archivos de ventas de los vendedores: " + e.getMessage());
	        return;
	    }

	    // Leer la información de los productos y almacenar el precio de cada uno en un mapa
	    try (BufferedReader br = new BufferedReader(new FileReader("Productos.csv"))) {
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            String[] partes = linea.split(";");
	            preciosProductos.put(partes[0], Double.parseDouble(partes[2])); // Almacenar el precio del producto por su nombre
	        }
	    } catch (IOException e) {
	        System.err.println("Error al leer el archivo de productos: " + e.getMessage());
	        return;
	    }

	    // Mapa para almacenar el valor total de ventas de cada producto
	    Map<String, Double> valorVentasPorProducto = new HashMap<>();
	    for (Map.Entry<String, Integer> entry : ventasPorProducto.entrySet()) {
	        String nombreProducto = entry.getKey();
	        int cantidad = entry.getValue();
	        double precioUnitario = preciosProductos.getOrDefault(nombreProducto, 0.0);
	        double valorTotalVenta = cantidad * precioUnitario;
	        valorVentasPorProducto.put(nombreProducto, valorTotalVenta);
	    }

	    // Ordenar los productos por cantidad vendida (de mayor a menor)
	    List<Map.Entry<String, Integer>> listaProductos = new ArrayList<>(ventasPorProducto.entrySet());
	    listaProductos.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

	    // Escribir la información de los productos en un archivo CSV
	    try (FileWriter writer = new FileWriter("Reporte_Productos_Vendidos.csv")) {
	        for (Map.Entry<String, Integer> producto : listaProductos) {
	            double valorTotalVenta = valorVentasPorProducto.getOrDefault(producto.getKey(), 0.0);
	            writer.write(producto.getKey() + ";" + producto.getValue() + ";" + valorTotalVenta + "\n");
	        }
	        System.out.println("Creación de archivo 'Reporte_Productos_Vendidos.csv' exitoso");
	    } catch (IOException e) {
	        System.err.println("Error al escribir el archivo de reporte de productos vendidos: " + e.getMessage());
	    }
	}
}
