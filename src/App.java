import java.nio.file.Files;
import javax.swing.*;
import java.io.*;

public class App {

    public static void main(String[] args) {
        // Input del archivo
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Archivo de entrada
            File file = fileChooser.getSelectedFile();
            long lineCount = 0;
            try {
                lineCount = Files.lines(file.toPath()).count();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 2^n
            int n = (int) Math.ceil(Math.log(lineCount) / Math.log(2));

            // Crear archivos temporales
            File temp_1 = new File("empty_file_1.txt");
            try {
                temp_1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            File temp_2 = new File("empty_file_2.txt");
            try {
                temp_2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Crear archivo de salida
            File outputFile = new File("output.txt");
            if (outputFile.exists()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));) {
                    writer.write("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    outputFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Ordenamiento
            directMergeSorting(file, n, temp_1, temp_2, outputFile);

            // Eliminar archivos temporales
            if (temp_1.exists())
                temp_1.delete();
            if (temp_2.exists())
                temp_2.delete();
        }
    }

    public static void directMergeSorting(File input, int n, File temp_1, File temp_2, File output) {
        // Copiar el archivo de entrada al archivo de salida
        try (BufferedReader reader = new BufferedReader(new FileReader(input));
                BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                writer.newLine();
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < n; i++) {
            // Leer archivo de entrada
            try (BufferedReader reader = new BufferedReader(new FileReader(output))) {
                String line = reader.readLine();
                Integer n_line = 1;
                Boolean first_temp = true;
                File temp;
                while (line != null) {
                    // Escribir sobre el archivo temporal
                    if (first_temp) {
                        temp = temp_1;
                    } else {
                        temp = temp_2;
                    }
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(temp, true))) {
                        writer.write(line);
                        writer.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Verificación de cambio de archivo temporal
                    if ((int) (n_line % Math.pow(2, i)) == 0)
                        first_temp = !first_temp;

                    line = reader.readLine();
                    n_line++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Mezclar archivos temporales
            Integer n_pop_file_1 = 0, n_pop_file_2 = 0;
            try (BufferedReader reader_1 = new BufferedReader(new FileReader(temp_1));
                    BufferedReader reader_2 = new BufferedReader(new FileReader(temp_2));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
                String line_1 = reader_1.readLine();
                String line_2 = reader_2.readLine();
                Boolean first_temp = true;
                while (line_1 != null || line_2 != null) {
                    // Validación de nuevo bloque
                    if (n_pop_file_1 == Math.pow(2, i) && n_pop_file_2 == Math.pow(2, i)) {
                        n_pop_file_1 = 0;
                        n_pop_file_2 = 0;
                    }

                    // Escogencia de archivo con menor valor
                    if (n_pop_file_1 == Math.pow(2, i) && n_pop_file_2 != Math.pow(2, i)) {
                        first_temp = false;
                    }
                    if (n_pop_file_1 != Math.pow(2, i) && n_pop_file_2 == Math.pow(2, i)) {
                        first_temp = true;
                    }
                    if (n_pop_file_1 != Math.pow(2, i) && n_pop_file_2 != Math.pow(2, i) && line_1 != null
                            && line_2 != null) {
                        first_temp = Integer.parseInt(line_1) < Integer.parseInt(line_2);
                    }

                    // Escritura sobre archivo de salida
                    if (first_temp) {
                        writer.write(line_1);
                        writer.newLine();
                        line_1 = reader_1.readLine();
                        n_pop_file_1++;
                    } else {
                        writer.write(line_2);
                        writer.newLine();
                        line_2 = reader_2.readLine();
                        n_pop_file_2++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Limpiar archivos temporales
            try (BufferedWriter writer1 = new BufferedWriter(new FileWriter(temp_1));
                    BufferedWriter writer2 = new BufferedWriter(new FileWriter(temp_2))) {
                writer1.write("");
                writer2.write("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
