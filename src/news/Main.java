package news;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		GenerateInfoFiles.main(args); // Invocar el método main de GenerateInfoFiles para generar los archivos necesarios

		String directorio = System.getProperty("user.dir"); // Obtener la ruta a los archivos donde se alojan los .csv
		File carpeta = new File(directorio);
		
		// Genera reporte vendedores en relacion al monto vendido
		generateSalesReport(new File("Vendedores.csv"), new File("Productos.csv"));

		// Genera el reporte de productos vendidos
		generateProductSalesReport(directorio);
		
		if (carpeta.exists() && carpeta.isDirectory()) { // Comprobar si la ruta es válida y si realmente existe la carpeta
			ProcesarProductos(new File(carpeta, "Productos.csv"));
			ProcesarVendedores(new File(carpeta, "Vendedores.csv"));
		} else {
			System.out.println("El directorio especificado no existe o no es un directorio válido.");
		}
		System.out.println("Archivos de reporte generados exitosamente.");
	}

	// Función para generar el archivo de reporte de productos vendidos por cantidad
	public static void generateProductSalesReport(String directorio) {
		// Mapa para almacenar la cantidad total vendida de cada producto
		Map<String, Integer> ventasPorProducto = new HashMap<>();

		// Mapa para almacenar el precio de cada producto //// override: por medio del id, almacenar nombre
		Map<String, String> preciosProductos = new HashMap<>();

		// Leer los archivos de ventas de los vendedores y calcular la cantidad total vendida de cada producto
		try {
			File carpeta = new File(directorio);
			File[] archivosVentas = carpeta.listFiles((dir, name) -> name.startsWith("Ventas")); //archivo[0]=idproducto /// archivo[1]=cantidad vendida

			if (archivosVentas != null) {
				for (File archivo : archivosVentas) {
					try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
						String linea;
						while ((linea = br.readLine()) != null) {
							String[] partes = linea.split(";");
							String nombreProducto = partes[0];
							int cantidad = Integer.parseInt(partes[1]);
							ventasPorProducto.put(nombreProducto,
									ventasPorProducto.getOrDefault(nombreProducto, 0) + cantidad);
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
				preciosProductos.put(partes[0], partes[1]); // Almacenar el precio del producto por su nombre ////override= guarda el name en base al id
			}
		} catch (IOException e) {
			System.err.println("Error al leer el archivo de productos: " + e.getMessage());
			return;
		}

		// Mapa para almacenar el valor total de ventas de cada producto
//		Map<String, Double> valorVentasPorProducto = new HashMap<>();
//		for (Map.Entry<String, Integer> entry : ventasPorProducto.entrySet()) {
//			String nombreProducto = entry.getKey();
//			int cantidad = entry.getValue();
//			double precioUnitario = preciosProductos.getOrDefault(nombreProducto, 0.0);
//			double valorTotalVenta = cantidad * precioUnitario;
//			valorVentasPorProducto.put(nombreProducto, valorTotalVenta);
//		}

		// Ordenar los productos por cantidad vendida (de mayor a menor)
		List<Map.Entry<String, Integer>> listaProductos = new ArrayList<>(ventasPorProducto.entrySet());
		listaProductos.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));
		// listaProductos contiene los productos ya ordenados

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
				preciosProductos.put(partes[0], Double.parseDouble(partes[2]));
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
				boolean primeraLinea = true;
				try (BufferedReader brVentas = new BufferedReader(new FileReader(archivoVentas))) {
					String lineaVenta;
					while ((lineaVenta = brVentas.readLine()) != null) {
						if (primeraLinea) {
							primeraLinea = false;
							continue;
						}

						String[] partesVenta = lineaVenta.split(";");
						String codigoProducto = partesVenta[0];
						if (preciosProductos.containsKey(codigoProducto)) {
							double precioProducto = preciosProductos.get(codigoProducto);
							int cantidad = Integer.parseInt(partesVenta[1]);
							totalVentas += precioProducto * cantidad;
						}
					}
				} catch (IOException e) {
					System.err.println(
							"Error al leer el archivo de ventas '" + nombreArchivoVentas + "': " + e.getMessage());
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
			DecimalFormat df = new DecimalFormat("#,###");
			for (Map.Entry<String, Double> vendedor : listaVendedores) {
				writer.write(vendedor.getKey() + ";" + df.format(vendedor.getValue()) + "\n");
			}
			System.out.println("Creación de archivo 'Reporte_Ventas_Vendedores.csv' exitoso");
		} catch (IOException e) {
			System.err.println("Error al escribir el archivo de reporte de ventas de vendedores: " + e.getMessage());
		}
	}

	// Funcion para validar que el proceso se realice correctamente
	public static void ProcesarProductos(File archivoProductos) {
		try (BufferedReader br = new BufferedReader(new FileReader(archivoProductos))) {
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

	// Funcion para validar que el proceso se realice correctamente
	public static void ProcesarVendedores(File archivoVendedores) {
		try (BufferedReader br = new BufferedReader(new FileReader(archivoVendedores))) {
			String linea;
			System.out.println("Información de Vendedores:");
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split(";");
				System.out.println("Tipo: " + partes[0] + ", Número: " + partes[1] + ", Nombres: " + partes[2]
						+ ", Apellidos: " + partes[3]);
			}
			System.out.println();
		} catch (IOException e) {
			System.err.println("Error al leer el archivo de vendedores: " + e.getMessage());
		}
	}

}
